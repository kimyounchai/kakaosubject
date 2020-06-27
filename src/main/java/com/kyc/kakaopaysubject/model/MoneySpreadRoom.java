package com.kyc.kakaopaysubject.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "SpreadRoom")
public class MoneySpreadRoom {
	
	private static final long seriaVersioUID = 1L;
	
	@Id
	private String xRoomId;			// 대화방 식별값 
	
	private int xUserId;			// 사용자 식별값
	private int spreadMoneyBalance;	// 뿌릴 총잔액
	private int spreadMoneyAmt;		// 뿌릴금액
	private int spreadPerson;		// 뿌릴인원
	private Date spreadOc;			// 뿌린일시
}
