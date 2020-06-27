package com.kyc.kakaopaysubject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kyc.kakaopaysubject.model.MoneySpreadUser;


@Repository
public interface UserRepository extends JpaRepository<MoneySpreadUser, Integer> {

	
	List<MoneySpreadUser> findAll();
	
	MoneySpreadUser findById(int xUserId);
	
	public MoneySpreadUser save(MoneySpreadUser user);
	
//	public MoneySpreadUser updateById(int xUserId, MoneySpreadUser user);
	
	
}
