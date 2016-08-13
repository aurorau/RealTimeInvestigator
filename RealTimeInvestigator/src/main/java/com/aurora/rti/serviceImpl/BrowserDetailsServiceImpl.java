package com.aurora.rti.serviceImpl;

import java.util.List;
import javax.transaction.Transactional;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.BrowserType;
import nl.bitwalker.useragentutils.UserAgent;
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
import com.aurora.rti.util.UserDetailsDTO;

@Service("browserDetailsService")
public class BrowserDetailsServiceImpl implements BrowserDetailsService {

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
	public String saveBrowserDetails(EventDetailsDTO dto,SessionDetails sessionDetails, DeviceDetails deviceDetails,EventDetails eventDetails) throws Exception {
		String res = Constants.FAIL;

		String refererURL = dto.getReferer();
		UserAgent userAgent1 = UserAgent.parseUserAgentString(dto.getUserAgent());
        long getId = userAgent1.getId();
        Browser browser = userAgent1.getBrowser();
        String browserName = browser.getName();
        BrowserType browserType = browser.getBrowserType();
        //Version browserVersion = userAgent1.getBrowserVersion();
		
		BrowserDetails browserDetails =  new BrowserDetails();
		
		browserDetails.setBrowserName(browserName);
		browserDetails.setBrowserType(browserType.toString());
		//browserDetails.setBrowserVersion(browserVersion.toString());
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

		return res;
	}
	
	@Transactional
	public List<UserDetailsDTO> analyseUserBySessionId(Long sid) throws Exception {
		List<UserDetailsDTO> list = null;
		list = browserDetailsDao.analyseUserBySessionId(sid);
		return list;
	}
	
	@Transactional
	public List<UserDetailsDTO> getUserDetailsBySessionId(String sortField,int order, int start, int gridTableSize, String searchq,Long sessionPK) throws Exception {
		List<UserDetailsDTO> list = null;
		list = browserDetailsDao.getUserDetailsBySessionId(sortField,order, start, gridTableSize, searchq,sessionPK);
		return list;
	}
	@Transactional
	public int getUserDetailsCountBySessionId(String searchq, Long sessionPK) throws Exception {
		int count = 0;
		count = browserDetailsDao.getUserDetailsCountBySessionId(searchq, sessionPK);
		return count;
	}

}
