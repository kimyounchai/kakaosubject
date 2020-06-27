package com.kyc.kakaopaysubject.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Kakaopay_user")
public class MoneySpreadUser {
	
	private static final long seriaVersioUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int xUserId;			// 사용자 식별값
	
	@Column(columnDefinition = "varchar(3)")
	private String token;			// 토큰
	
	
	private String xRoomId;			// 대화방 식별값 
	private int spreadMoneyBalance;	// 뿌릴 총잔액
	private int spreadMoneyAmt;		// 뿌릴금액
	private int spreadOriginalAmt;	// 뿌린금액 원본
	private int spreadPerson;		// 뿌릴인원
	private Date spreadOc;			// 뿌린일시
	private int spreadMoneyForReciver; // 받을금액
	private String mainUser;		// 뿌릴사용자
	private String spreadMoneyReciveYn; // 뿌린금액 받은여부
	
	
}
