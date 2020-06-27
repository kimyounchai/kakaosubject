package com.kyc.kakaopaysubject.workflow;

import java.util.List;

import com.kyc.kakaopaysubject.dto.KaKaopayMoneySpreadDTO;
import com.kyc.kakaopaysubject.model.MoneySpreadUser;

public interface KakaopayMoneySpreadWorkFlowBI {

	
	// 뿌리기 API
	public KaKaopayMoneySpreadDTO spreadMoneyRequestApi(int spreadMoneyAmt, int spreadPerson);
	
	// 받기 API
	public KaKaopayMoneySpreadDTO spreadMoneyReceiveApi(String token, int xUserid);
	
	// 조회 API
	public KaKaopayMoneySpreadDTO spreadMoneyInquiryApi(String token, int xUserid);
	
	List<MoneySpreadUser> findAll();
	
	MoneySpreadUser finById(int xUserId);
	
	MoneySpreadUser saveUserInfo(MoneySpreadUser user);
	
	void updateUserSpreadInfoById(int xUserId, MoneySpreadUser user);
	
	void saveSpreadMoneyInfo(MoneySpreadUser user);
	
	
}
