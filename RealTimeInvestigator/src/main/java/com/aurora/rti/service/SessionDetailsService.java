package com.aurora.rti.service;

import javax.servlet.http.HttpServletRequest;
import com.aurora.rti.util.EventDetailsDTO;

public interface SessionDetailsService {

	String saveSessionDetails(EventDetailsDTO dto) throws Exception;
	String heartBeat(HttpServletRequest request) throws Exception;
	void changeSessionStatus() throws Exception;
}
