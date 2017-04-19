package com.gsmggk.accountspayable.services;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Account;


public class AccountServiceTest extends AbstractTest {

	@Inject
	private IAccountService accountService;
	
	private static Account account;

	@BeforeClass
	public static void runBeforeTests() {
		account = new Account();
		account.setAccountName("Test account");

		BigDecimal money = new BigDecimal("3000.25");

		account.setSumm(money);
		// account.setDebtorId(1);
	}

	@Ignore
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
	
	@Test
	public void getAccounts4DebtorTest(){
		Integer debtorId=1;
		accountService.getAccounts4Debtor(debtorId);
		
	}
	
	
}
