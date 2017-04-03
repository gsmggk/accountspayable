package com.gsmggk.accountspayable.services;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Role;

@Transactional
public class ClerkServiceTest extends AbstractTest {
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
