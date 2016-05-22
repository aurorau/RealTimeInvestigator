package com.aurora.rti.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.aurora.rti.dao.ProxyDetailsDao;
import com.aurora.rti.model.ProxyDetails;
import com.aurora.rti.util.AbstractDao;
import com.aurora.rti.util.ProxyDetailsDTO;
import com.aurora.rti.util.UserDetailsDTO;

@Repository("proxyDetailsDao")
public class ProxyDetailsDaoImpl extends AbstractDao<Integer, ProxyDetails>  implements ProxyDetailsDao {

	public void saveProxyDetailsService(ProxyDetails proxyDetails) throws Exception{
		persist(proxyDetails);
	}

	public List<UserDetailsDTO> getPID(List<UserDetailsDTO> list) throws Exception {
		
		for(UserDetailsDTO userDetailsDTO : list) {
			Criteria criteriaProxy = createEntityCriteria();
			criteriaProxy.createAlias("browserDetails", "browserDetails");
			criteriaProxy.add(Restrictions.eq("browserDetails.BID", userDetailsDTO.getBid()));
			criteriaProxy.setProjection(Projections.projectionList()
						.add(Projections.property("PID").as("pid")));
			List<Long> pidList = criteriaProxy.list();
			userDetailsDTO.setPid(pidList);
		}
		return list;
	}

	public List<ProxyDetailsDTO> getProxyDetails(Long bid) throws Exception {
		List<ProxyDetailsDTO> list = null;
		
		Criteria criteria = createEntityCriteria()
				.createAlias("browserDetails", "browserDetails")
				.add(Restrictions.eq("browserDetails.BID", bid));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("ipAddress").as("ip"))
				.add(Projections.property("countryName").as("countryName")));
		list = criteria.setResultTransformer(Transformers.aliasToBean(ProxyDetailsDTO.class)).list();
		return list;
	}
}
