package com.gsmggk.accountspayable.services;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.services.impl.DebtorServiceImpl;
import com.gsmggk.accountspayable.services.util.CurrentLayer;



public class DebtorServiceTest extends AbstractTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtorServiceTest.class);

	@Inject
	public IDebtorService service;
	@Inject
	public  IClerkService clerkService;
	private  Debtor model;

	@Before 
	public  void runBefore() {
       
	}

	@Test
	 @Ignore
	@Rollback(true)
	public void insertDebtorTest() {
		Clerk clerk = new Clerk();
		String login = "Boss";
		String password = "111111";
		clerk = clerkService.loginCheck(login, password);
		CurrentLayer.setClerkId(clerk.getId());
		Debtor model = new Debtor();
		model.setShortName("Янушкевич");
		model.setFullName("Янушкевич Вера Варфоломеевна");
    
		
		Assert.isNull(model.getId(), "insertDebtorTest- debtor.id before test is not null");
		service.save(model);
		LOGGER.debug("insert debtor: {}", model.getId());
		Assert.isTrue(model.getId() != null, "insertDebtorTest- debtor.id after test is  null");
	}

	@Test
	@Ignore
	public void getNotDistributeDebtorsTest() {
		List<Debtor> debtors = service.getAllocatedDebtor(false);
		System.out.println(debtors);
		Assert.notEmpty(debtors,"debtors - must containt data");
	}

	
}
