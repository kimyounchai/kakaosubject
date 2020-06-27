package com.kyc.kakaopaysubject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kyc.kakaopaysubject.model.MoneySpreadUser;
import com.kyc.kakaopaysubject.repository.UserRepository;

@SpringBootApplication
public class KakaopaysubjectApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(KakaopaysubjectApplication.class, args);
	}

	
	@Autowired UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception{
		
		int cnt = 10;
		String xRoomId = null;
		String mainUserYn = null;
		
		for (int i=1 ; i<= cnt ; i++) {
			
			if(i == 1) {
				mainUserYn = "Y";
			}else {
				mainUserYn = "N";
			}
			
			xRoomId = "A";
			
			userRepository.save(new MoneySpreadUser(i, "", xRoomId, 9999999,0, 0, 0, null, 0, mainUserYn, "N"));			
			
		}
		
	}
}
