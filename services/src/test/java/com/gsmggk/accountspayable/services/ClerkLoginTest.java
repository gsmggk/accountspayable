package com.gsmggk.accountspayable.services;

import javax.inject.Inject;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Role;

@Transactional
public class ClerkLoginTest extends AbstractTest {

	@Inject
	private IClerkService clerkService;
	@Inject
	private  IRoleService roleService;
	private  Clerk clerk;
	private  Role role;
	private  Clerk clerkAfter;
	private  Role roleAfter;

	

	@Test
	@Rollback(true)
//	@Test(expected=IllegalArgumentException.class)
	public void loginTest() {
		role = new Role();
		role.setRoleName("Testers");
		roleService.save(role);
		Integer role_id = role.getId();

		clerk = new Clerk();
		clerk.setClerkFullName("Петров Петр Петрович");
		clerk.setClerkLoginName("Tester");
		clerk.setPassword("111111");
		clerk.setRoleId(role_id);
		clerkService.save(clerk);
// for null+null
		String login = "Tester";
		String password = "111111";
	
		clerkService.loginClerk(login, password);
		Assert.isTrue(clerkService.loginClerk(login, password),"login ok");
		
		
	}
	

}
