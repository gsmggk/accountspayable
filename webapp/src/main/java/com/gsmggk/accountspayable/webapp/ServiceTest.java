package com.gsmggk.accountspayable.webapp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.datamodel.Role;
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

Role role=new Role();
role.setRoleName("Hello");
iRoleService.save(role);

		context.close();
		System.out.println("Service test Stoped");
	}
}
