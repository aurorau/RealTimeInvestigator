package com.aurora.rti.configuration;

import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.aurora.rti.aspect.ExceptionHandlingAspect;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.aurora.rti")
public class AspectConfiguration {
	private ExceptionHandlingAspect exceptionHandlingAspect = null;

	@Autowired
	public void setExceptionHandlingAspect(ExceptionHandlingAspect exceptionHandlingAspect) {
		this.exceptionHandlingAspect = exceptionHandlingAspect;
	}
	
	public void addAOP(AspectJProxyFactory factory) {
		factory.addAspect(exceptionHandlingAspect);
	}
}
