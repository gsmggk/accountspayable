package com.gsmggk.accountspayable.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gsmggk.accountspayable.dao4api.IAccountDao;
import com.gsmggk.accountspayable.datamodel.Account;
import com.gsmggk.accountspayable.services.IAccountService;

@Service
public class AccountServiceImpl implements IAccountService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
	

	
	@Inject
	private IAccountDao accountDao;

	@Override
	public void save(Account account) {
		if (account.getId() == null) {
			accountDao.insert(account);
			LOGGER.info("Add account id:{} for debtor id{}",account.getId(),account.getDebtorId());
		} else {
			accountDao.update(account);
			LOGGER.info("Update account id:{} for debtor id{}",account.getId(),account.getDebtorId());
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
		LOGGER.warn("Delete account id:{} for debtor id{}",account.getId(),account.getDebtorId());
		accountDao.delete(account.getId());

	}

	@Override
	public List<Account> getAccounts4Debtor(Integer debtorId) {
		 		
		return accountDao.getAccounts(debtorId);
	}

	

	

}
