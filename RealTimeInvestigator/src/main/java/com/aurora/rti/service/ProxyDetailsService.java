package com.aurora.rti.service;

import java.util.List;

import com.aurora.rti.model.BrowserDetails;
import com.aurora.rti.util.EventDetailsDTO;
import com.aurora.rti.util.ProxyDetailsDTO;
import com.aurora.rti.util.UserDetailsDTO;

public interface ProxyDetailsService {

	String saveProxyDetailsService(EventDetailsDTO dto, BrowserDetails browserDetails) throws Exception;
	List<UserDetailsDTO> getPID(List<UserDetailsDTO> list) throws Exception;
	List<ProxyDetailsDTO> getProxyDetails(Long bid) throws Exception;

}
