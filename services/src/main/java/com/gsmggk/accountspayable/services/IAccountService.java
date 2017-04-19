package com.gsmggk.accountspayable.services;

import java.util.List;

import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.datamodel.Account;

public interface IAccountService {

	
	void save(Account account);

	List<Account> getAll();

	Account get(Integer id);

	
	void delete(Account account);
	

	 /**
	  * Get accounts for debtor with id
	 * @param  id -  debtor id 
	 * @return List<Account>
	 */
	List<Account> getAccounts4Debtor(Integer debtorId);
	
	

	
}
