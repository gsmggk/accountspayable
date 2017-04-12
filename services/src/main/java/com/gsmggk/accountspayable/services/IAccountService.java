package com.gsmggk.accountspayable.services;

import java.util.List;

import com.gsmggk.accountspayable.datamodel.Account;

public interface IAccountService {

	
	void save(Account account);

	List<Account> getAll();

	Account get(Integer id);

	
	void delete(Account account);
	


	
}
