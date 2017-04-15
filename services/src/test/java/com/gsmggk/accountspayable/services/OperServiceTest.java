package com.gsmggk.accountspayable.services;

import javax.inject.Inject;

import org.junit.Test;

public class OperServiceTest extends AbstractTest {
	
	@Inject
	private IOperService operService;
	
	@Test
	public void allocateDebtor2ClerkTest(){
		operService.allocateDebtor2Clerk(4, 91);
	}

}
