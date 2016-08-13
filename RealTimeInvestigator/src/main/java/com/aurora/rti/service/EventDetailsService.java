package com.aurora.rti.service;

import com.aurora.rti.model.DeviceDetails;
import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.util.EventDetailsDTO;

public interface EventDetailsService {

	String saveEventDetails(EventDetailsDTO dto, SessionDetails sessionDetails, DeviceDetails deviceDetails) throws Exception;

}
