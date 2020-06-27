package com.kyc.kakaopaysubject.util;

import java.util.Random;

public class CreateToken {
	
	private int tokenLength = 3;
	private char[] tokenRule = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 
					            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
								's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
								'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 
					            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
								'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
								'!', '@', '#', '$', '%', '&', '*'
							   };
	
	public String getToken() {
		
		String token = null;
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		int tokenRuleLength = tokenRule.length;
		
		for(int i=0 ; i<tokenLength ; i++) {
			buf.append(tokenRule[random.nextInt(tokenRuleLength)]);
		}
		
		token = buf.toString();
		
		
		System.out.println(token);
		
		
		return token;
	}
}
