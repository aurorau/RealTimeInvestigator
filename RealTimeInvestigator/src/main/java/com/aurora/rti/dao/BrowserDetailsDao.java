package com.aurora.rti.dao;

import java.util.List;

import com.aurora.rti.model.BrowserDetails;
import com.aurora.rti.util.UserDetailsDTO;

public interface BrowserDetailsDao {

	void saveBrowserDetails(BrowserDetails browserDetails) throws Exception;
	List<UserDetailsDTO> analyseUserBySessionId(Long sid) throws Exception;
	List<UserDetailsDTO> getUserDetailsBySessionId(String sortField, int order,int start, int gridTableSize, String searchq, Long sessionPK) throws Exception;
	int getUserDetailsCountBySessionId(String searchq, Long sessionPK)throws Exception;

}
