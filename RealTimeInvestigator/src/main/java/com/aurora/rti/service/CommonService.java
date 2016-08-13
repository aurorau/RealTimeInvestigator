package com.aurora.rti.service;

import java.util.List;
import java.util.Map;

import org.springframework.mobile.device.Device;

import com.aurora.rti.util.GeoLocation;
import com.aurora.rti.util.UserCountDTO;
import com.aurora.rti.util.UserDetailsDTO;

public interface CommonService {
	String getServerTime() throws Exception;
	String getLocationBasedCurrentTime(String timeOffset) throws Exception;
	long getTimeDifference(String time1, String time2);
	Map<String, String> getCountryDateAndTime(String timeOffset) throws Exception;
	public GeoLocation getLocation(String ipAddress);
	String beforeTime(int minutes) throws Exception;
	List<UserCountDTO> getCurrentUserCountList(String sortField, int order,int start, int gridTableSize, String searchq) throws Exception;
	int getCurrentUserCount(String searchq) throws Exception;
	Map<String, Integer> getDeviceCount() throws Exception;
	List<UserDetailsDTO> getUserDetailsBySessionId(String sortField, int order,int start, int gridTableSize, String searchq, Long sessionPK) throws Exception;
	int getUserDetailsCountBySessionId(String searchq, Long sessionPK) throws Exception;
	Map<String, Object> getHeaderDetailsData(Long sid) throws Exception;
	Map<String, Object> getAnalyseData(Long sid) throws Exception;
	List<String> getCountryListByTimeOffset(String offset) throws Exception;
	String deviceIdentification(Device device);
	
}
