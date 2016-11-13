package com.aurora.rti.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.aurora.rti.util.EventDetailsDTO;

public interface SessionDetailsService {

	String saveSessionDetails(EventDetailsDTO dto) throws Exception;
	String heartBeat(HttpServletRequest request) throws Exception;
	void changeSessionStatus() throws Exception;
	String changeSessionJSandCSSstatus(HttpServletRequest request) throws Exception;
	public Map<String, String> getSessionJSandCSSstatus(HttpServletRequest request) throws Exception;
}
