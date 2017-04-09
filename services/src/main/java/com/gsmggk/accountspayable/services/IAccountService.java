package com.gsmggk.accountspayable.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.datamodel.Account;

public interface IAccountService {

	@Transactional
	void save(Account account);

	List<Account> getAll();

	Account get(Integer id);

	@Transactional
	void delete(Account account);
	
	//FIXME remove after test
	Account getR(Integer id);
	
	@Transactional
	void deleteR(Account account);
	
	List<Account> getAllR();
	
	@Transactional
	void saveR(Account account);

	
}
