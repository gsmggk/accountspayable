package com.gsmggk.accountspayable.webapp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceTest {
	public static void main(String[] args) {
		
		
		ClassPathXmlApplicationContext  context = new 
				ClassPathXmlApplicationContext("web-context.xml");
		
		System.out.print("Service test Startted");
	}
}
