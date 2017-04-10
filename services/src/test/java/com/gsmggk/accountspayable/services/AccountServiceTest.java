package com.gsmggk.accountspayable.services;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Account;

@Transactional
public class AccountServiceTest extends AbstractTest {

	@Inject
	private IAccountService accountService;
	
	private static Account account;

	@BeforeClass
	public static void runBeforeTests() {
		account = new Account();
		account.setAccountName("Test account");

		BigDecimal money = new BigDecimal("3000.25");

		account.setMoney(money);
		// account.setDebtorId(1);
	}

	
	@Test
	@Rollback(true)
	public void InsertTest() {
		// FIXME Дделать форин кей  
	//	accountService.save(account);
		Integer savedAccountId = account.getId();
		Account accountFromDb = new Account();
		accountFromDb = accountService.get(savedAccountId);

		Assert.isNull(accountFromDb, "must be not null after save");

	}
}
