package org.javatunes.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;


public class ProfilerInterceptor {
	@AroundInvoke
	public Object profile(InvocationContext inv) throws Exception {
		long startTime = 0;
		try {
			startTime=System.currentTimeMillis();
			return inv.proceed();
		} finally {
			long elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("Calling "+inv.getTarget()+"."+inv.getMethod().getName()+" took "+elapsedTime+" milliseconds");
		}
	}
}
