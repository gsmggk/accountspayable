package com.gsmggk.accountspayable.dao4api;

import java.util.List;

import com.gsmggk.accountspayable.dao4api.generic.IGenericDao;
import com.gsmggk.accountspayable.datamodel.Account;

public interface IAccountDao extends IGenericDao<Account>{

	List<Account> search(Integer id);

	
	
}
