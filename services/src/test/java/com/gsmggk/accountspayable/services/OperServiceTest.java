package com.gsmggk.accountspayable.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.Assert;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:services-context.xml")
public class OperServiceTest 
extends AbstractTest
{

	@Inject
	private IOperService operService;
	@Inject
	private IClerkService clerkService;
	@Inject
	private IDebtorService debtorService;
	@Inject
	private IActionService actionService;
	@Inject
	private IRoleService roleService;

	@Test
	@Ignore
	public void allocateDebtor2ClerkTest() {

		String login = "Boss";
		String password = "111111";
		clerkService.loginCheck(login, password);
		// TODO test it
		
		operService.allocateDebtor2Clerk(46, 90);
		
	}

	@Test
	 @Ignore
	public void addOperTest() {
		// log in
		String login = "user1";
		String password = "111111";
		Clerk clerk = new Clerk();
		clerk = clerkService.loginCheck(login, password);
		Assert.notNull(clerk, "clerk -after login not null");
		Assert.isTrue(clerk.getClerkLoginName().equals(login),"login name is same");

		// give debtor
		Integer debtorId = 5;
		Debtor debtor = new Debtor();
		debtor = debtorService.get(debtorId);
		Assert.notNull(debtor, "debtor -must be not null");
		Assert.isTrue(debtor.getId() == debtorId, "Debtor id must be 14");

		// give action
		Integer actionId = 18;
		Action action = new Action();
		List<Action> actions = roleService.getActions4Role(clerk.getRoleId());
		action = actionService.get(actionId);
		Integer duration = action.getDuration();

		// define oper
		//
		Oper oper = new Oper();
		String descr = "Сказал что будет брать кредит для рефинансирования";
		oper.setDebtorId(debtor.getId());
		oper.setActionId(action.getId());
		oper.setOperDesc(descr);

		// add control date
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, duration);
		dt = c.getTime();

		oper.setControlDate(dt);

		operService.addOper(oper);

	}
	
	@Test
	@Ignore
	public void updateOper(){
	Integer clerkId=15;
	Integer operId=46;
	Oper oldOper=operService.getOper(operId);
	oldOper.setControlDate(new Date(new Date().getTime()));
	operService.updateOper(clerkId,oldOper);
	System.out.println(oldOper);
}
}
