package com.gsmggk.accountspayable.webapp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.datamodel.Role;
import com.gsmggk.accountspayable.services.IActionService;
import com.gsmggk.accountspayable.services.IClerkService;
import com.gsmggk.accountspayable.services.IDebtorService;
import com.gsmggk.accountspayable.services.IRoleService;
import com.gsmggk.accountspayable.services.util.CurrentLayer;

public class ServiceTest {
	public static void main(String[] args) {
		System.out.println("Service test Startted");
		/*
		 * Init context
		 * 
		 */
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("web-context.xml");

	//	IRoleService iRoleService = context.getBean(IRoleService.class);
	//	IActionService iActionService = context.getBean(IActionService.class);
		IClerkService iClerkService = context.getBean(IClerkService.class);
		IDebtorService iDebtorService=context.getBean(IDebtorService.class);

		Clerk clerk = new Clerk();
		String login = "Boss";
		String password = "11111";
		clerk = iClerkService.loginCheck(login, password);
		CurrentLayer.setClerkId(clerk.getId());
		Debtor model = new Debtor();
		model.setShortName("Гончарук");
		model.setFullName("Гончарук Игнат Варфоломеевич");
        iDebtorService.save(model);
		
		// close app
		context.close();
		System.out.println("Service test Stoped");
	}
}
