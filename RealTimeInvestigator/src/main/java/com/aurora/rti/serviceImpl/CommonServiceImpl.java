package com.aurora.rti.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import com.aurora.rti.service.CommonService;

@Service("commonService")
public class CommonServiceImpl implements CommonService {
	 private static final Logger logger = Logger.getLogger(CommonServiceImpl.class);
	 
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
}
