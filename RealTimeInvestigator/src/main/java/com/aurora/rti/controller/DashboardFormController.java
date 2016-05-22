package com.aurora.rti.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.aurora.rti.service.CommonService;
import com.aurora.rti.service.SessionDetailsService;
import com.aurora.rti.util.Constants;
import com.aurora.rti.util.GetHeaders;
import com.aurora.rti.util.ResponseClass;
import com.aurora.rti.util.UserCountDTO;
import com.aurora.rti.util.UserDetailsDTO;

@RestController
@RequestMapping("/dashboardFormController")
public class DashboardFormController {
	private static final Logger logger = Logger.getLogger(DashboardFormController.class);
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

	@RequestMapping(method = RequestMethod.GET, value="/getCurrentUserCount")
	public ModelAndView getCurrentUserCount(HttpServletRequest request, HttpServletResponse response){
	    List<UserCountDTO> list = null;
	     
		Model model = new ExtendedModelMap();
		ParamEncoder paramEncoder = new ParamEncoder(Constants.USER_COUNT_TABLE);
		 
	    try{
	    	String sortField = ServletRequestUtils.getStringParameter(request, paramEncoder.encodeParameterName(TableTagParameters.PARAMETER_SORT));
	    	int order = ServletRequestUtils.getIntParameter(request, paramEncoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER), 0);
	    	int page = ServletRequestUtils.getIntParameter(request, paramEncoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), 0);
	    	int start = (page>0) ? (page - 1) * Constants.GRID_TABLE_SIZE : 0;
	    	String searchq = ServletRequestUtils.getStringParameter(request, Constants.PARAMETER_SEARCH);
			
	    	list = commonService.getCurrentUserCountList(sortField,order,start, Constants.GRID_TABLE_SIZE, searchq);
	    	int listCount = commonService.getCurrentUserCount(searchq);
			
	    	request.setAttribute(Constants.TABLE_SIZE, listCount );
	    	request.setAttribute(Constants.GRID_TABLE_SIZE_KEY, Constants.GRID_TABLE_SIZE);
	    	model.addAttribute(Constants.USER_COUNT_TABLE, list);
	    } catch (Exception e) {
	    	logger.error("++++++++++ Error in getCurrentUserCount in DashboardFormController :"+e);
	    }
		return new ModelAndView("pages/dynamicTables/dynamicUserCountTable", model.asMap());
	 }
	@RequestMapping(method = RequestMethod.GET, value="/getUserDetailsBySessionId")
	public ModelAndView getUserDetailsBySessionId(HttpServletRequest request, HttpServletResponse response){
	     Long sessionPK = Long.parseLong(request.getParameter("sid"));
	     List<UserDetailsDTO> list = null;
	     
		 Model model = new ExtendedModelMap();
		 ParamEncoder paramEncoder = new ParamEncoder(Constants.USER_DETAILS);
		 
	    	try{
	    		String sortField = ServletRequestUtils.getStringParameter(request, paramEncoder.encodeParameterName(TableTagParameters.PARAMETER_SORT));
	    		int order = ServletRequestUtils.getIntParameter(request, paramEncoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER), 0);
	    		int page = ServletRequestUtils.getIntParameter(request, paramEncoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), 0);
	    		int start = (page>0) ? (page - 1) * Constants.GRID_TABLE_SIZE : 0;
	    		String searchq = ServletRequestUtils.getStringParameter(request, Constants.PARAMETER_SEARCH);
			
	    		list = commonService.getUserDetailsBySessionId(sortField,order,start, Constants.GRID_TABLE_SIZE, searchq, sessionPK);
	    		int listCount = commonService.getUserDetailsCountBySessionId(searchq, sessionPK);
			
	    		request.setAttribute(Constants.TABLE_SIZE, listCount );
	    		request.setAttribute(Constants.GRID_TABLE_SIZE_KEY, Constants.GRID_TABLE_SIZE);
	    		model.addAttribute(Constants.USER_DETAILS, list);
	    	} catch (Exception e) {
	    		logger.error("++++++++++ Error in getUserDetailsBySessionId in DashboardFormController :"+e);
	    	}
		 return new ModelAndView("pages/dynamicTables/dynamicUserDetailsTable", model.asMap());
	}
	
	 @RequestMapping(method = RequestMethod.GET, value = "/getDeviceCount")
     public ResponseEntity<ResponseClass> getDeviceCount(HttpServletRequest request, HttpServletResponse response){

		 HttpHeaders headers = new GetHeaders().getHeaders();
		 ResponseClass res = new ResponseClass();

	     Map<String, Integer> deviceCountMap = null;
	     deviceCountMap = commonService.getDeviceCount();
	    
	     res.setResponce(deviceCountMap);
		 return new ResponseEntity<ResponseClass>(res,headers,HttpStatus.CREATED);
	 }
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/getHeaderDetailsData")
     public ResponseEntity<ResponseClass> getHeaderDetailsData(HttpServletRequest request, HttpServletResponse response){

		 HttpHeaders headers = new GetHeaders().getHeaders();
		 ResponseClass res = new ResponseClass();
		 Long sid = Long.parseLong((String)request.getParameter("sid"));
	     Map<String, Object> geaderDetailsMap = null;
	     geaderDetailsMap = commonService.getHeaderDetailsData(sid);
	    
	     res.setResponce(geaderDetailsMap);
		 return new ResponseEntity<ResponseClass>(res,headers,HttpStatus.CREATED);
	 }
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/getAnalyseData")
     public ResponseEntity<ResponseClass> getAnalyseData(HttpServletRequest request, HttpServletResponse response){

		 HttpHeaders headers = new GetHeaders().getHeaders();
		 ResponseClass res = new ResponseClass();
		 Long sid = Long.parseLong((String)request.getParameter("sid"));
	     Map<String, Object> geaderDetailsMap = null;
	     geaderDetailsMap = commonService.getAnalyseData(sid);
	    
	     res.setResponce(geaderDetailsMap);
		 return new ResponseEntity<ResponseClass>(res,headers,HttpStatus.CREATED);
	 }
}
