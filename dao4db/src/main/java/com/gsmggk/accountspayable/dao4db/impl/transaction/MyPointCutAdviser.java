package com.gsmggk.accountspayable.dao4db.impl.transaction;

import java.lang.reflect.Method;

import javax.inject.Inject;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.stereotype.Component;

import com.gsmggk.accountspayable.dao4api.MyTrans;

@SuppressWarnings("serial")
@Component
public class MyPointCutAdviser extends AbstractPointcutAdvisor {
	private final StaticMethodMatcherPointcut pointcut = new
            StaticMethodMatcherPointcut() {
                @Override
                public boolean matches(Method method, Class<?> targetClass) {
                    return method.isAnnotationPresent(MyTrans.class);
                }

				
            };
            @Inject
            private  MyTransAnalize interceptor;
            
	@Override
	public Pointcut getPointcut() {
		
		return this.pointcut;
	}

	@Override
	public Advice getAdvice() {
		
		return  this.interceptor;
	}

}
