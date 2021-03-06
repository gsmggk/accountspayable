package com.gsmggk.accountspayable.services;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Role;

public class RoleServiceTest extends AbstractTest {
	@Inject
	private IRoleService roleService;

	private Role role;
	private String roleName = null;
	private String layer = "WORK";
	private Role roleFromDb;
	private Integer savedRoleId;

	/**
	 * Save Role to database and determine it
	 *
	 */
	private void getRoleFromDB() {
		roleService.save(role);
		savedRoleId = role.getId();
		roleFromDb = roleService.get(savedRoleId);
	}

	@Before
	public void runBeforeTests() {
		role = new Role();
		roleFromDb = new Role();
		// Add value to roleName
		roleName = "My New testeerer role";
		role.setRoleName(roleName);
		role.setLayer(layer);
	}

	
	@Test
	@Rollback(true)
	public void insertTest() {
		getRoleFromDB();

		Assert.notNull(roleFromDb, "IRoleService.save(insert) test- must by not null after save");
		Assert.isTrue(roleFromDb.getRoleName().equals(roleName),
				"IRoleService.save(insert) - roleName must be assigned");
		Assert.isTrue(roleFromDb.getId().equals(savedRoleId), "IRoleService.save(insert) - id must be assigned");

	}

	@Test
	@Rollback(true)
	public void updateTest() {
		roleService.save(role);
		roleName = "TEst1";
		role.setRoleName(roleName);
		getRoleFromDB();

		Assert.notNull(roleFromDb, "IRoleService.save (update) test- must by not null after save");
		Assert.isTrue(roleFromDb.getRoleName().equals(roleName),
				"IRoleService.save (update) - roleName must be assigned");
		Assert.isTrue(roleFromDb.getId().equals(savedRoleId),
				"IRoleService.save(update) - id must be assigned and same");

	}

	@Test
	@Rollback(true)
	public void deleteTest() {
		roleService.save(role);
		Integer savedRoleId = role.getId();
		roleService.delete(role);
		Role roleFromDb = roleService.get(savedRoleId);
		Assert.isNull(roleFromDb, "Role must be null after delete");
	}

	@Test
	public void getAllTest(){
		List<Role> roles=roleService.getAll();
		Assert.notEmpty(roles, "List of roles must be not empty");
	}
	@Test
	public void getLayerTest(){
		
	Integer id=0;
	Role role=roleService.get(id);
	String layer=role.getLayer();
		Assert.isTrue(layer.equals("ADMIN"), "layer admin not equal");
	}
	
}
