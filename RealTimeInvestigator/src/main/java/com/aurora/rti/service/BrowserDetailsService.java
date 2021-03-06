package com.aurora.rti.service;

import java.util.List;

import com.aurora.rti.model.DeviceDetails;
import com.aurora.rti.model.EventDetails;
import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.util.EventDetailsDTO;
import com.aurora.rti.util.UserDetailsDTO;

public interface BrowserDetailsService {

	String saveBrowserDetails(EventDetailsDTO dto,SessionDetails sessionDetails, DeviceDetails deviceDetails,EventDetails eventDetails) throws Exception;
	List<UserDetailsDTO> analyseUserBySessionId(Long sid) throws Exception;
	List<UserDetailsDTO> getUserDetailsBySessionId(String sortField, int order,int start, int gridTableSize, String searchq, Long sessionPK) throws Exception;
	int getUserDetailsCountBySessionId(String searchq, Long sessionPK) throws Exception;

}
