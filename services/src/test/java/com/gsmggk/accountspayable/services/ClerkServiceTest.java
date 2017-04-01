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
public class ClerkServiceTest extends AbstractTest {
	@Inject
	private IClerkService clerkService;
	@Inject
	private IRoleService roleService;
	private  Clerk clerk;
	private  Role role;
	private  Clerk clerkAfter;
	private  Role roleAfter;


	private  void runBeforeAll() {
		role = new Role();
		role.setRoleName("Testers");
		roleAfter = new Role();

		clerk = new Clerk();
		clerk.setClerkFullName("Петров Петр Петрович");
		clerk.setClerkLoginName("Tester");
		clerk.setPassword("111111");

		clerkAfter = new Clerk();
	}

	

	
	

}
