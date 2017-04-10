package com.gsmggk.accountspayable.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao4db.IAccountDao;
import com.gsmggk.accountspayable.dao4db.IrAccountDao;
import com.gsmggk.accountspayable.datamodel.Account;
import com.gsmggk.accountspayable.services.IAccountService;

@Service
public class AccountServiceImpl implements IAccountService {
	// FIXME remove after test
	@Inject
	private IrAccountDao accountDaoR;
	
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
		accountDao.delete(account);

	}

	
	// FIXME remove after test
	@Override
	public Account getR(Integer id) {
		return accountDaoR.readR(id);
	}

	@Override
	public void deleteR(Account account) {
		 accountDaoR.deleteR(account);
	}

	@Override
	public List<Account> getAllR() {
		
		return accountDaoR.getAllR();
	}

	@Override
	public void saveR(Account account) {
		if (account.getId() == null) {

			accountDaoR.insertR(account);
		} else {

			accountDaoR.updateR(account);
		}

		
	}
	

}
