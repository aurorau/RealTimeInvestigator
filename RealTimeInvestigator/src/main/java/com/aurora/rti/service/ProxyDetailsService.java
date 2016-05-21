package com.aurora.rti.service;

import com.aurora.rti.model.BrowserDetails;
import com.aurora.rti.util.EventDetailsDTO;

public interface ProxyDetailsService {

	String saveProxyDetailsService(EventDetailsDTO dto, BrowserDetails browserDetails);

}
