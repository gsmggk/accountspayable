package com.gsmggk.accountspayable.services;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gsmggk.accountspayable.datamodel.Account;

public class AccountServiceTest extends AbstractTest {

	@Inject
	private IAccountService accountService;
	private static Account account;

	@BeforeClass
	public static void runBeforeTests(){
		 account=new Account();
		account.setAccountName("Test account");
		
		BigDecimal money = new BigDecimal("3000.25");
		
		account.setMoney(money);
		account.setDebtorId(1);
	}
	
	@Before
	public void runBeforeInsertTest(){
		accountService.save(account);
	}
	@Test
	public void InsertTest() {
		
	}
}
