package com.aurora.rti.service;

import javax.servlet.http.HttpServletRequest;

import com.aurora.rti.util.EventDetailsDTO;

public interface SessionDetailsService {

	String saveSessionDetails(EventDetailsDTO dto);
	String heartBeat(HttpServletRequest request);
	void changeSessionStatus();
}
