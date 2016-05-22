package com.aurora.rti.service;

import java.util.List;
import java.util.Map;

import com.aurora.rti.util.GeoLocation;
import com.aurora.rti.util.UserCountDTO;
import com.aurora.rti.util.UserDetailsDTO;

public interface CommonService {
	String getServerTime();
	String getLocationBasedCurrentTime(String timeOffset);
	long getTimeDifference(String time1, String time2);
	Map<String, String> getCountryDateAndTime(String timeOffset);
	public GeoLocation getLocation(String ipAddress);
	String beforeTime(int minutes);
	List<UserCountDTO> getCurrentUserCountList(String sortField, int order,int start, int gridTableSize, String searchq);
	int getCurrentUserCount(String searchq);
	Map<String, Integer> getDeviceCount();
	List<UserDetailsDTO> getUserDetailsBySessionId(String sortField, int order,int start, int gridTableSize, String searchq, Long sessionPK);
	int getUserDetailsCountBySessionId(String searchq, Long sessionPK);
	Map<String, Object> getHeaderDetailsData(Long sid);
	Map<String, Object> getAnalyseData(Long sid);
	List<String> getCountryListByTimeOffset(String offset);
	
}
