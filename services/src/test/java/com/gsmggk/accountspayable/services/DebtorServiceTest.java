package com.gsmggk.accountspayable.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.gsmggk.accountspayable.dao4api.modelmap.ClerkRepo;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorControl;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorRepo;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorState;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtor;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Boss;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Clerk;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.services.impl.DebtorServiceImpl;
import com.gsmggk.accountspayable.services.util.CurrentLayer;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:services-context.xml")

public class DebtorServiceTest extends AbstractTest {
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

		Debtor model = new Debtor();
		// model.setId(46);
		model.setShortName("Утконосов1");
		model.setFullName("Уткин И. А.");

		Assert.isNull(model.getId(), "insertDebtorTest- debtor.id before test is not null");
		// service.save(model);//admin save
		service.saveDebtor(clerk.getId(), model);// clerk save
		LOGGER.debug("insert debtor: {}", model.getId());
		Assert.isTrue(model.getId() != null, "insertDebtorTest- debtor.id after test is  null");
	}

	@Test
	@Ignore
	@Rollback(true)
	public void updateDebtorTest() {
		Clerk clerk = new Clerk();
		String login = "user";
		String password = "111111";
		clerk = clerkService.loginCheck(login, password);
		String shortName = "Утконосов1";

		Debtor model = new Debtor();
		int id = 46;
		model.setId(id);

		model.setShortName(shortName);
		model.setFullName("Уткин1 И. А.");

		// service.save(model);//admin save
		service.saveDebtor(clerk.getId(), model);// clerk save
		model = service.get(id);
		LOGGER.debug("insert debtor: {}", model.getId());
		Assert.isTrue(model.getShortName().equals(shortName), "Debtor after update mast be equal");
		Assert.isTrue(model.getId() != null, "updateDebtorTest- debtor.id after test is not null");
	}

	@Test
	// @Ignore
	public void getNotDistributeDebtorsTest() {

		Boolean allcated = false;
		ParamsDebtor params = new ParamsDebtor();
		params.setSortShortName(true);
		List<Debtor> debtors = service.getAllocatedDebtors(allcated, params);
		int i = 0;
		while (i < debtors.size()) {

			System.out.println(debtors.get(i));
			i++;
		}

		Assert.notEmpty(debtors, "debtors - must containt data");
	}

	@Test
	// @Ignore
	public void getDebtor4ClerkTest() {
		Integer clerkId = 91;
		Boolean sortControl = true;
		ParamsDebtors4Clerk params = new ParamsDebtors4Clerk();
		params.setSortControl(sortControl);
		params.setSortShortName(true);
		List<DebtorControl> debtors = service.getDebtors4Clerk(clerkId, params);
		DebtorControl debtorControl = new DebtorControl();
		DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
		debtorControl = debtors.get(1);
		Date dateFrom = debtorControl.getControl();
		String d = dFormat.format(dateFrom);
		System.out.println(d);
		int i = 0;
		while (i < debtors.size()) {

			System.out.println(debtors.get(i));
			i++;
		}
		Assert.notEmpty(debtors, "List must be not empty");
	}

	@Test
	public void getTest() {
		Integer id = 1;
		Debtor debtor = service.get(id);
		Assert.notNull(debtor, "debtor after get must exist");
	}

	@Test
	@Ignore
	public void closeDebtorTest() {
		Integer clerkId = 15;// boss
		Integer debtorId = 5;
		service.closeDedtor(clerkId, debtorId);

	}

	@Test
	@Ignore
	@Rollback(true)
	public void reoperDebtorTest() {
		Integer clerkId = 15;// boss
		Integer debtorId = 5;
		service.reopenDedtor(clerkId, debtorId);

	}

	@Test
	@Ignore
	public void getDebtors4Boss() {
		ParamsDebtors4Boss params = new ParamsDebtors4Boss();
		// params.setSearchShortName("%нуш%");
		params.setSortActive(false);
		params.setSortShortName(true);
		// params.setLimit(5);
		List<DebtorState> debtorStates = service.getDebtors4Boss(params);
		int i = 0;
		while (i < debtorStates.size()) {

			System.out.println(debtorStates.get(i));
			i++;
		}
		Assert.notEmpty(debtorStates, "List DebtorState must be not empty");
	}

	// -----------------------report tests-------------------------
	@Test

	public void repoClerksTest() {

		List<ClerkRepo> clerkRepo = clerkService.getClerkRepo();
		System.out.println("repoClerksTest");
		int i = 0;
		while (i < clerkRepo.size()) {

			System.out.println(clerkRepo.get(i));
			i++;
		}
		Assert.notEmpty(clerkRepo, "List ClerkRepo must be not empty");
	}

	@Test
	public void repoDebtorTest() {
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String fromStr="20/04/2017";
		String toStr="21/04/2017";
		Date from = null;
		try {
			from = sdf.parse(fromStr+" 00:00:00");
		} catch (ParseException e) {
						e.printStackTrace();
		};
		Date to = null;
		try {
			to = sdf.parse(toStr+" 29:59:59");
		} catch (ParseException e) {
					e.printStackTrace();
		}
		ParamsDebtor params = new ParamsDebtor();
		params.setSortShortName(true);
		List<DebtorRepo> clerkRepo = service.getDebtorRepo(from,to,params);
		
		System.out.println("repoDebtorTest");
		int i = 0;
		while (i < clerkRepo.size()) {

			System.out.println(clerkRepo.get(i));
			i++;
		}
		Assert.notEmpty(clerkRepo, "List ClerkRepo must be not empty");

	}

}
