package com.gsmggk.accountspayable.services;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Account;

public class AccountParaTest extends AbstractTest {

	@Inject
	private IAccountService accountService;

	private  Account account;
	private Account accountAfter;

	

	@Before
	public void runBefore() {
		account = new Account();
		account.setAccountName("Test account para");
		BigDecimal money = new BigDecimal("1300.25");
		account.setSumm(money);
		account.setDebtorId(1);
		accountAfter = new Account();
	}

	//@Ignore
	@Test
	public void readTest() {

		account = accountService.get(1);
		Assert.notNull(account, "account after read must be not null");
	}

	//@Ignore
	@Test
	@Rollback(true)
	public void deleteTest() {
		account = accountService.get(1);

		accountService.delete(account);
		accountAfter = accountService.get(1);
		System.out.println(account);
	}

	@Test
//	@Ignore
	public void getAllTest() {
		List<Account> accounts = accountService.getAll();
		
		System.out.println(accounts);
		Assert.notEmpty(accounts, "List accounts not null");

	}

//	 @Ignore
	@Test
	@Rollback(true)
	public void insertTest() {
		accountService.save(account);

		accountAfter = accountService.get(account.getId());
		
		System.out.println(accountAfter);
		Assert.notNull(account, "insertTest accont after insert must be not null");
		Assert.notNull(accountAfter, "insertTest accont readed after save must be not null");
		Assert.isTrue(accountAfter.equals(account), "account readed after save must be equal accont");

	}

	@Test
	// @Ignore
	@Rollback(true)
	public void updateTest() {
		

		account = accountService.get(1);
	      
		account.setSumm(new BigDecimal("1313.11"));
		accountService.save(account);
		accountAfter = accountService.get(account.getId());
		System.out.println("u" + accountAfter);
		Assert.notNull(account, "updateTest accont after update must be not null");
		Assert.notNull(accountAfter, "updateTest accont readed after save update be not null");
		Assert.isTrue(accountAfter.equals(account), "updateTest account readed after save must be NOT equal accont");

		Assert.isTrue(accountAfter.getSumm().equals(new BigDecimal("1313.11")),
				"updateTest account readed after save must be  equal accont after correct");

	}

}
