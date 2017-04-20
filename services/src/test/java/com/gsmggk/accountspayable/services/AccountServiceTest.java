package com.gsmggk.accountspayable.services;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Account;
import com.gsmggk.accountspayable.datamodel.Action;

public class AccountServiceTest extends AbstractTest {

	@Inject
	private IAccountService modelService;
	private Account model;
	private Account modelFromDb;

	private Integer savedModelId;

	// Describe fields

	private Integer modelId;
	private String modelName;
	private Integer debtorId;
	private BigDecimal money;

	@Before
	public void runBeforeTests() {
		model = new Account();

		modelFromDb = new Account();
		// Init fields
		modelName = "Tester";
		money = new BigDecimal("3000.25");
		debtorId=2;
		
		model.setAccountName(modelName);
		model.setSumm(money);
		model.setDebtorId(debtorId);
		
		modelFromDb = new Account();
	}

	/**
	 * Save Model to database and determine it
	 *
	 */
	private void getModelFromDB() {
		modelService.save(model);
		savedModelId = model.getId();
		modelFromDb = modelService.get(savedModelId);
	}

	@Test
	@Rollback(true)
	public void insertTest() {
		getModelFromDB();

		Assert.notNull(modelFromDb,
				modelFromDb.getClass().getSimpleName() + ".save(insert) test- must by not null after save");
		Assert.isTrue(modelFromDb.getAccountName().equals(modelName),
				"IClerkService.save(insert) - AccountName must be assigned");
		Assert.isTrue(modelFromDb.getSumm().equals(money),
				modelFromDb.getClass().getSimpleName() + ".save(insert) - Summ must be assigned");

		Assert.isTrue(modelFromDb.getId().equals(savedModelId),
				modelFromDb.getClass().getSimpleName() + ".save(insert) - id must be assigned");

	}

	@Test
	@Rollback(true)
	public void updateTest() {
		modelService.save(model);

		modelName = "TEst1";
		money = new BigDecimal("3300.35");

		model.setAccountName(modelName);
		model.setSumm(money);

		getModelFromDB();

		Assert.notNull(modelFromDb,
				modelFromDb.getClass().getSimpleName() + "..save (update) test- must by not null after save");
		Assert.isTrue(modelFromDb.getAccountName().equals(modelName),
				modelFromDb.getClass().getSimpleName() + "..save (update) - AccountName must be assigned and same");
		Assert.isTrue(modelFromDb.getSumm().equals(money),
				modelFromDb.getClass().getSimpleName() + "..save (update) - Summ must be assigned and same");
		Assert.isTrue(modelFromDb.getId().equals(savedModelId),
				modelFromDb.getClass().getSimpleName() + "..save(update) - id must be assigned and same");

	}

	@Test
	@Rollback(true)

	public void deleteTest() {
		modelService.save(model);
		Integer savedModelId = model.getId();
		modelService.delete(model);
		Account modelFromDb = modelService.get(savedModelId);
		Assert.isNull(modelFromDb, "Model " + model.getClass().getSimpleName() + " must be null after delete");
	}

	@Test
	public void getAllTest() {
		List<Account> actions = modelService.getAll();
		Assert.notEmpty(actions, "List of actions must be not empty");
	}

	@Test
	public void getAccounts4DebtorTest() {
		Integer debtorId = 1;
		List<Account> accounts = modelService.getAccounts4Debtor(debtorId);
		Assert.notEmpty(accounts, "list must be not empty");
		System.out.println(accounts);
	}

}
