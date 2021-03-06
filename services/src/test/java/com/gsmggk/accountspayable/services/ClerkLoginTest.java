package com.gsmggk.accountspayable.services;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Role;
import com.gsmggk.accountspayable.services.impl.exceptions.MyBadLoginNameException;
import com.gsmggk.accountspayable.services.impl.exceptions.MyBadPasswordException;



public class ClerkLoginTest extends AbstractTest {

	@Inject
	private  IClerkService clerkService;
	@Inject
	private  IRoleService roleService;
	private  Clerk clerk;
	private  Role role;
	private  Clerk clerkAfter;
	


	@Rollback(true)
	private  void initData() {
		role = new Role();
		role.setRoleName("Testers");
		role.setLayer("WORK");
		roleService.save(role);
		Integer role_id = role.getId();

		clerk = new Clerk();
		clerkAfter = new Clerk();
		clerk.setClerkFullName("Петров Петр Петрович");
		clerk.setClerkLoginName("Tester");
	
		clerk.setRoleId(role_id);
		clerkService.save(clerk);
		clerkService.allocatePassword(clerk.getId(), "111111");
	}

	
	
	@Test
		public void allOk() {
		initData();
		String login = "Tester";
		String password = "111111";

		clerkAfter=clerkService.loginCheck(login, password);
		Assert.notNull(clerkAfter, "loginCheck<>null - login ok");
	}
	
	
	 @Test(expected=IllegalArgumentException.class)
	public void loginIsNull() {
		 initData();
		String login = null;
		String password = null;

		clerkAfter=clerkService.loginCheck(login, password);
	
	}
	
	 @Test(expected=MyBadLoginNameException.class)
		public void loginIsWrong() {
			 initData();
			String login = "Tester1";
			String password = null;

			clerkAfter=clerkService.loginCheck(login, password);
		
		}
	 @Test(expected=MyBadPasswordException.class)
		public void passwordIsWrong() {
			 initData();
			String login = "Tester";
			String password = "222";

			clerkAfter=clerkService.loginCheck(login, password);
		
		}
}
