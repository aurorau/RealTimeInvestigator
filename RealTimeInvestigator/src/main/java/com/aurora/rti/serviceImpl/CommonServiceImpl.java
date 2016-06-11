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
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurora.rti.dao.SessionDetailsDao;
import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.service.AnalyseDeviceService;
import com.aurora.rti.service.AnalyseEventService;
import com.aurora.rti.service.AnalyseUserService;
import com.aurora.rti.service.BrowserDetailsService;
import com.aurora.rti.service.CommonService;
import com.aurora.rti.service.ProxyDetailsService;
import com.aurora.rti.util.GeoLocation;
import com.aurora.rti.util.UserCountDTO;
import com.aurora.rti.util.UserDetailsDTO;
import com.maxmind.geoip.LookupService;

@Service("commonService")
public class CommonServiceImpl implements CommonService {
	 private static final Logger logger = Logger.getLogger(CommonServiceImpl.class);
	 private static LookupService lookUp;
	 private BrowserDetailsService browserDetailsService = null;
	 private ProxyDetailsService proxyDetailsService = null;
	 private SessionDetailsDao sessionDetailsDao = null;
	 private AnalyseDeviceService analyseDeviceService = null;
	 private AnalyseEventService analyseEventService = null;
	 private AnalyseUserService analyseUserService = null;
	
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
	 public String getServerTime(){
		 String result = null;
		 try {
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 Date date = new Date();
			 result = formatter.format(date);
		 } catch(Exception e){
			 logger.error("++++++ Error in getServerTime in CommonServiceImpl :"+e);
		 }

		 return result;
	 }
	public String beforeTime(int minutes) {
		 String result = null;
		 try{
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 Date date = new Date();
			 result = formatter.format(DateUtils.addMinutes(date, minutes));
		 } catch(Exception e){
			 logger.error("++++++ Error in beforeTime in CommonServiceImpl :"+e);
		 }

		 return result;
	}
	 public String getLocationBasedCurrentTime(String timeOffset){
		 String dateTime = null;
		 try {
			 if(!timeOffset.equalsIgnoreCase("-1")) {
				 DateTime utc = new DateTime(DateTimeZone.UTC);
				 DateTimeZone tz = DateTimeZone.forOffsetMillis((Integer.parseInt(timeOffset)* 60000 * -1));
				 DateTime currentTime = utc.toDateTime(tz);
				 
				 DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
				 String str = currentTime.toString(fmt);
				 dateTime = str;
			 }
		 } catch(Exception e){
			 logger.error("++++++ Error in getLocationBasedCurrentTime in CommonServiceImpl :"+e);
		 }
		 return dateTime;
	 }
	 
	 public Map<String, String> getCountryDateAndTime(String timeOffset) {
		 Map<String, String> map = new HashMap<String, String>();
		 try {
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
		 } catch(Exception e){
			 logger.error("++++++ Error in getCountryDateAndTime in CommonServiceImpl :"+e);
		 }
		 return map;
	 }
	 @Transactional
	 public List<UserCountDTO> getCurrentUserCountList(String sortField,int order, int start, int gridTableSize, String searchq) {
		List<UserCountDTO> dtoList = null;
		List<UserCountDTO> returnList = new ArrayList<UserCountDTO>();
		List<UserDetailsDTO> list = null;
		try {
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
			
		} catch(Exception e) {
			logger.error("+++++++++ Error in getCurrentUserCountList in CommonServiceImpl :"+e);
		}
		return returnList;
	 }
	 @Transactional
	 public int getCurrentUserCount(String searchq) {
		int count = 0;
		
		try {
			count = sessionDetailsDao.getCurrentUserCount(searchq);
		}catch (Exception e){
			logger.error("+++++++++ Error in getCurrentUserCount in CommonServiceImpl :"+e);
		}
		
		return count;
	 }
	 
	 @Transactional
	 public Map<String, Integer> getDeviceCount() {
		List<SessionDetails> activeSessionList = null;
		List<UserDetailsDTO> list = null;
		Map<String, Integer> deviceReturnMap = new HashMap<String, Integer>();
		Integer mobileCount = 0;
		Integer desktopCount = 0;
		Integer fraudCount = 0;
		Integer totalCount = 0;
		try{
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
		} catch (Exception e){
			logger.error("+++++++++ Error in getDeviceCount in CommonServiceImpl :"+e);
		}
		return deviceReturnMap;
	 }
	 @Transactional
	 public List<UserDetailsDTO> getUserDetailsBySessionId(String sortField,int order, int start, int gridTableSize, String searchq,Long sessionPK) {
		List<UserDetailsDTO> list = null;
		
		try {
			list = browserDetailsService.getUserDetailsBySessionId(sortField,order,start,gridTableSize,searchq, sessionPK);
			list = analyseEventService.eventVerification(list);
			list = proxyDetailsService.getPID(list);
		} catch(Exception e) {
			logger.error("+++++++++ Error in getUserDetailsBySessionId in CommonServiceImpl :"+e);
		}
		return list;
	 }
	 @Transactional
	 public int getUserDetailsCountBySessionId(String searchq, Long sessionPK) {
		int count = 0;
		
		try {
			count = browserDetailsService.getUserDetailsCountBySessionId(searchq, sessionPK);
		}catch (Exception e){
			logger.error("+++++++++ Error in getUserDetailsCountBySessionId in CommonServiceImpl :"+e);
		}
		
		return count;
	 }
	 @Transactional
	 public Map<String, Object> getHeaderDetailsData(Long sid) {
		List<UserDetailsDTO> list = null;
		Map<String, Object> map = null;
		try{
			list = browserDetailsService.analyseUserBySessionId(sid);
			list = proxyDetailsService.getPID(list);
			map = analyseEventService.getHeaderDetailsData(list);
		} catch(Exception e){
			logger.error("+++++++++ Error in getHeaderDetailsData in CommonServiceImpl :"+e);
		}
		return map;
	 }
	 
	 @Transactional
	 public Map<String, Object> getAnalyseData(Long sid) {
		List<UserDetailsDTO> list = null;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Integer> eventAnalyseMap = null;
		Map<String, Object> deviceAnalyseMap = null;
		Map<String, Map<String, Integer>> userAnalyseMap = null;
		try{
			list = browserDetailsService.analyseUserBySessionId(sid);
			list = proxyDetailsService.getPID(list);
			eventAnalyseMap = analyseEventService.getEventAnalyseData(list);
			deviceAnalyseMap = analyseDeviceService.deviceIdenticication(list);
			userAnalyseMap = analyseUserService.getUserAnalyseData(list);
		} catch(Exception e){
			logger.error("+++++++++ Error in getAnalyseData in CommonServiceImpl :"+e);
		}
		returnMap.put("eventAnalyseMap", eventAnalyseMap);
		returnMap.put("deviceAnalyseMap", deviceAnalyseMap);
		returnMap.put("userAnalyseMap", userAnalyseMap);
		return returnMap;
	 }
	 
	 public List<String> getCountryListByTimeOffset(String offset) {
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
	 public Map<String, String> getCountryTimeMap() {
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
		} catch(IOException ex){
			logger.error("+++++++++ Error in getCountryTimeMap in CommonServiceImpl :"+ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("+++++++++ Error in getCountryTimeMap in CommonServiceImpl :"+e);
				}
			}
		}
		
		return map;
	}
	 static {
        try {
            lookUp = new LookupService(
            		CommonServiceImpl.class.getResource("/GeoLiteCity.dat").getFile(),
                    LookupService.GEOIP_MEMORY_CACHE);

        } catch (IOException e) {
        	logger.error("Could not load geo ip database: " + e.getMessage());
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
