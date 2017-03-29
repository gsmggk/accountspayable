package com.gsmggk.accountspayable.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * For import annotation to child 
 * @author Gena
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:services-context-test.xml")

public class AbstractTest {

	/**
	 * Need for prevent maven test error:
	 * No runnable method 
	 */
	@Test
	public void nullTest() {
	}

}
