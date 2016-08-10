package com.aurora.rti.serviceImpl;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aurora.rti.dao.SessionDetailsDao;
import com.aurora.rti.emuns.State;
import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.service.CommonService;
import com.aurora.rti.service.DeviceDetailsService;
import com.aurora.rti.service.SessionDetailsService;
import com.aurora.rti.util.Constants;
import com.aurora.rti.util.EventDetailsDTO;

@Service("sessionDetailsService")
@EnableScheduling
public class SessionDetailsServiceImpl implements SessionDetailsService {
	//private static final Logger logger = Logger.getLogger(SessionDetailsServiceImpl.class);
	private CommonService commonService = null;
	private DeviceDetailsService deviceDetailsService = null;
	private SessionDetailsDao sessionDetailsDao = null;
	
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	
	@Autowired
	public void setDeviceDetailsService(DeviceDetailsService deviceDetailsService) {
		this.deviceDetailsService = deviceDetailsService;
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
				sessionDetails.setStatus(State.ACTIVE.getName());
			} else {
				sessionDetails = sessionDetailsDao.getById(sessionDetails.getSID());
				sessionDetails.setSessionAccessCount(sessionDetails.getSessionAccessCount()+1);
				sessionDetails.setLastAccessTime(currentTime);
				sessionDetails.setStatus(State.ACTIVE.getName());
			}
			//sessionDetailsDao.saveSessionDetails(sessionDetails);
			String status = deviceDetailsService.saveDeviceDetails(dto, sessionDetails);
		
			if(status.equalsIgnoreCase(Constants.SUCCESS)) {
				res = Constants.SUCCESS;
			}
		} catch(Exception e) {
			//logger.error("+++++++++ Error in saveSessionDetails in SessionDetailsServiceImpl :"+e);
		}
		return res;
	}

	@Transactional
	public String heartBeat(HttpServletRequest request) {
		String res = Constants.FAIL;
		SessionDetails sessionDetails = null;
		String sessionId = null;
		try {
			sessionId = request.getParameter("sessionID");
			if(!sessionId.equalsIgnoreCase("-1")){
				sessionDetails = sessionDetailsDao.getSessionDetailsByCreationTimeById(1L, sessionId);
				if(sessionDetails != null) {
					sessionDetails.setHeartBeatTime(commonService.getServerTime());
					sessionDetailsDao.saveSessionDetails(sessionDetails);
					res = Constants.ACTIVE;
				} else {
					res = Constants.INACTIVE;
				}
			}
		} catch(Exception e){
			//logger.error("+++++++++ Error in heartBeat in SessionDetailsServiceImpl :"+e);
		}
		return res;
	}
	
	@Transactional
	@Scheduled(fixedDelay =60000)
	public void changeSessionStatus() {
		String currentTime = commonService.getServerTime();
		String beforeTime = commonService.beforeTime(-3);
		String beforeHeartBeatTime = commonService.beforeTime(-31);
		try{
			sessionDetailsDao.changeSessionStatus(currentTime, beforeTime, beforeHeartBeatTime);
		} catch(Exception e){
			//logger.error("+++++++++ Error in changeSessionStatus in SessionDetailsServiceImpl :"+e);
		}
		
	}
}
