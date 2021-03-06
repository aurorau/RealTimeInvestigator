package com.aurora.rti.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aurora.rti.service.CommonService;
import com.aurora.rti.service.SessionDetailsService;
import com.aurora.rti.util.EventDetailsDTO;
import com.aurora.rti.util.ResponseClass;

@Controller
@RequestMapping("/api")
public class RTIFormController {
	private SessionDetailsService sessionDetailsService = null;
	private CommonService commonService = null;
	 
	@Autowired
	public void setSessionDetailsService(SessionDetailsService sessionDetailsService) {
		this.sessionDetailsService = sessionDetailsService;
	}
	
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/heartBeat")
    public @ResponseBody ResponseClass heartBeat(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		ResponseClass res = new ResponseClass();
		
		String status = sessionDetailsService.heartBeat(request);
		res.setStatus(status);
		
		return res;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getSessionJSandCSSstatus")
    public @ResponseBody ResponseClass getSessionJSandCSSstatus(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		ResponseClass res = new ResponseClass();
		
		Map<String, String> jsCssStatusMap = sessionDetailsService.getSessionJSandCSSstatus(request);
		res.setResponce(jsCssStatusMap);
		
		return res;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/postEventDetails")
    public @ResponseBody ResponseClass postEventDetails(HttpServletRequest request, HttpServletResponse response,Device device) throws Exception{
		
		ResponseClass res = new ResponseClass();
	
		
		EventDetailsDTO dto = new EventDetailsDTO();
		dto.setCoordinateX(request.getParameter("coordinateX"));
		dto.setCoordinateY(request.getParameter("coordinateY"));
		dto.setCountry(request.getParameter("country"));
		dto.setElementClass(request.getParameter("elementClass"));
		dto.setElementHeight(request.getParameter("elementHeight"));
		dto.setElementId(request.getParameter("elementId"));
		dto.setElementOffsetLeft(request.getParameter("elementOffsetLeft"));
		dto.setElementOffsetTop(request.getParameter("elementOffsetTop"));
		dto.setElementScrollTop(request.getParameter("elementScrollTop"));
		dto.setElementWidth(request.getParameter("elementWidth"));
		dto.setEventTriggeredTime(request.getParameter("eventTriggeredTime"));
		dto.setEventType(request.getParameter("eventType"));
		dto.setImageName(request.getParameter("imageName"));
		dto.setNumberOfFingers(request.getParameter("numberOfFingers"));
		dto.setOrientation(request.getParameter("orientation"));
		dto.setScreenHeight(request.getParameter("screenHeight"));
		dto.setScreenWidth(request.getParameter("screenWidth"));
		dto.setScrollTopPx(request.getParameter("scrollTopPx"));
		dto.setSessionID(request.getParameter("sessionID"));
		dto.setTagName(request.getParameter("tagName"));
		dto.setTimeZoneOffset(request.getParameter("timeZoneOffset"));
		dto.setViewportHeight(request.getParameter("viewportHeight"));
		dto.setViewportWidth(request.getParameter("viewportWidth"));
		dto.setReferer(request.getHeader("referer"));
		dto.setRemoteAddress(request.getRemoteAddr());
		dto.setUserAgent(request.getHeader("User-Agent"));
		dto.setxForwarded(request.getHeader("X-FORWARDED-FOR"));
		dto.setDeviceType(commonService.deviceIdentification(device));
		dto.setDevicePlatform(device.getDevicePlatform().toString());
		dto.setCssStatus(request.getParameter("cssStatus"));
		
        if(dto.getSessionID() == null || dto.getSessionID().equalsIgnoreCase("")){
        	dto.setSessionID(request.getSession().getId());
        }
		
        String res1 = sessionDetailsService.saveSessionDetails(dto);
        
		res.setStatus(res1);
		res.setResponce(dto.getSessionID());
        
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		
		//deviceIdentification(device);
		
		return res;
	}
	
	public void deviceIdentification(Device device){
		if(device.isMobile()){
			System.err.println("Your are Mobile User.");
		}
		else if(device.isTablet()){
			System.err.println("Your are Tablet User.");
		}
		else if(device.isNormal()){
			System.err.println("Your are Normal Browser User.");
		}
	}
	
/*	@RequestMapping(method = RequestMethod.POST, value = "/postEventDetails")
    public ResponseEntity<ResponseClass> postEventDetails(HttpServletRequest request, HttpServletResponse response){
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
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Control-Allow-Origin", "*");
		headers.set("Access-Control-Allow-Methods", "GET,PUT,POST,OPTIONS");
		headers.set("Access-Control-Max-Age", "3600");
		headers.set("Access-Control-Allow-Headers", "x-requested-with");
		
		return new ResponseEntity<ResponseClass>(res,headers,HttpStatus.CREATED);
	}*/
}
