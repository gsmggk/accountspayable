package com.gsmggk.accountspayable.services;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsmggk.accountspayable.datamodel.Action;




@ContextConfiguration(locations = "classpath:services-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ActionServiceXMLTest {

	@Inject
	private IActionService service;

	@Test
	@Ignore
	public void insertTest() {
		Action action = new Action();
		action.setActionName("Test Action");
		action.setDuration(2);

		service.save(action);
	}
	@Test
	@Ignore
	public void updateTest() {
		Action action = new Action();
		action.setId(3);
		action.setActionName("Test Update Action");
		action.setDuration(4);

		service.save(action);
	}
	@Test
	//@Ignore
	public void deleteTest() {
		Action action = new Action();
		action.setId(2);
		service.delete(action);
	}
}
