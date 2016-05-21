package com.aurora.rti.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.aurora.rti.dao.SessionDetailsDao;
import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.util.AbstractDao;

@Repository("sessionDetailsDao")
public class SessionDetailsDaoImpl extends AbstractDao<Integer, SessionDetails> implements SessionDetailsDao {

	public void saveSessionDetails(SessionDetails sessionDetails) {
		persist(sessionDetails);
		
	}
	
	public SessionDetails getSessionDetailsByCreationTimeById(long l, String sessionId) {
		SessionDetails sessionDetails = null;
		
		Criteria criteria = createEntityCriteria()
				.add(Restrictions.eq("status", "ACTIVE"))
				.add(Restrictions.eq("sessionId", sessionId));
		sessionDetails = (SessionDetails) criteria.uniqueResult();
		return sessionDetails;
	}

	public SessionDetails getById(Long sid) {
		SessionDetails sessionDetails = null;
		
		Criteria criteria = createEntityCriteria()
				.add(Restrictions.eq("SID", sid));
		sessionDetails = (SessionDetails) criteria.uniqueResult();
		return sessionDetails;
	}

}
