package com.aurora.rti.serviceImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurora.rti.dao.SessionDetailsDao;
import com.aurora.rti.emuns.State;
import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.service.CommonService;
import com.aurora.rti.service.SessionDetailsService;
import com.aurora.rti.util.Constants;
import com.aurora.rti.util.EventDetailsDTO;

@Service("sessionDetailsService")
public class SessionDetailsServiceImpl implements SessionDetailsService {
	private static final Logger logger = Logger.getLogger(SessionDetailsServiceImpl.class);
	private CommonService commonService = null;
	private SessionDetailsDao sessionDetailsDao = null;
	
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@Autowired
	public void setSessionDetailsDao(SessionDetailsDao sessionDetailsDao) {
		this.sessionDetailsDao = sessionDetailsDao;
	}
	
	@Transactional
	public String saveSessionDetails(EventDetailsDTO dto) {
		String res = Constants.FAIL;
		String sessionId = null;
		String currentTime = commonService.getServerTime();
		sessionId = dto.getSessionID();
		 
		SessionDetails sessionDetails = null;
			
		try {
			sessionDetails = sessionDetailsDao.getSessionDetailsByCreationTimeById(1L, sessionId);

			if(sessionDetails == null) {
				sessionDetails = new SessionDetails();
				sessionDetails.setSessionId(sessionId);
				sessionDetails.setSessionAccessCount(1L);
				sessionDetails.setSessionCreatedTime(currentTime);
				sessionDetails.setLastAccessTime(currentTime);
				sessionDetails.setHeartBeatTime(commonService.getServerTime());
				sessionDetails.setStatus("ACTIVE");
			} else {
				sessionDetails = sessionDetailsDao.getById(sessionDetails.getSID());
				sessionDetails.setSessionAccessCount(sessionDetails.getSessionAccessCount()+1);
				sessionDetails.setLastAccessTime(currentTime);
				sessionDetails.setStatus(State.ACTIVE.getName());
			}
			sessionDetailsDao.saveSessionDetails(sessionDetails);
			String status = Constants.SUCCESS;
			//deviceDetailsService.saveDeviceDetails(request, sessionDetails);
		
			if(status.equalsIgnoreCase(Constants.SUCCESS)) {
				res = Constants.SUCCESS;
			}
		} catch(Exception e) {
			logger.error("+++++++++ Error in saveSessionDetails in SessionDetailsServiceImpl :"+e);
		}
		return res;
	}

}
