package com.gsmggk.accountspayable.services;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:services-context.xml")
public class Role2ActionTest 
//extends AbstractTest 
{
	@Inject
	private IRoleService roleService;
	@Inject
	private IClerkService clerkService;
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
	//@Ignore
	public void getActions4Role() {
     Integer clerkId=15;
     Clerk clerk=clerkService.get(clerkId);
      Assert.notNull(clerk,"clerk must be not null");
    List<Action> actions= roleService.getActions4Role(clerk.getRoleId());
    int i=0;
	while(i<actions.size()){
		
		System.out.println(actions.get(i));
		i++;
	}
    Assert.notNull(actions,"actions must be not null");
    Integer actionId=11;
   Boolean flag= roleService.chekAction2Role(actionId, clerk.getRoleId());
    Assert.isTrue(flag, "Must have link in role2action");
    
	}

	@Test
	@Rollback(true)
	@Ignore
	public void addAction2RoleTest() {
		
		
		//roleService.save(role);
		//actionService.save(action);
		//roleService.addAction2Role(action.getId(), role.getId());
		//roleService.getActions4Role(role.getId());
		
		//FIXME return after needed
		roleService.addAction2Role(20, 9);
		//roleService.deleteAction2Role(20,9);
		
	}

	@Test
	@Ignore
	public void deleteAction2Role() {
		roleService.save(role);
		actionService.save(action);
		roleService.addAction2Role(action.getId(), role.getId());
		roleService.deleteAction2Role(action.getId(), role.getId());
		
	}
}
