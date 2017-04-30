package com.gsmggk.accountspayable.services;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:services-context.xml")
public class AllocatePasswordTest 
//extends AbstractTest 
{
	@Inject
	private IClerkService service;

	@Test

	public void allocatePasswwordTest() {
		Integer clerkId = 91;
		String password = "111111";
		service.allocatePassword(clerkId, password);

	}
}
