package com.gsmggk.accountspayable.dao4db.impl;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
public class MyTransAnalize implements MethodInterceptor{
	
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
//		System.out.println("MyTransAnalize proceed invocation");
		return invocation.proceed();
	}
	}
	
	
	
