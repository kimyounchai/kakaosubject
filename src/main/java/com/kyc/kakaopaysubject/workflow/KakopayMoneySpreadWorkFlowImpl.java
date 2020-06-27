package com.kyc.kakaopaysubject.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kyc.kakaopaysubject.dto.KaKaopayMoneySpreadDTO;
import com.kyc.kakaopaysubject.model.MoneySpreadUser;
import com.kyc.kakaopaysubject.repository.UserRepository;
import com.kyc.kakaopaysubject.util.CreateToken;


@Service
public class KakopayMoneySpreadWorkFlowImpl implements KakaopayMoneySpreadWorkFlowBI{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public KaKaopayMoneySpreadDTO spreadMoneyRequestApi(int spreadMoneyAmt, int spreadPerson) {
		KaKaopayMoneySpreadDTO rsDto = new KaKaopayMoneySpreadDTO();
		
		CreateToken tokenUtil = new CreateToken();
		
		String token = tokenUtil.getToken();
		System.out.println(token);
		
		rsDto.setSpreadMoneyAmt(spreadMoneyAmt);
		rsDto.setToken(token);
		
		
		List<MoneySpreadUser> users = new ArrayList<>();
		
		userRepository.findAll().forEach(e -> users.add(e));
		
		for(int i=0 ; i<users.size() ; i++) {
			MoneySpreadUser tmpUser = users.get(i);
			
			tmpUser.setToken(token);
			
			// 소수점은 올림처리
			int realSpreadMoney = (int)Math.round((double)spreadMoneyAmt/spreadPerson);
			
			// 뿌린사람은 자신의돈을 받을 수 없음.
			if("Y".equals(tmpUser.getMainUser())) {
				tmpUser.setSpreadMoneyAmt(spreadMoneyAmt);
				tmpUser.setSpreadOriginalAmt(spreadMoneyAmt);
			}else {
				tmpUser.setSpreadMoneyForReciver(realSpreadMoney);			// 뿌린금액에 대하여 대화방내의 인원들은 같은금액을 미리저장하고 있음.
			}
			
			tmpUser.setSpreadOc(new Date(System.currentTimeMillis()));	// 뿌린일시 저장
			
			userRepository.save(tmpUser);
		}
		
		return rsDto;
	}

	
	@Override
	public KaKaopayMoneySpreadDTO spreadMoneyReceiveApi(String token, int xUserId) {
		KaKaopayMoneySpreadDTO rsDto = new KaKaopayMoneySpreadDTO();
		int mainUserId = 0;
		int receiveAmt = 0;
		
		rsDto.setToken(token);
		
		
		List<MoneySpreadUser> users = new ArrayList<>();
		
		userRepository.findAll().forEach(e -> users.add(e));
		
		for(int i=0 ; i<users.size() ; i++) {
			// 뿌린유저정보 저장
			if("Y".equals(users.get(i).getMainUser())) {
				mainUserId = users.get(i).getXUserId();
			}
		}
		
		for(int i=0 ; i<users.size() ; i++) {
			MoneySpreadUser tmpUser = users.get(i);
			
			// 토큰일치여부검증
			// 받기수행한 사용자 and 아직받지않은사용자인지 and 자신이 뿌린사용자인지
			if(token.equals(tmpUser.getToken()) && xUserId == tmpUser.getXUserId() && "N".equals(tmpUser.getSpreadMoneyReciveYn()) && "N".equals(tmpUser.getMainUser())) {
				Date now = new Date(System.currentTimeMillis());
				long differDate = now.getTime() - tmpUser.getSpreadOc().getTime();
								
				// 토큰발급 후 10분이내일경우
				if(differDate <= (1000 * 60 * 10)) {					
					MoneySpreadUser mainUser = userRepository.findById(mainUserId);
					int totalSpreadMoney = mainUser.getSpreadMoneyAmt();
					// 뿌릴금액이 아직남았는지 체크
					if(totalSpreadMoney > tmpUser.getSpreadMoneyForReciver()) {
						tmpUser.setSpreadMoneyReciveYn("Y");		// 받은유저의 상태값업데이트
						receiveAmt = tmpUser.getSpreadMoneyForReciver();
						
						mainUser.setSpreadMoneyBalance(mainUser.getSpreadMoneyBalance() - tmpUser.getSpreadMoneyForReciver()); // 뿌린사용자의 총잔액깍기
						mainUser.setSpreadMoneyAmt(mainUser.getSpreadMoneyAmt() - tmpUser.getSpreadMoneyForReciver());		   // 뿌린금액에 대한 잔액깍기

						userRepository.save(mainUser);		// 뿌린사용자 정보업데이트
						userRepository.save(tmpUser);	// 받은사용자 update
					}
				}
				
				break;
				
			}
		}
		
		rsDto.setSpreadMoneyForReceiver(receiveAmt);
		return rsDto;
	}
	
	
	@Override
	public KaKaopayMoneySpreadDTO spreadMoneyInquiryApi(String token, int xUserid) {
		
		KaKaopayMoneySpreadDTO rsDto = new KaKaopayMoneySpreadDTO();
		List<KaKaopayMoneySpreadDTO> receiveList = new ArrayList<>(); 
		List<MoneySpreadUser> users = new ArrayList<>();
		MoneySpreadUser mainUser = new MoneySpreadUser();
		
		userRepository.findAll().forEach(e -> users.add(e));
		
		for(int i=0 ; i<users.size() ; i++) {
			// 뿌린유저정보 저장
			if("Y".equals(users.get(i).getMainUser())) {
				mainUser = users.get(i);
			}
		}
		
		Date now = new Date(System.currentTimeMillis());
		long differDate = now.getTime() - mainUser.getSpreadOc().getTime();
		
		
		// 뿌린건에 대하여 7일 이내에만 조회가능
		if(differDate <= (1000 * 60 * 60 * 24 * 7)) {	
			// 뿌린사용자정보셋팅
			rsDto.setSpreadOc(mainUser.getSpreadOc());	// 뿌린일시
			rsDto.setSpreadOriginalAmt(mainUser.getSpreadOriginalAmt());	// 뿌린금액
			rsDto.setRecieveCompAmt(mainUser.getSpreadOriginalAmt() - mainUser.getSpreadMoneyAmt());	// 뿌린금액 - 현재잔액 = 받기완료된금액
			// 조회요청자가 뿌린사람일경우
			if(token.equals(mainUser.getToken()) && xUserid == mainUser.getXUserId()) {
				for(int i=0 ; i<users.size() ; i++) {
					// 받기완료된 사용자정보 조회
					if("Y".equals(users.get(i).getSpreadMoneyReciveYn())) {
						KaKaopayMoneySpreadDTO tmpDto = new KaKaopayMoneySpreadDTO();
						tmpDto.setSpreadMoneyForReceiver(users.get(i).getSpreadMoneyForReciver());	// 받은금액
						tmpDto.setxUserId(users.get(i).getXUserId());	// 받은사용자ID
						
						receiveList.add(tmpDto);	
					}
				}
			}
			
		}
		
		rsDto.setReceiveList(receiveList);
		
		return rsDto;
	}

	
	
	@Override
	public List<MoneySpreadUser> findAll() {
		List<MoneySpreadUser> users = new ArrayList<>();
		
		userRepository.findAll().forEach(e -> users.add(e));
		
		return users;
	}

	@Override
	public MoneySpreadUser finById(int xUserId) {
		MoneySpreadUser user = userRepository.findById(xUserId);
		
		return user;
	}

	@Override
	public MoneySpreadUser saveUserInfo(MoneySpreadUser user) {
		userRepository.save(user);
		
		return user;
	}

	@Override
	public void updateUserSpreadInfoById(int xUserId, MoneySpreadUser user) {
		MoneySpreadUser userInfo = userRepository.findById(xUserId);
		
		userInfo.setToken(user.getToken());
		
	}

	@Override
	public void saveSpreadMoneyInfo(MoneySpreadUser user) {
		userRepository.save(user);
	}



}
