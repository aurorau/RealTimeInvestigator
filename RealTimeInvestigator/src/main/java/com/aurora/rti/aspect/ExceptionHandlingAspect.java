package com.aurora.rti.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
@Aspect
public class ExceptionHandlingAspect {
	@Pointcut("execution(* com.aurora.rti.serviceImpl.*.*(..))")
	public void serviceImplExceptionPointcut(){}
	
	@Pointcut("execution(public * loadUserRegistrationDynamicTable(..))")
	public void controllerLoggablePointcut(){}
	
	@Around("serviceImplExceptionPointcut()")
	public Object serviceImplExceptionAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		Object object = null;
		System.err.println("Enter :"+proceedingJoinPoint.getSignature());
		try {
			object =  proceedingJoinPoint.proceed();
		} catch (Exception e) {
			System.err.println("Exception in Service :"+proceedingJoinPoint.getSignature()+"-"+e);
		} 
		System.err.println("Exit :"+proceedingJoinPoint.getSignature());
		return object;
	}
	
	@Around("@annotation(com.aurora.rti.annotation.ControllerLoggable)")
	public ModelAndView controllerLoggableAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		ModelAndView modelAndView = null;
		
		try {
			modelAndView =  (ModelAndView) proceedingJoinPoint.proceed();
		} catch (Exception e) {
			System.err.println("Exception in Controller :"+proceedingJoinPoint.getSignature()+"-"+e);
		} 
		return modelAndView;
	}
}
