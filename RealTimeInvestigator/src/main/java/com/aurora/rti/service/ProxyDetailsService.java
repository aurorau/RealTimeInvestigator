package com.aurora.rti.service;

import java.util.List;

import com.aurora.rti.model.BrowserDetails;
import com.aurora.rti.util.EventDetailsDTO;
import com.aurora.rti.util.ProxyDetailsDTO;
import com.aurora.rti.util.UserDetailsDTO;

public interface ProxyDetailsService {

	String saveProxyDetailsService(EventDetailsDTO dto, BrowserDetails browserDetails);
	List<UserDetailsDTO> getPID(List<UserDetailsDTO> list);
	List<ProxyDetailsDTO> getProxyDetails(Long bid);

}
