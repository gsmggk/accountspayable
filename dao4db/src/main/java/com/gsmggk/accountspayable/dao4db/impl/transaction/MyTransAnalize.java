package com.gsmggk.accountspayable.dao4db.impl.transaction;





import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
public class MyTransAnalize implements MethodInterceptor{
	

	
	@Override
	@Transactional
	public Object invoke(MethodInvocation invocation) throws Throwable {
	System.out.println("MyTransAnalize DB proceed invocation");
  

	Object obj=invocation.proceed();
		return obj;
	}
	}
	
	
	
