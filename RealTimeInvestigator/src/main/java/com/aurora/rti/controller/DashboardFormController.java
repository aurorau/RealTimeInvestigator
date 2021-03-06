package com.aurora.rti.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.aurora.rti.annotation.ControllerLoggable;
import com.aurora.rti.service.CommonService;
import com.aurora.rti.service.ProxyDetailsService;
import com.aurora.rti.service.SessionDetailsService;
import com.aurora.rti.util.Constants;
import com.aurora.rti.util.ProxyDetailsDTO;
import com.aurora.rti.util.ResponseClass;
import com.aurora.rti.util.UserCountDTO;
import com.aurora.rti.util.UserDetailsDTO;

@RestController
@RequestMapping("/dashboardFormController")
public class DashboardFormController {

	private ProxyDetailsService proxyDetailsService = null;
	private CommonService commonService = null;
	private SessionDetailsService sessionDetailsService = null;
	
	@Autowired
	public void setproxyDetailsService(ProxyDetailsService proxyDetailsService) {
		this.proxyDetailsService = proxyDetailsService;
	}
	
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@Autowired
	public void setSessionDetailsService(SessionDetailsService sessionDetailsService) {
		this.sessionDetailsService = sessionDetailsService;
	}

	@RequestMapping(method = RequestMethod.GET, value="/getCurrentUserCount")
	@ControllerLoggable
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
	    	//logger.error("++++++++++ Error in getCurrentUserCount in DashboardFormController :"+e);
	    }
		return new ModelAndView("pages/dynamicTables/dynamicUserCountTable", model.asMap());
	 }
	@RequestMapping(method = RequestMethod.GET, value="/getUserDetailsBySessionId")
	@ControllerLoggable
	public ModelAndView getUserDetailsBySessionId(HttpServletRequest request, HttpServletResponse response) throws Exception{
	     Long sessionPK = Long.parseLong(request.getParameter("sid"));
	     List<UserDetailsDTO> list = null;
	     
		 Model model = new ExtendedModelMap();
		 ParamEncoder paramEncoder = new ParamEncoder(Constants.USER_DETAILS);

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
		 return new ModelAndView("pages/dynamicTables/dynamicUserDetailsTable", model.asMap());
	}
	
	 @RequestMapping(method = RequestMethod.GET, value = "/getDeviceCount")
     public ResponseEntity<ResponseClass> getDeviceCount(HttpServletRequest request, HttpServletResponse response) throws Exception{

		 HttpHeaders headers = new HttpHeaders();
		 headers.set("Access-Control-Allow-Origin", "*");
		 headers.set("Access-Control-Allow-Methods", "GET");
		 headers.set("Access-Control-Max-Age", "3600");
		 headers.set("Access-Control-Allow-Headers", "x-requested-with");
		 ResponseClass res = new ResponseClass();

	     Map<String, Integer> deviceCountMap = null;
	     deviceCountMap = commonService.getDeviceCount();
	    
	     res.setResponce(deviceCountMap);
		 return new ResponseEntity<ResponseClass>(res,headers,HttpStatus.OK);
	 }
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/getHeaderDetailsData")
     public ResponseEntity<ResponseClass> getHeaderDetailsData(HttpServletRequest request, HttpServletResponse response) throws Exception{

		 HttpHeaders headers = new HttpHeaders();
		 headers.set("Access-Control-Allow-Origin", "*");
		 headers.set("Access-Control-Allow-Methods", "GET");
		 headers.set("Access-Control-Max-Age", "3600");
		 headers.set("Access-Control-Allow-Headers", "x-requested-with");
		 ResponseClass res = new ResponseClass();
		 Long sid = Long.parseLong((String)request.getParameter("sid"));
	     Map<String, Object> geaderDetailsMap = null;
	     geaderDetailsMap = commonService.getHeaderDetailsData(sid);
	    
	     res.setResponce(geaderDetailsMap);
		 return new ResponseEntity<ResponseClass>(res,headers,HttpStatus.OK);
	 }
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/getAnalyseData")
     public ResponseEntity<ResponseClass> getAnalyseData(HttpServletRequest request, HttpServletResponse response) throws Exception{

		 HttpHeaders headers = new HttpHeaders();
		 headers.set("Access-Control-Allow-Origin", "*");
		 headers.set("Access-Control-Allow-Methods", "GET");
		 headers.set("Access-Control-Max-Age", "3600");
		 headers.set("Access-Control-Allow-Headers", "x-requested-with");
		 ResponseClass res = new ResponseClass();
		 Long sid = Long.parseLong((String)request.getParameter("sid"));
	     Map<String, Object> geaderDetailsMap = null;
	     geaderDetailsMap = commonService.getAnalyseData(sid);
	    
	     res.setResponce(geaderDetailsMap);
		 return new ResponseEntity<ResponseClass>(res,headers,HttpStatus.OK);
	 }
	 
	 @RequestMapping(method = RequestMethod.GET, value = "/getProxyDetails")
     public ResponseEntity<ResponseClass> getProxyDetails(HttpServletRequest request, HttpServletResponse response) throws Exception{

		 HttpHeaders headers = new HttpHeaders();
		 headers.set("Access-Control-Allow-Origin", "*");
		 headers.set("Access-Control-Allow-Methods", "GET");
		 headers.set("Access-Control-Max-Age", "3600");
		 headers.set("Access-Control-Allow-Headers", "x-requested-with");
		 ResponseClass res = new ResponseClass();
		 Long bid = Long.parseLong((String)request.getParameter("bid"));
	     List<ProxyDetailsDTO> list = proxyDetailsService.getProxyDetails(bid);
	    
	     res.setResponce(list);
		 return new ResponseEntity<ResponseClass>(res,headers,HttpStatus.OK);
	 }
	 
	 @RequestMapping(method = RequestMethod.POST, value = "/setCSSandJSStatus")
     public ResponseEntity<ResponseClass> setCSSandJSStatus(HttpServletRequest request, HttpServletResponse response) throws Exception{

		 HttpHeaders headers = new HttpHeaders();
		 headers.set("Access-Control-Allow-Origin", "*");
		 headers.set("Access-Control-Allow-Methods", "GET");
		 headers.set("Access-Control-Max-Age", "3600");
		 headers.set("Access-Control-Allow-Headers", "x-requested-with");
		 
		 ResponseClass res = new ResponseClass();
	    
		 System.out.println("Session Id :"+request.getParameter("sessionId"));
		 System.out.println("Type:"+request.getParameter("type"));
		 
		 String status = sessionDetailsService.changeSessionJSandCSSstatus(request);
		 
	     res.setResponce(status);
		 return new ResponseEntity<ResponseClass>(res,headers,HttpStatus.OK);
	 }
}
