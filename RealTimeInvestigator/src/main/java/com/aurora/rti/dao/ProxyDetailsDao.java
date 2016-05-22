package com.aurora.rti.dao;

import java.util.List;

import com.aurora.rti.model.ProxyDetails;
import com.aurora.rti.util.ProxyDetailsDTO;
import com.aurora.rti.util.UserDetailsDTO;

public interface ProxyDetailsDao {

	void saveProxyDetailsService(ProxyDetails proxyDetails) throws Exception;
	List<UserDetailsDTO> getPID(List<UserDetailsDTO> list)throws Exception;
	List<ProxyDetailsDTO> getProxyDetails(Long bid)throws Exception;

}
