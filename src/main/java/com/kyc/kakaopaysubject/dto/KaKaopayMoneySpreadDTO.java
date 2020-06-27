package com.kyc.kakaopaysubject.dto;

import java.util.Date;
import java.util.List;

public class KaKaopayMoneySpreadDTO {
	
	private String token;			// 토큰
	private int xUserId;			// 사용자 식별값
	private String xRoomId;			// 대화방 식별값 
	private int spreadMoneyBalance;	// 뿌릴 총잔액
	private int spreadOriginalAmt;	// 뿌린금액 원본
	private int spreadMoneyAmt;		// 뿌릴금액
	private int spreadPerson;		// 뿌릴인원
	private Date spreadOc;			// 뿌린일시
	private int spreadMoneyForReceiver; // 받은금액
	private int recieveCompAmt;		// 받기완료된금액
	
	private List<KaKaopayMoneySpreadDTO> receiveList;	// 받은사람리스트
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getxUserId() {
		return xUserId;
	}
	public void setxUserId(int xUserId) {
		this.xUserId = xUserId;
	}
	public String getxRoomId() {
		return xRoomId;
	}
	public void setxRoomId(String xRoomId) {
		this.xRoomId = xRoomId;
	}
	public int getSpreadMoneyBalance() {
		return spreadMoneyBalance;
	}
	public void setSpreadMoneyBalance(int spreadMoneyBalance) {
		this.spreadMoneyBalance = spreadMoneyBalance;
	}
	public int getSpreadMoneyAmt() {
		return spreadMoneyAmt;
	}
	public void setSpreadMoneyAmt(int spreadMoneyAmt) {
		this.spreadMoneyAmt = spreadMoneyAmt;
	}
	public int getSpreadPerson() {
		return spreadPerson;
	}
	public void setSpreadPerson(int spreadPerson) {
		this.spreadPerson = spreadPerson;
	}
	public Date getSpreadOc() {
		return spreadOc;
	}
	public void setSpreadOc(Date spreadOc) {
		this.spreadOc = spreadOc;
	}
	public int getSpreadMoneyForReceiver() {
		return spreadMoneyForReceiver;
	}
	public void setSpreadMoneyForReceiver(int spreadMoneyForReceiver) {
		this.spreadMoneyForReceiver = spreadMoneyForReceiver;
	}
	public List<KaKaopayMoneySpreadDTO> getReceiveList() {
		return receiveList;
	}
	public void setReceiveList(List<KaKaopayMoneySpreadDTO> receiveList) {
		this.receiveList = receiveList;
	}
	public int getSpreadOriginalAmt() {
		return spreadOriginalAmt;
	}
	public void setSpreadOriginalAmt(int spreadOriginalAmt) {
		this.spreadOriginalAmt = spreadOriginalAmt;
	}
	public int getRecieveCompAmt() {
		return recieveCompAmt;
	}
	public void setRecieveCompAmt(int recieveCompAmt) {
		this.recieveCompAmt = recieveCompAmt;
	}
	
}
