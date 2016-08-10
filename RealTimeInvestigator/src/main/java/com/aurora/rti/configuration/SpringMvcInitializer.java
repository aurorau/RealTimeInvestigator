package com.aurora.rti.configuration;

import javax.servlet.Filter;
import org.springframework.mobile.device.DeviceResolverRequestFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RTIConfiguration.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
	@Override
	protected Filter[] getServletFilters() {
		return new Filter[]{new DeviceResolverRequestFilter()};
	}
}
