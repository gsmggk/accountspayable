package com.gsmggk.accountspayable.services;

import java.util.List;

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
	@Inject
	private IRoleService roleService;
	
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
		 clerk.setPassword(password);

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

	@Test
	@Ignore
	public void checkAction4ClerkTest(){
		Integer clerkId=91;
		Integer actionId=19;
		
		Clerk clerk=clerkService.get(clerkId);
		
	Integer roleId = clerk.getRoleId();

	Boolean chekAction2Role = roleService.chekAction2Role(actionId, roleId);
	Assert.isTrue(chekAction2Role, "Link must exist");	
	}
	@Test
	public void getAllTest(){
		List<Clerk> clerks=clerkService.getAll();
		Assert.notEmpty(clerks,"List of clerks must be not empty");
	}


	@Test
	public void getClerks4DebtorTest(){
		
	Integer debtorId=44;
	List<Clerk> clerks=	clerkService.getClerks4Debtor(debtorId);
	Assert.notEmpty(clerks,"Clerk list must be not empty");
	}
}
