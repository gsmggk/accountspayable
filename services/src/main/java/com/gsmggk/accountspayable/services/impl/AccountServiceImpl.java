package com.gsmggk.accountspayable.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao4api.IAccountDao;
import com.gsmggk.accountspayable.datamodel.Account;
import com.gsmggk.accountspayable.services.IAccountService;

@Service
public class AccountServiceImpl implements IAccountService {

	
	@Inject
	private IAccountDao accountDao;

	@Override
	public void save(Account account) {
		if (account.getId() == null) {

			accountDao.insert(account);
		} else {

			accountDao.update(account);
		}


	}

	@Override
	public List<Account> getAll() {
		
		return accountDao.getAll();
	}

	@Override
	public Account get(Integer id) {
		
		return accountDao.read(id);
	}

	@Override
	public void delete(Account account) {
		accountDao.delete(account.getId());

	}

	@Override
	public List<Account> getAccounts4Debtor(Integer debtorId) {
		 		
		return accountDao.getAccounts(debtorId);
	}

	

	

}
