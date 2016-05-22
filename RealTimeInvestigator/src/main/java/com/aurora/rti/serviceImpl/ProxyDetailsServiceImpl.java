package com.aurora.rti.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurora.rti.dao.ProxyDetailsDao;
import com.aurora.rti.model.BrowserDetails;
import com.aurora.rti.model.ProxyDetails;
import com.aurora.rti.service.CommonService;
import com.aurora.rti.service.ProxyDetailsService;
import com.aurora.rti.util.Constants;
import com.aurora.rti.util.EventDetailsDTO;
import com.aurora.rti.util.GeoLocation;
import com.aurora.rti.util.ProxyDetailsDTO;
import com.aurora.rti.util.UserDetailsDTO;

@Service("proxyDetailsService")
public class ProxyDetailsServiceImpl implements ProxyDetailsService {

	private static final Logger logger = Logger.getLogger(ProxyDetailsServiceImpl.class);
	private CommonService commonService = null;
	private ProxyDetailsDao proxyDetailsDao = null;
	
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@Autowired
	public void setProxyDetailsDao(ProxyDetailsDao proxyDetailsDao) {
		this.proxyDetailsDao = proxyDetailsDao;
	}

	@Transactional
	public String saveProxyDetailsService(EventDetailsDTO dto, BrowserDetails browserDetails) {
		String res = Constants.FAIL;
		List<String> proxyList = null;
		try {
			 proxyList = new ArrayList<String>();
			 String ipAddress = "112.135.1.252,199.189.80.13,177.207.196.50";
					 //dto.getxForwarded();
					 //"112.135.1.252,199.189.80.13,177.207.196.50";
					 //request.getHeader("X-FORWARDED-FOR");
					
			 
			 if(ipAddress != null) {
	        	String[] proxyList1 = ipAddress.split(",");
	        	for(String prxy : proxyList1) {
	        		String ip = prxy;
	        		proxyList.add(ip);
	        	}
			 } 
/*			 else {
				 ipAddress =dto.getRemoteAddress();  
			 }*/
			 
			 for(String proxyIP : proxyList) {
				 ProxyDetails proxyDetails=  new ProxyDetails();
				 GeoLocation geoLocation = commonService.getLocation(proxyIP);
				 
				 proxyDetails.setBrowserDetails(browserDetails);
				 proxyDetails.setCity(geoLocation.getCity());
				 proxyDetails.setCountryCode(geoLocation.getCountryCode());
				 proxyDetails.setCountryName(geoLocation.getCountryName());
				 proxyDetails.setIpAddress(proxyIP);
				 proxyDetails.setLatitude(geoLocation.getLatitude());
				 proxyDetails.setLongitude(geoLocation.getLongitude());
				 proxyDetails.setPostalCode(geoLocation.getPostalCode());
				 proxyDetails.setRegion(geoLocation.getRegion());
				 
				 proxyDetailsDao.saveProxyDetailsService(proxyDetails);
			 }
			res = Constants.SUCCESS;
		} catch(Exception e) {
			logger.error("++++++++++ Error in saveProxyDetailsService in ProxyDetailsServiceImpl :"+e);
		}
		return res;
	}

	@Transactional
	public List<UserDetailsDTO> getPID(List<UserDetailsDTO> list) {
		List<UserDetailsDTO> dtoList = null;
		
		try {
			dtoList = proxyDetailsDao.getPID(list);
		} catch(Exception e){
			logger.error("++++++++++ Error in getPID in ProxyDetailsServiceImpl :"+e);
		}
		return dtoList;
	}

	@Transactional
	public List<ProxyDetailsDTO> getProxyDetails(Long bid) {
		List<ProxyDetailsDTO>  list =null;
		try {
			list = proxyDetailsDao.getProxyDetails(bid);
		}catch (Exception e){
			logger.error("++++++++++ Error in getProxyDetails in ProxyDetailsServiceImpl :"+e);
		}
		return list;
	}

}
