package com.gsmggk.accountspayable.services;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Account;

public interface IAccountService {

	
	void save(Account account);

	List<Account> getAll();

	Account get(Integer id);

	
	void delete(Account account);
	
	//FIXME remove after test
	Account getR(Integer id);
	
	
	void deleteR(Account account);
	
	List<Account> getAllR();
	
	
	void saveR(Account account);

	
}
