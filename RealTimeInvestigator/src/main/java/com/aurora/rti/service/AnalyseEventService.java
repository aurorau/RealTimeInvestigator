package com.aurora.rti.service;

import java.util.List;
import java.util.Map;

import com.aurora.rti.util.UserDetailsDTO;

public interface AnalyseEventService {

	Map<String, Object> getHeaderDetailsData(List<UserDetailsDTO> list) throws Exception;
	Map<String, Integer> getEventAnalyseData(List<UserDetailsDTO> list);
	List<UserDetailsDTO> eventVerification(List<UserDetailsDTO> list);
}
