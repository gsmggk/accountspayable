package com.gsmggk.accountspayable.services;

import java.util.Date;
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

import com.gsmggk.accountspayable.dao4api.maps.DebtorControl;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.services.impl.DebtorServiceImpl;
import com.gsmggk.accountspayable.services.util.CurrentLayer;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:services-context.xml")

public class DebtorServiceTest
 extends AbstractTest
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtorServiceTest.class);

	@Inject
	public IDebtorService service;
	@Inject
	public IClerkService clerkService;
	private Debtor model;

	@Before
	public void runBefore() {

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
		model.setShortName("Уткин");
		model.setFullName("Уткин И. А.");

		Assert.isNull(model.getId(), "insertDebtorTest- debtor.id before test is not null");
		service.save(model);
		LOGGER.debug("insert debtor: {}", model.getId());
		Assert.isTrue(model.getId() != null, "insertDebtorTest- debtor.id after test is  null");
	}

	@Test
	@Ignore
	public void getNotDistributeDebtorsTest() {
		List<Debtor> debtors = service.getAllocatedDebtor(false);
		int i = 0;
		while (i < debtors.size()) {

			System.out.println(debtors.get(i));
			i++;
		}

		Assert.notEmpty(debtors, "debtors - must containt data");
	}

	@Test
	//@Ignore
	public void getDebtor4ClerkTest() {
		Integer clerkId = 91;
		String searchShotName = null;
		String searchFullName = null;
		Date equal2Date = null;
		Boolean sortControl = true;
		Boolean sortShortName = false;
		Boolean sortFullName = null;
		Integer limit=5;
		Integer offset=5;
		List<DebtorControl> debtors = service.getDebtors4Clerk(clerkId, searchShotName, searchFullName, equal2Date,
				sortControl, sortShortName, sortFullName,limit,offset);

		int i = 0;
		while (i < debtors.size()) {

			System.out.println(debtors.get(i));
			i++;
		}
		Assert.notEmpty(debtors, "List must be not empty");
	}
	
	@Test
	public void getTest(){
	 Integer id=1;
	Debtor debtor=service.get(id);
	Assert.notNull(debtor,"debtor after get must exist");
	}
}
