package com.gsmggk.accountspayable.services;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.datamodel.Account;

@Transactional
public class AccountParaTest extends AbstractTest {

	@Inject
	private IAccountService accountService;
	private static Account account;

	@BeforeClass
	public static void runBeforeTests() {
		account = new Account();
		account.setAccountName("Test account para");

		BigDecimal money = new BigDecimal("1300.25");

		account.setMoney(money);
		account.setDebtorId(1);
	}

	@Ignore
	@Test
	public void readTest() {
		account = accountService.getR(3);
		System.out.println(account);

	}

	@Ignore
	@Test
	@Rollback(true)
	public void deleteTest() {
		account = accountService.getR(3);
		System.out.println(account);
		accountService.deleteR(account);
		account = accountService.getR(3);
		System.out.println(account);
	}

	@Test
	@Ignore
	public void getAllTest() {
		List<Account> accounts = accountService.getAllR();
		// TODO доделать тест
		System.out.println(accounts);

	}

	@Test
	@Ignore
	@Rollback(true)
	public void insertTest() {
		accountService.saveR(account);
		List<Account> accounts = accountService.getAllR();
		// TODO доделать тест
		System.out.println(accounts);

	}
	@Test
	
	@Rollback(true)
	public void updateTest() {
		accountService.saveR(account);
		List<Account> accounts = accountService.getAllR();
		// TODO доделать тест
		System.out.println(accounts);
		
		Account accountUpdated=new Account();
		accountUpdated=accountService.getR(account.getId())	;
		
		accountUpdated.setMoney(new BigDecimal("1313.11"));
		accountService.saveR(accountUpdated);
	//	accounts.clear();
		accounts = accountService.getAllR();
		// TODO доделать тест
		System.out.println(accounts);

	}

}
