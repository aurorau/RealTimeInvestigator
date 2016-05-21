package com.aurora.rti.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aurora.rti.service.SessionDetailsService;
import com.aurora.rti.util.EventDetailsDTO;
import com.aurora.rti.util.GetHeaders;
import com.aurora.rti.util.ResponseClass;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/dashboardFormController")
public class DashboardFormController {
	private static final Logger logger = Logger.getLogger(DashboardFormController.class);
	private SessionDetailsService sessionDetailsService = null;
	 
	@Autowired
	public void setSessionDetailsService(SessionDetailsService sessionDetailsService) {
		this.sessionDetailsService = sessionDetailsService;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/heartBeat")
    public ResponseEntity<ResponseClass> heartBeat(HttpServletRequest request, HttpServletResponse response){
		HttpHeaders headers = new GetHeaders().getHeaders();
		ResponseClass res = new ResponseClass();
		
		String status = sessionDetailsService.heartBeat(request);
		
		return new ResponseEntity<ResponseClass>(res,headers,HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/postEventDetails")
    public ResponseEntity<ResponseClass> postEventDetails(HttpServletRequest request, HttpServletResponse response){

		HttpHeaders headers = new GetHeaders().getHeaders();
		ResponseClass res = new ResponseClass();
		
		try{
	        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String json = "";
	        if(br != null){
	            json = br.readLine();
	        }
	        ObjectMapper mapper = new ObjectMapper();
	        
	        EventDetailsDTO dto = mapper.readValue(json, EventDetailsDTO.class);
	        
	        dto.setUserAgent(request.getHeader("User-Agent"));
	        dto.setReferer(request.getHeader("referer"));
	        dto.setxForwarded(request.getHeader("X-FORWARDED-FOR"));
	        dto.setRemoteAddress(request.getRemoteAddr());
	        
	        if(dto.getSessionID() == null || dto.getSessionID().equalsIgnoreCase("")){
	        	dto.setSessionID(request.getSession().getId());
	        }
	        
			String res1 = sessionDetailsService.saveSessionDetails(dto);
			 
			res.setStatus(res1);
			res.setResponce(dto.getSessionID());
	        
		} catch(Exception e){
			logger.error("++++++++ Error in postEventDetails in DashboardFormController");
		}

		return new ResponseEntity<ResponseClass>(res,headers,HttpStatus.CREATED);
	}
}
