package com.aurora.rti.daoImpl;

import org.springframework.stereotype.Repository;
import com.aurora.rti.dao.ProxyDetailsDao;
import com.aurora.rti.model.ProxyDetails;
import com.aurora.rti.util.AbstractDao;

@Repository("proxyDetailsDao")
public class ProxyDetailsDaoImpl extends AbstractDao<Integer, ProxyDetails>  implements ProxyDetailsDao {

	public void saveProxyDetailsService(ProxyDetails proxyDetails) {
		persist(proxyDetails);
	}

}
