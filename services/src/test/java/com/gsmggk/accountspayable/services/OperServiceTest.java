package com.gsmggk.accountspayable.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Oper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:services-context.xml")
public class OperServiceTest
// extends AbstractTest
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
	public void linkDebtor2ClerkTest() {

		String login = "Boss";
		String password = "111111";
		

		Integer debtorId=5;
		Integer clerkId=90;
		operService.linkDebtor2Clerk(clerkService.loginCheck(login, password).getId(),debtorId, clerkId);
		operService.unlinkDebtor2Clerk(clerkService.loginCheck(login, password).getId(),debtorId, clerkId);
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
		Assert.isTrue(clerk.getClerkLoginName().equals(login), "login name is same");

		// give debtor
		Integer debtorId = 2;
		Debtor debtor = new Debtor();
		debtor = debtorService.get(debtorId);
		Assert.notNull(debtor, "debtor -must be not null");
		Assert.isTrue(debtor.getId() == debtorId, "Debtor id must be 14");

		// give action
		Integer actionId = 19;
		Action action = new Action();
		List<Action> actions = roleService.getActions4Role(clerk.getRoleId());
		action = actionService.get(actionId);
		Integer duration = action.getDuration();

		// define oper
		//
		Oper oper = new Oper();
		String descr = "Обязательно заплатит";
		oper.setDebtorId(debtor.getId());
		oper.setActionId(action.getId());
		oper.setClerkId(clerk.getId());
		oper.setOperDesc(descr);

		// add control date
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, duration);
		dt = c.getTime();

		oper.setControlDate(dt);

		operService.addOper(oper);
		Integer newOperId = oper.getId();
		Oper newOper = operService.getOper(newOperId);
		Assert.notNull(newOper, "Oper after insert mast be not null");
	}

	@Test
	 @Ignore
	public void updateOperTest() {
		Integer clerkId = 91;
		Integer operId = 71;
		Oper oldOper = operService.getOper(operId);
		// oldOper.setControlDate(new Date(new Date().getTime()));
		String operDesc = oldOper.getOperDesc();
		operDesc += " Дополнительные сведения";
		oldOper.setOperDesc(operDesc);
		operService.updateOper(clerkId, oldOper);
		System.out.println(oldOper);
	}
	@Test
	@Ignore
	public void deleteOperTest() {
		Integer clerkId = 15;
		Integer operId = 76;
		Oper oldOper = operService.getOper(operId);
		
		operService.deleteOper(clerkId, oldOper);
		System.out.println(oldOper);
	}
	
	
	@Test
	@Ignore
	public void getOpers4DebtorTest() {
		Integer debtorId = 5;
		List<Oper> opers = operService.getOpers4Debtor(debtorId);
		int i = 0;
		while (i < opers.size()) {

			System.out.println(opers.get(i));
			i++;
		}

		Assert.notEmpty(opers, "Opers for debtor must be not null");
	}

}
