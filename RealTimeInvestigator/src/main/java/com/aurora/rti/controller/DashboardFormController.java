package com.aurora.rti.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aurora.rti.service.SessionDetailsService;

@RestController
@RequestMapping("/dashboardFormController")
public class DashboardFormController {
	private static final Logger logger = Logger.getLogger(DashboardFormController.class);
	private SessionDetailsService sessionDetailsService = null;
	 
	@Autowired
	public void setSessionDetailsService(SessionDetailsService sessionDetailsService) {
		this.sessionDetailsService = sessionDetailsService;
	}
	

}
