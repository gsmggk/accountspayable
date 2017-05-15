package com.gsmggk.accountspayable.services;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Role;

@ContextConfiguration(locations = "classpath:services-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceXMLTest {

	@Inject
	private IRoleService roleService;
	@Inject
	private IClerkService clerkService;

	@Test
	@Ignore
	public void roleTest() {
		// create role
		Role role = new Role();
		String roleName = "My New test role";
		role.setRoleName(roleName);
		role.setLayer("WORK");
		roleService.save(role);
		// read role
		Integer id = role.getId();
		Assert.notNull(id, "id havn't value");
		Role newRole = roleService.get(id);
		Assert.isTrue(newRole.getRoleName().equals(roleName), "Role name after insert not same");
		// update role
		String layer = "Work1";
		newRole.setLayer(layer);
		roleService.save(newRole);
		newRole = roleService.get(id);
		Assert.isTrue(newRole.getLayer().equals(layer), "Role layer after insert not same");
		// delete role
		roleService.delete(newRole);
		Assert.isNull(roleService.get(id), "Delete role is fail");
	}

	@Test
	@Ignore
	public void clerkTest() {
		// create clerk
		Clerk clerk = new Clerk();

		String clerkLoginName = "Login name";
		clerk.setClerkLoginName(clerkLoginName);
		String clerkFullName = "Full name";
		clerk.setClerkFullName(clerkFullName);
		Integer roleId = 0;
		clerk.setRoleId(roleId);
		clerkService.save(clerk);
		// read clerk
		Integer id = clerk.getId();
		Assert.notNull(id, "id havn't value");
		Clerk newClerk = clerkService.get(id);
		Assert.isTrue(newClerk.getClerkLoginName().equals(clerkLoginName), "Clerk login name after insert not same");
		// update clerk
		String fullName = "Work1";
		newClerk.setClerkFullName(fullName);
		clerkService.save(newClerk);
		newClerk = clerkService.get(id);
		Assert.isTrue(newClerk.getClerkFullName().equals(fullName), "Clerk full name after insert not same");
		// delete clerk
		clerkService.delete(newClerk);
		Assert.isNull(clerkService.get(id), "Delete clerk is fail");
	}
	@Test
	@Ignore
	public void setPasswordTest() {
		Integer clerkId=3;
		String password="111111";
		clerkService.allocatePassword(clerkId, password);
		
	}
	@Test
//	@Ignore
	public void loginCheckTest() {
		
		String login="user1";
		String password="111111";
		Clerk clerk=
		clerkService.loginCheck(login, password);
		Assert.isTrue(clerk.getClerkLoginName().equals(login), "Login chek has misstake");
	}
}
