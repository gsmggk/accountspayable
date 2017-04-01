package com.gsmggk.accountspayable.services;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Role;

@Transactional
public class RoleServiceTest extends AbstractTest {
	@Inject
	private IRoleService roleService;

	private  Role role;
	public  String roleName = null;

	@Before
	public  void runBeforeTests() {
		role = new Role();

		// Add value to roleName
		roleName = "TEst";
		role.setRoleName(roleName);
	}

	@Test
	@Rollback(true)
	public void insertTest() {
		roleService.save(role);
		Integer savedRoleId = role.getId();
		Role roleFromDb = roleService.get(savedRoleId);

		Assert.notNull(roleFromDb, "IRoleService.save(insert) test- must by not null after save");
		Assert.isTrue(roleFromDb.getRoleName().equals(roleName), "IRoleService.save(insert) - roleName must be assigned");
		Assert.isTrue(roleFromDb.getId().equals(savedRoleId), "IRoleService.save(insert) - id must be assigned");

	}
	@Test
	@Rollback(true)
	public void updateTest() {
		roleService.save(role);
		roleName = "TEst1";
		role.setRoleName(roleName);
		roleService.save(role);
		Integer savedRoleId = role.getId();
		Role roleFromDb = roleService.get(savedRoleId);

		Assert.notNull(roleFromDb, "IRoleService.save (update) test- must by not null after save");
		Assert.isTrue(roleFromDb.getRoleName().equals(roleName), "IRoleService.save (update) - roleName must be assigned");
		Assert.isTrue(roleFromDb.getId().equals(savedRoleId), "IRoleService.save(update) - id must be assigned");

	}

}
