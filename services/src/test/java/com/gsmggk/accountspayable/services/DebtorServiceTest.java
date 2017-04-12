package com.gsmggk.accountspayable.services;

import javax.inject.Inject;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.services.impl.DebtorServiceImpl;
import com.gsmggk.accountspayable.services.util.CurrentLayer;

public class DebtorServiceTest extends AbstractTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtorServiceTest.class);

	@Inject
	public IDebtorService service;
	private static Debtor model;

	@BeforeClass
	public static void runBeforeClass() {
		model = new Debtor();
		model.setShortName("Тестовый №15");
		model.setFullName("Петрученко Игнат Варфоломеевич");
      CurrentLayer.setClerkId(15);
      
	}

	@Test
	// @Ingore
	@Rollback(true)
	public void insertDebtorTest() {
		Assert.isNull(model.getId(), "insertDebtorTest- debtor.id before test is not null");
		service.save(model);
		LOGGER.debug("insert debtor: {}", model.getId());
		Assert.isTrue(model.getId() != null, "insertDebtorTest- debtor.id after test is  null");
	}
	
	
}
