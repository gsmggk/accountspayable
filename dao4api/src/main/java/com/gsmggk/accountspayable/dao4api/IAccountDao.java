package com.gsmggk.accountspayable.dao4api;

import java.util.List;

import com.gsmggk.accountspayable.dao4api.filter.Criteria;
import com.gsmggk.accountspayable.dao4api.generic.IGenericDao;
import com.gsmggk.accountspayable.datamodel.Account;

public interface IAccountDao extends IGenericDao<Account>{

	/**
	 * Get accounts for debtor
	 * @param id - debtor id
	 * @return List<Account>
	 */
	List<Account> getAccounts(Integer debtorId);

	

	
	
}
