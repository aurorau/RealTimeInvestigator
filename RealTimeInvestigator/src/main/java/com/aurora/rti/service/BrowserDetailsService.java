package com.aurora.rti.service;

import com.aurora.rti.model.DeviceDetails;
import com.aurora.rti.model.EventDetails;
import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.util.EventDetailsDTO;

public interface BrowserDetailsService {

	String saveBrowserDetails(EventDetailsDTO dto,SessionDetails sessionDetails, DeviceDetails deviceDetails,EventDetails eventDetails);

}
