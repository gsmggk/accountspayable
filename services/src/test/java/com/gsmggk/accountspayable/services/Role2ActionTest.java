package com.gsmggk.accountspayable.services;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Role;

@Transactional
public class Role2ActionTest extends AbstractTest {
	@Inject
	private IRoleService roleService;
	@Inject
	private IActionService actionService;
	private Role role;
	private Action action;

	private static String roleName;
	private static String actionName;

	@BeforeClass
	public static void runBeforeClass() {
		roleName = "role2action";
		actionName = "test action";
	}

	@Before
	public void runBefore() {
		role = new Role();
		action = new Action();
		role.setRoleName(roleName);
		action.setActionName(actionName);

	}

	@Test
	@Ignore
	public void getActions4Role() {

	}

	@Test
	@Rollback(true)
	public void addAction2Role() {
		roleService.save(role);
		actionService.save(action);
		roleService.addAction2Role(action.getId(), role.getId());
		roleService.getActions4Role(role.getId());
	}

	@Test
	
	public void deleteAction2Role() {
		roleService.save(role);
		actionService.save(action);
		roleService.addAction2Role(action.getId(), role.getId());
		roleService.deleteAction2Role(action.getId(), role.getId());
		
	}
}
