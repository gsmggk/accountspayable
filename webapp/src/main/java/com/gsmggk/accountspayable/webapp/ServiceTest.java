package com.gsmggk.accountspayable.webapp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Role;
import com.gsmggk.accountspayable.services.IActionService;
import com.gsmggk.accountspayable.services.IGenericService;
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
        IActionService iActionService=context.getBean(IActionService.class);
	// TEst Role
       //	System.out.println(iRoleService.get(1));
		//	System.out.println(iRoleService.getAll());
	 /*	Role role = new Role();
		role.setRoleName("Cотрудник Bезопасности");

		role = iRoleService.get(9);
		role.setRoleName("Cотрудник Bезопасности");

		iRoleService.save(role);
		// iRoleService.delete(role);
		System.out.println(iRoleService.getAll());*/
       
      // Test Action  
        //System.out.println(iActionService.get(1));
      /*  System.out.println(iActionService.getAll());
		Action action=new Action();
		action.setActionName("Начало работы");
		iActionService.save(action);
		System.out.println(iActionService.getAll());*/
		
        @SuppressWarnings("unchecked")
		IGenericService<Clerk> iClerkService=(IGenericService<Clerk>) context.getBean(IGenericService.class);
        Clerk clerk=new Clerk();
		iClerkService.delete(clerk);
        
        context.close();
	}
}
