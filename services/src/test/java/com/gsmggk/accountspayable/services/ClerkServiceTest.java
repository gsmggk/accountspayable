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
	private static Clerk clerk;
	private static Role role;
	private static Clerk clerkAfter;
	private static Role roleAfter;

	@BeforeClass
	public static void runBeforeAll() {
		role = new Role();
		role.setRoleName("Testers");
		roleAfter = new Role();

		clerk = new Clerk();
		clerk.setClerkFullName("Петров Петр Петрович");
		clerk.setClerkLoginName("Tester");
		clerk.setPassword("111111");

		clerkAfter = new Clerk();
	}

	@Test
	public void roleBeforeInsertTest() {
		System.out.println("rolePreInsertTest-" + role.getId());
		Assert.isNull(role.getId(), "role.id must be null before insert");
	}

	@Test
	@Rollback(true)
	public void roleInsertTest() {
		roleService.save(role);
		System.out.println("RoleInsertTest-" + role);
		Assert.notNull(role, "Mole must be not null");
		Assert.notNull(role.getId(), "role.id must be not null");
		Assert.notNull(role.getRoleName(), "role.role_name must be not null");

	}

	@Test
	public void roleSaveTest() {
		roleAfter = roleService.get(role.getId());
		Assert.notNull(roleAfter, "roleAfter must be not null");
		Assert.isTrue(roleAfter.getId() == role.getId(), "role.id must be equals");
		Assert.isTrue(roleAfter.getRoleName() == role.getRoleName(), "role.getRoleName must be equals");

	}

	@Test
	@Rollback(true)
	public void clerkBeforeInsertTest() {
		Assert.isNull(clerk.getId(), "clerk.id must be null before insert");

	}

	@Test
	@Rollback(true)
	public void checkInsertClerk() {
		clerk.setRoleId(role.getId());
		clerkService.save(clerk);

	}

	@Test
	public void checkLogin() {
		String login = "Tester";
		String password = "111111";

		clerkService.loginClerk(login, password);
	}

}
