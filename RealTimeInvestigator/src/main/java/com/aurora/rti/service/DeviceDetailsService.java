package com.aurora.rti.service;

import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.util.EventDetailsDTO;

public interface DeviceDetailsService {

	String saveDeviceDetails(EventDetailsDTO dto, SessionDetails sessionDetails) throws Exception;

}
