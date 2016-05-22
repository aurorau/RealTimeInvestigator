package com.aurora.rti.dao;

import java.util.List;

import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.util.UserCountDTO;
import com.aurora.rti.util.UserDetailsDTO;

public interface SessionDetailsDao {

	SessionDetails getSessionDetailsByCreationTimeById(long l, String sessionId) throws Exception;
	SessionDetails getById(Long sid) throws Exception;
	void saveSessionDetails(SessionDetails sessionDetails) throws Exception;
	void changeSessionStatus(String currentTime, String beforeTime, String beforeHeartBeatTime) throws Exception;
	List<UserCountDTO> getCurrentUserCountList(String sortField, int order,int start, int gridTableSize, String searchq) throws Exception;
	int getCurrentUserCount(String searchq)throws Exception;
	List<SessionDetails> getActiveSessions() throws Exception;
	int getSessionTimeOutCountBySessionId(String sessionId) throws Exception;

}
