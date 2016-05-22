package com.aurora.rti.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.aurora.rti.util.EventDetailsDTO;
import com.aurora.rti.util.UserCountDTO;

public interface SessionDetailsService {

	String saveSessionDetails(EventDetailsDTO dto);
	String heartBeat(HttpServletRequest request);
	void changeSessionStatus();
}
