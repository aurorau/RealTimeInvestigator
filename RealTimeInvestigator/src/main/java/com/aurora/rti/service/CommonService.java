package com.aurora.rti.service;

import java.util.Map;

import com.aurora.rti.util.GeoLocation;

public interface CommonService {
	String getServerTime();
	String getLocationBasedCurrentTime(String timeOffset);
	Map<String, String> getCountryDateAndTime(String timeOffset);
	public GeoLocation getLocation(String ipAddress);
	String beforeTime(int minutes);
}
