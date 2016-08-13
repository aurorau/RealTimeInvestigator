package com.aurora.rti.serviceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aurora.rti.dao.SessionDetailsDao;
import com.aurora.rti.emuns.DeviceStatus;
import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.service.AnalyseDeviceService;
import com.aurora.rti.service.AnalyseEventService;
import com.aurora.rti.service.AnalyseUserService;
import com.aurora.rti.service.BrowserDetailsService;
import com.aurora.rti.service.CommonService;
import com.aurora.rti.service.HorizontalAnalysisService;
import com.aurora.rti.service.ProxyDetailsService;
import com.aurora.rti.util.GeoLocation;
import com.aurora.rti.util.UserCountDTO;
import com.aurora.rti.util.UserDetailsDTO;
import com.maxmind.geoip.LookupService;

@Service("commonService")
public class CommonServiceImpl implements CommonService {
	 private static LookupService lookUp;
	 private BrowserDetailsService browserDetailsService = null;
	 private ProxyDetailsService proxyDetailsService = null;
	 private SessionDetailsDao sessionDetailsDao = null;
	 private AnalyseDeviceService analyseDeviceService = null;
	 private AnalyseEventService analyseEventService = null;
	 private AnalyseUserService analyseUserService = null;
	 private HorizontalAnalysisService horizontalAnalysisService = null;
	
	 @Autowired
	 public void setAnalyseDeviceService(AnalyseDeviceService analyseDeviceService) {
		this.analyseDeviceService = analyseDeviceService;
	 }

	 @Autowired
	 public void setBrowserDetailsService(BrowserDetailsService browserDetailsService) {
		this.browserDetailsService = browserDetailsService;
	 }

	 @Autowired
	 public void setProxyDetailsService(ProxyDetailsService proxyDetailsService) {
		this.proxyDetailsService = proxyDetailsService;
	 }
	 
	 @Autowired
	 public void setAnalyseEventService(AnalyseEventService analyseEventService) {
		this.analyseEventService = analyseEventService;
	}
	 
	@Autowired
	public void setAnalyseUserService(AnalyseUserService analyseUserService) {
		this.analyseUserService = analyseUserService;
	}

	@Autowired
	public void setSessionDetailsDao(SessionDetailsDao sessionDetailsDao) {
		this.sessionDetailsDao = sessionDetailsDao;
	}
	
	@Autowired
	public void setHorizontalAnalysisService(HorizontalAnalysisService horizontalAnalysisService) {
		this.horizontalAnalysisService = horizontalAnalysisService;
	}

	public long getTimeDifference(String time1, String time2){
		DateTime crDate = getDate(time1);
		DateTime prDate = getDate(time2);
		
		long timeDiffer  = (crDate.getMillis() - prDate.getMillis());
		return timeDiffer;
	}
	public DateTime getDate(String date1){
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
		DateTime date = null;
		date = fmt.parseDateTime(date1);
        return date;
	}
	 public String getServerTime() throws Exception{
		 String result = null;
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = new Date();
		 result = formatter.format(date);
		 return result;
	 }
	public String beforeTime(int minutes) throws Exception{
		 String result = null;
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = new Date();
		 result = formatter.format(DateUtils.addMinutes(date, minutes));
		 return result;
	}
	 public String getLocationBasedCurrentTime(String timeOffset) throws Exception{
		 String dateTime = null;
		 if(!timeOffset.equalsIgnoreCase("-1")) {
			 DateTime utc = new DateTime(DateTimeZone.UTC);
			 DateTimeZone tz = DateTimeZone.forOffsetMillis((Integer.parseInt(timeOffset)* 60000 * -1));
			 DateTime currentTime = utc.toDateTime(tz);
			 
			 DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
			 String str = currentTime.toString(fmt);
			 dateTime = str;
		 }
		 return dateTime;
	 }
	 
	 public Map<String, String> getCountryDateAndTime(String timeOffset) throws Exception{
		 Map<String, String> map = new HashMap<String, String>();
		 if(!timeOffset.equalsIgnoreCase("-1")) {
			 DateTime utc = new DateTime(DateTimeZone.UTC);
			 DateTimeZone tz = DateTimeZone.forOffsetMillis((Integer.parseInt(timeOffset)* 60000 * -1));
			 DateTime currentTime = utc.toDateTime(tz);
			 
			 String timeZone = currentTime.getZone().toString();
			 
			 DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
			 String str = currentTime.toString(fmt);
			 String dateTime = str;
			 
			 map.put("timeZone", timeZone);
			 map.put("dateTime", dateTime);
		 }
		 return map;
	 }
	 @Transactional
	 public List<UserCountDTO> getCurrentUserCountList(String sortField,int order, int start, int gridTableSize, String searchq) throws Exception{
		List<UserCountDTO> dtoList = null;
		List<UserCountDTO> returnList = new ArrayList<UserCountDTO>();
		List<UserDetailsDTO> list = null;
		dtoList = sessionDetailsDao.getCurrentUserCountList(sortField, order, start, gridTableSize,searchq);
		
		for(UserCountDTO dto : dtoList){
			list = new ArrayList<UserDetailsDTO>();
			list = browserDetailsService.analyseUserBySessionId(dto.getSid());
			list = proxyDetailsService.getPID(list);
			Map<String, Object> deviceMap = analyseDeviceService.deviceIdenticication(list);
			String deviceType = (String) deviceMap.get("deviceType");
			dto.setDeviceType(deviceType);
			returnList.add(dto);
		}
		return returnList;
	 }
	 @Transactional
	 public int getCurrentUserCount(String searchq) throws Exception{
		int count = 0;
		count = sessionDetailsDao.getCurrentUserCount(searchq);
		return count;
	 }
	 
	 @Transactional
	 public Map<String, Integer> getDeviceCount() throws Exception {
		List<SessionDetails> activeSessionList = null;
		List<UserDetailsDTO> list = null;
		Map<String, Integer> deviceReturnMap = new HashMap<String, Integer>();
		Integer mobileCount = 0;
		Integer desktopCount = 0;
		Integer fraudCount = 0;
		Integer totalCount = 0;

		activeSessionList = sessionDetailsDao.getActiveSessions();
		totalCount = activeSessionList.size();
		for(SessionDetails sessionDetail : activeSessionList){
			list = new ArrayList<UserDetailsDTO>();
			list = browserDetailsService.analyseUserBySessionId(sessionDetail.getSID());
			list = proxyDetailsService.getPID(list);
			Map<String, Object> deviceMap = analyseDeviceService.deviceIdenticication(list);
			String deviceType = (String) deviceMap.get("deviceType");
			if(deviceType.equalsIgnoreCase("Desktop")) {
				desktopCount += 1;
			} else if(deviceType.equalsIgnoreCase("Mobile")){
				mobileCount += 1;
			} else if(deviceType.equalsIgnoreCase("Fraud")){
				fraudCount += 1;
			}
		}
		deviceReturnMap.put("desktopCount", desktopCount);
		deviceReturnMap.put("mobileCount", mobileCount);
		deviceReturnMap.put("fraudCount", fraudCount);
		deviceReturnMap.put("totalCount", totalCount);

		return deviceReturnMap;
	 }
	 @Transactional
	 public List<UserDetailsDTO> getUserDetailsBySessionId(String sortField,int order, int start, int gridTableSize, String searchq,Long sessionPK) throws Exception {
		List<UserDetailsDTO> list = null;

		list = browserDetailsService.getUserDetailsBySessionId(sortField,order,start,gridTableSize,searchq, sessionPK);
		//list = analyseEventService.eventVerification(list);
		list = horizontalAnalysisService.horizontalAnalysis(list);
		list = proxyDetailsService.getPID(list);
		
		return list;
	 }
	 @Transactional
	 public int getUserDetailsCountBySessionId(String searchq, Long sessionPK) throws Exception {
		int count = 0;
		count = browserDetailsService.getUserDetailsCountBySessionId(searchq, sessionPK);
		return count;
	 }
	 @Transactional
	 public Map<String, Object> getHeaderDetailsData(Long sid) throws Exception {
		List<UserDetailsDTO> list = null;
		Map<String, Object> map = null;
		list = browserDetailsService.analyseUserBySessionId(sid);
		list = proxyDetailsService.getPID(list);
		map = analyseEventService.getHeaderDetailsData(list);
		return map;
	 }
	 
	 @Transactional
	 public Map<String, Object> getAnalyseData(Long sid) throws Exception {
		List<UserDetailsDTO> list = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Integer> eventAnalyseMap = null;
		Map<String, Object> deviceAnalyseMap = null;
		Map<String, Map<String, Integer>> userAnalyseMap = null;

		list = browserDetailsService.analyseUserBySessionId(sid);
		list = proxyDetailsService.getPID(list);
		eventAnalyseMap = analyseEventService.getEventAnalyseData(list);
		deviceAnalyseMap = analyseDeviceService.deviceIdenticication(list);
		userAnalyseMap = analyseUserService.getUserAnalyseData(list);

		returnMap.put("eventAnalyseMap", eventAnalyseMap);
		returnMap.put("deviceAnalyseMap", deviceAnalyseMap);
		returnMap.put("userAnalyseMap", userAnalyseMap);
		return returnMap;
	 }
	 
	 public List<String> getCountryListByTimeOffset(String offset) throws Exception {
		List<String> list =  new ArrayList<String>();
		Map<String,String> map = getCountryTimeMap();
		
		for (Map.Entry<String, String> entry : map.entrySet()){
			String val = entry.getValue();
			String[] timeZones = val.split(",");
			
			for(String timeZone : timeZones) {
				if(timeZone.equalsIgnoreCase(offset)){
					list.add(entry.getKey());
				}
			}
		}
		return list;
	 }
	 public Map<String, String> getCountryTimeMap() throws Exception {
		Map<String, String> map =  new HashMap<String, String>();
		Properties prop = new Properties();
		InputStream input = null;

		try{
			String filename = "timeCountryMapping.properties";
			input = getClass().getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
			}

			prop.load(input);

			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				map.put(key, value);
				//System.out.println("Key : " + key + ", Value : " + value);
			}
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					//logger.error("+++++++++ Error in getCountryTimeMap in CommonServiceImpl :"+e);
				}
			}
		}
		
		return map;
	}
	 
	public String deviceIdentification(Device device) {
		String deviceType=null;
		if(device.isMobile()){
			deviceType = DeviceStatus.MOBILE.getName();
		}
		else if(device.isTablet()){
			deviceType = DeviceStatus.TABLET.getName();
		}
		else if(device.isNormal()){
			deviceType = DeviceStatus.DESKTOP.getName();
		} else {
			deviceType = DeviceStatus.OTHER.getName();
		}
		return deviceType;
	}

	static {
        try {
            lookUp = new LookupService(
            		CommonServiceImpl.class.getResource("/GeoLiteCity.dat").getFile(),
                    LookupService.GEOIP_MEMORY_CACHE);

        } catch (IOException e) {
        	//logger.error("Could not load geo ip database: " + e.getMessage());
        }
	 }
	 public static GeoLocation getLocation(long ipAddress) {
	        return GeoLocation.map(lookUp.getLocation(ipAddress));
	 } 
	 public GeoLocation getLocation(String ipAddress) {
	        return GeoLocation.map(lookUp.getLocation(ipAddress));
	 }
	 public static GeoLocation getLocation(InetAddress ipAddress){
	        return GeoLocation.map(lookUp.getLocation(ipAddress));
	 }


}
