package com.gsmggk.accountspayable.services;

import java.util.Map;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Role;
@Transactional
public class RoleServiceTest extends AbstractTest {
	@Inject
	private IRoleService roleService;
	
	private static Role role;

	@BeforeClass
	public static void runBeforeTests() {
		role = new Role();
		String roleName = null;
		// Add value to roleName
		roleName = "TEst";
		role.setRoleName(roleName);
	}

	@Before
	public void runBeforeCreateTest() {
		
	}

	@Test
	@Rollback(true)
	public void CreateTest() {
		roleService.save(role);
		Integer savedRoleId = role.getId();
		Role roleFromDb = roleService.get(savedRoleId);
		roleFromDb.setRoleName(null);
		Assert.notNull(roleFromDb, "roleService.save test- must by not null after save");
		
		//System.out.println(roleFromDb.toString());
		
	}

}
