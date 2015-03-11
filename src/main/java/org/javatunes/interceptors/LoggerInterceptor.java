package org.javatunes.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LoggerInterceptor {
	@AroundInvoke
	public Object log(InvocationContext inv) throws Exception {
		System.out.println("Before calling "+inv.getTarget()+"."+inv.getMethod().getName());
		return inv.proceed();
	}
}
