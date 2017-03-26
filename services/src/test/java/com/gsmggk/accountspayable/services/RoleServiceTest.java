package com.gsmggk.accountspayable.services;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Role;

public class RoleServiceTest extends AbstractTest {
	@Inject
	private IRoleService roleService;

	@Test
	public void CreateTest() {
		Role role = new Role();
		String roleName = null;
		// Add value to roleName
        roleName="TEst";
		role.setRoleName(roleName);
		roleService.save(role);

		Integer savedRoleId = role.getId();
		Role roleFromDb = roleService.get(savedRoleId);
		
		Assert.notNull(roleFromDb, "role must be saved");
	}

}
