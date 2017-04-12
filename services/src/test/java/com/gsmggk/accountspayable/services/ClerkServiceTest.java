package com.gsmggk.accountspayable.services;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import com.gsmggk.accountspayable.datamodel.Clerk;


public class ClerkServiceTest extends AbstractTest {
	@Inject
	private IClerkService clerkService;

	private Clerk clerk;
	private Clerk clerkFromDb;

	private Integer savedClerkId;

	// Describe fields
	
	private Integer clerkId;
	private String clerkLoginName;
	private String password;
	private Integer clerkRoleId;
	private String clerkFullName;

	@Before
	public void runBeforeTests() {
		clerk = new Clerk();

		clerkFromDb = new Clerk();

		clerkLoginName = "Tester";
		password = "111111";
		clerkFullName = "Петров Петр Петрович";

		clerk.setClerkFullName(clerkFullName);
		clerk.setClerkLoginName(clerkLoginName);
		// clerk.setPassword();

		clerkFromDb = new Clerk();
	}

	/**
	 * Save Clerk to database and determine it
	 *
	 */
	private void getClerkFromDB() {
		clerkService.save(clerk);
		savedClerkId = clerk.getId();
		clerkFromDb = clerkService.get(savedClerkId);
	}

	@Test
	@Rollback(true)
	public void insertTest() {
		getClerkFromDB();

		Assert.notNull(clerkFromDb, "IClerkService.save(insert) test- must by not null after save");
		Assert.isTrue(clerkFromDb.getClerkLoginName().equals(clerkLoginName),
				"IClerkService.save(insert) - clerkLoginName must be assigned");
		Assert.isTrue(clerkFromDb.getClerkFullName().equals(clerkFullName),
				"IClerkService.save(insert) - clerkFullName must be assigned");

		Assert.isTrue(clerkFromDb.getId().equals(savedClerkId), "IClerkService.save(insert) - id must be assigned");

	}

	@Test
	@Ignore
	@Rollback(true)
	public void updateTest() {
		clerkService.save(clerk);
		clerkLoginName = "TEst1";
		clerkFullName = "whejwhjdwhe wjkefkjweh";
		clerk.setClerkLoginName(clerkLoginName);
		clerk.setClerkFullName(clerkFullName);
		getClerkFromDB();

		Assert.notNull(clerkFromDb, "IClerkService.save (update) test- must by not null after save");
		Assert.isTrue(clerkFromDb.getClerkLoginName().equals(clerkLoginName),
				"IClerkService.save (update) - clerkLoginName must be assigned and same");
		Assert.isTrue(clerkFromDb.getClerkFullName().equals(clerkFullName),
				"IClerkService.save (update) - clerkFullName must be assigned and same");
		Assert.isTrue(clerkFromDb.getId().equals(savedClerkId),
				"IClerkService.save(update) - id must be assigned and same");

	}

	@Test
	@Rollback(true)
	@Ignore
	public void deleteTest() {
		clerkService.save(clerk);
		Integer savedClerkId = clerk.getId();
		clerkService.delete(clerk);
		Clerk clerkFromDb = clerkService.get(savedClerkId);
		Assert.isNull(clerkFromDb, "Clerk must be null after delete");
	}

}
