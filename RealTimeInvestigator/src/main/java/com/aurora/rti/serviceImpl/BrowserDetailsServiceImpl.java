package com.aurora.rti.serviceImpl;

import javax.transaction.Transactional;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.BrowserType;
import nl.bitwalker.useragentutils.UserAgent;
import nl.bitwalker.useragentutils.Version;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.rti.dao.BrowserDetailsDao;
import com.aurora.rti.model.BrowserDetails;
import com.aurora.rti.model.DeviceDetails;
import com.aurora.rti.model.EventDetails;
import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.service.BrowserDetailsService;
import com.aurora.rti.service.ProxyDetailsService;
import com.aurora.rti.util.Constants;
import com.aurora.rti.util.EventDetailsDTO;

@Service("browserDetailsService")
public class BrowserDetailsServiceImpl implements BrowserDetailsService {

	private static final Logger logger = Logger.getLogger(BrowserDetailsServiceImpl.class);
	private ProxyDetailsService proxyDetailsService = null;
	private BrowserDetailsDao browserDetailsDao = null;
	
	@Autowired
	public void setProxyDetailsService(ProxyDetailsService proxyDetailsService) {
		this.proxyDetailsService = proxyDetailsService;
	}

	@Autowired
	public void setBrowserDetailsDao(BrowserDetailsDao browserDetailsDao) {
		this.browserDetailsDao = browserDetailsDao;
	}

	@Transactional
	public String saveBrowserDetails(EventDetailsDTO dto,SessionDetails sessionDetails, DeviceDetails deviceDetails,EventDetails eventDetails) {
		String res = Constants.FAIL;
		
		try {
			String refererURL = dto.getReferer();
			UserAgent userAgent1 = UserAgent.parseUserAgentString(dto.getUserAgent());
	        long getId = userAgent1.getId();
	        Browser browser = userAgent1.getBrowser();
	        String browserName = browser.getName();
	        BrowserType browserType = browser.getBrowserType();
	        Version browserVersion = userAgent1.getBrowserVersion();
			
			BrowserDetails browserDetails =  new BrowserDetails();
			
			browserDetails.setBrowserName(browserName);
			browserDetails.setBrowserType(browserType.toString());
			browserDetails.setBrowserVersion(browserVersion.toString());
			browserDetails.setUserAgetntId(getId);
			browserDetails.setRefererURL(refererURL);
			browserDetails.setDeviceDetails(deviceDetails);
			browserDetails.setSessionDetails(sessionDetails);
			browserDetails.setEventDetails(eventDetails);
		
			browserDetailsDao.saveBrowserDetails(browserDetails);
			String status = proxyDetailsService.saveProxyDetailsService(dto, browserDetails);
			
			if(status.equalsIgnoreCase(Constants.SUCCESS)){
				res = Constants.SUCCESS;
			}
		} catch(Exception e) {
			logger.error("++++++++++ Error in saveBrowserDetails in BrowserDetailsServiceImpl :"+e);
		}
		return res;
	}

}
