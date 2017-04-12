package com.gsmggk.accountspayable.services;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsmggk.accountspayable.datamodel.Role;

@ContextConfiguration(locations = "classpath:services-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RoleServiceXMLTest {

	@Inject
	private IRoleService service;

	@Test
	
	public void insertTest() {
		Role role = new Role();
		role.setRoleName("My New test role");
		service.save(role);

	}
}
