package com.gsmggk.accountspayable.services;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * For import annotation to child
 * 
 * @author Gena
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:services-context.xml")

public abstract class AbstractTest extends AbstractTransactionalJUnit4SpringContextTests{


}
