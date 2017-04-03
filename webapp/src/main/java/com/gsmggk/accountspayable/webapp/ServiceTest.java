package com.gsmggk.accountspayable.webapp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.services.IActionService;
import com.gsmggk.accountspayable.services.IClerkService;
import com.gsmggk.accountspayable.services.IRoleService;

public class ServiceTest {
	public static void main(String[] args) {
		System.out.println("Service test Startted");
		/*
		 * Init context
		 * 
		 */
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("web-context.xml");

		IRoleService iRoleService = context.getBean(IRoleService.class);
		IActionService iActionService = context.getBean(IActionService.class);
		IClerkService iClerkService = context.getBean(IClerkService.class);

		// Clerk clerk= new Clerk();

		// iClerkService.save(clerk);
		// TEst Role
		// System.out.println(iRoleService.get(1));
		// System.out.println(iRoleService.getAll());
		/*
		 * Role role = new Role(); role.setRoleName("Cотрудник Bезопасности");
		 * 
		 * role = iRoleService.get(9);
		 * role.setRoleName("Cотрудник Bезопасности");
		 * 
		 * iRoleService.save(role); // iRoleService.delete(role);
		 * System.out.println(iRoleService.getAll());
		 */

		// Test Action
		// System.out.println(iActionService.get(1));
		/*
		 * System.out.println(iActionService.getAll()); Action action=new
		 * Action(); action.setActionName("Начало работы");
		 * iActionService.save(action);
		 * System.out.println(iActionService.getAll());
		 */

		@SuppressWarnings("unchecked")
		// IAbstractService<Clerk> iClerkService = (IAbstractService<Clerk>)
		// context.getBean(IAbstractService.class);
		Clerk clerk = new Clerk();
	//	iClerkService.delete(clerk);

		context.close();
	}
}
