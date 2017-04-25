package com.gsmggk.accountspayable.services;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Clerk;

public class ActionsServiceTest extends AbstractTest {
	@Inject
	private IActionService modelService;
	@Inject
	private IRoleService roleService;
	
	private Action model;
	private Action modelFromDb;

	private Integer savedModelId;

	// Describe fields

	private Integer modelId;
	private String modelName;

	private Integer duration;

	@Before
	public void runBeforeTests() {
		model = new Action();

		modelFromDb = new Action();
		// Init fields
		modelName = "Tester";
		duration = 15;

		model.setActionName(modelName);
		model.setDuration(duration);

		modelFromDb = new Action();
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
		Assert.isTrue(modelFromDb.getActionName().equals(modelName),
				"IClerkService.save(insert) - clerkLoginName must be assigned");
		Assert.isTrue(modelFromDb.getDuration().equals(duration),
				modelFromDb.getClass().getSimpleName() + "..save(insert) - clerkFullName must be assigned");

		Assert.isTrue(modelFromDb.getId().equals(savedModelId),
				modelFromDb.getClass().getSimpleName() + "..save(insert) - id must be assigned");

	}

	@Test
	@Rollback(true)
	public void updateTest() {
		modelService.save(model);

		modelName = "TEst1";
		duration = 1;

		model.setActionName(modelName);
		model.setDuration(duration);

		getModelFromDB();

		Assert.notNull(modelFromDb,
				modelFromDb.getClass().getSimpleName() + "..save (update) test- must by not null after save");
		Assert.isTrue(modelFromDb.getActionName().equals(modelName),
				modelFromDb.getClass().getSimpleName() + "..save (update) - clerkLoginName must be assigned and same");
		Assert.isTrue(modelFromDb.getDuration().equals(duration),
				modelFromDb.getClass().getSimpleName() + "..save (update) - clerkFullName must be assigned and same");
		Assert.isTrue(modelFromDb.getId().equals(savedModelId),
				modelFromDb.getClass().getSimpleName() + "..save(update) - id must be assigned and same");

	}

	@Test
	@Rollback(true)

	public void deleteTest() {
		modelService.save(model);
		Integer savedModelId = model.getId();
		modelService.delete(model);
		Action modelFromDb = modelService.get(savedModelId);
		Assert.isNull(modelFromDb, "Model " + model.getClass().getSimpleName() + " must be null after delete");
	}
	@Test
	public void getAllTest(){
		List<Action> actions=modelService.getAll();
		Assert.notEmpty(actions,"List of actions must be not empty");
	}
	@Test
	public void getActions4RoleTest(){
		Integer id=10;
		List<Action> actions=roleService.getActions4Role(id);
		Assert.notEmpty(actions,"List of actions must be not empty");
	}
	@Test
	public void getTest(){
		Integer id=9;
		Action action=modelService.get(id);
		Assert.notNull(action,"List of actions must be not empty");
	}
}
