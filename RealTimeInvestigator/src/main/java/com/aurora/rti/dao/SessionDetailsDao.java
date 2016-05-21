package com.aurora.rti.dao;

import com.aurora.rti.model.SessionDetails;

public interface SessionDetailsDao {

	SessionDetails getSessionDetailsByCreationTimeById(long l, String sessionId);
	SessionDetails getById(Long sid);
	void saveSessionDetails(SessionDetails sessionDetails);
	void changeSessionStatus(String currentTime, String beforeTime, String beforeHeartBeatTime);

}
