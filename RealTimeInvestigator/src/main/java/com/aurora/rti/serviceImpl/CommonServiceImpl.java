package com.aurora.rti.serviceImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import com.aurora.rti.service.CommonService;
import com.aurora.rti.util.GeoLocation;
import com.maxmind.geoip.LookupService;

@Service("commonService")
public class CommonServiceImpl implements CommonService {
	 private static final Logger logger = Logger.getLogger(CommonServiceImpl.class);
	 private static LookupService lookUp;
	 
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
