package com.kyc.kakaopaysubject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kyc.kakaopaysubject.dto.KaKaopayMoneySpreadDTO;
import com.kyc.kakaopaysubject.workflow.KakaopayMoneySpreadWorkFlowBI;


@RestController
@RequestMapping("kakopay")
public class Controller {
	
	
	@Autowired
	private KakaopayMoneySpreadWorkFlowBI KakaopayMoneySpreadWorkFlow;
	
	
	
	public KakaopayMoneySpreadWorkFlowBI getKakaopayMoneySpreadWorkFlow() {
		return KakaopayMoneySpreadWorkFlow;
	}
	public void setKakaopayMoneySpreadWorkFlow(KakaopayMoneySpreadWorkFlowBI kakaopayMoneySpreadWorkFlow) {
		KakaopayMoneySpreadWorkFlow = kakaopayMoneySpreadWorkFlow;
	}


	// 카카오머니 뿌리기
	@GetMapping("/spread/{spreadMoneyAmt}/{spreadPerson}")
	public String spreadMoneyApi(@PathVariable int spreadMoneyAmt, @PathVariable int spreadPerson) {
		KaKaopayMoneySpreadDTO rsDTO = new KaKaopayMoneySpreadDTO();
		
		rsDTO = KakaopayMoneySpreadWorkFlow.spreadMoneyRequestApi(spreadMoneyAmt, spreadPerson);
		return rsDTO.getToken();
	}
	
	
	// 카카오머니 받기
	@GetMapping("/recieve/{token}/{xUserId}")
	public int spreadMoneyReceiveApi(@PathVariable String token, @PathVariable int xUserId) {
		KaKaopayMoneySpreadDTO rsDTO = new KaKaopayMoneySpreadDTO();
		
		rsDTO = KakaopayMoneySpreadWorkFlow.spreadMoneyReceiveApi(token, xUserId);
		return rsDTO.getSpreadMoneyForReceiver();
	}
	
	// 뿌린사람의 뿌리기 진행상황 조회
	@GetMapping("/allUsers/{token}/{xUserId}")
	public KaKaopayMoneySpreadDTO getAllUsers(@PathVariable String token, @PathVariable int xUserId){
		KaKaopayMoneySpreadDTO rsDTO  = KakaopayMoneySpreadWorkFlow.spreadMoneyInquiryApi(token, xUserId);
		
		return rsDTO;
	}
		
//	// 모든 유저조회
//	@GetMapping("/allUsers")
//	public List<MoneySpreadUser> getAllUsers(){
//		List<MoneySpreadUser> users = KakaopayMoneySpreadWorkFlow.findAll();
//		
//		return users;
//	}
	
	
}
