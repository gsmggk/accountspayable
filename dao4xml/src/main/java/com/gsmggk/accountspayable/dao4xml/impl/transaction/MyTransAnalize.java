package com.gsmggk.accountspayable.dao4xml.impl.transaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

@Component
public class MyTransAnalize implements MethodInterceptor {
	Boolean lock = false;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("MyTransAnalize XML proceed invocation");

		Object obj = new Object();
		if (lock) {
			System.out.println("Wait...");
			while (!lock) {
				// wait

			}

		} else {
			lock = true;
			
				obj = invocation.proceed();
		

				lock = false;

			}

			System.out.println("Proceed invocation");
	

		return obj;
	}

}
