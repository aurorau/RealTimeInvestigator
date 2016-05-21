package com.aurora.rti.daoImpl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.aurora.rti.dao.SessionDetailsDao;
import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.util.AbstractDao;
import com.aurora.rti.util.CurrentUsersDTO;

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
	
	public void changeSessionStatus(String currentTime, String beforeTime, String beforeHeartBeatTime){

		List<CurrentUsersDTO> allList = null;
		List<CurrentUsersDTO> activeList = null;
		
		Criteria criteria = createEntityCriteria()
				.add(Restrictions.between("heartBeatTime",beforeHeartBeatTime, currentTime))
				.add(Restrictions.between("lastAccessTime",beforeTime, currentTime));
		criteria.addOrder(Order.asc("SID"));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("SID").as("sid"))
				.add(Projections.property("lastAccessTime").as("lastAccessTime")));	
		activeList = criteria.setResultTransformer(Transformers.aliasToBean(CurrentUsersDTO.class)).list();
		
		Criteria criteria1 = createEntityCriteria();
		criteria1.addOrder(Order.asc("SID"));
		criteria1.setProjection(Projections.projectionList()
				.add(Projections.property("SID").as("sid"))
				.add(Projections.property("lastAccessTime").as("lastAccessTime")));
		allList= criteria1.setResultTransformer(Transformers.aliasToBean(CurrentUsersDTO.class)).list();
		
		for(CurrentUsersDTO dto : allList){
			try{
				SessionDetails sd = getById(dto.getSid());
				sd.setStatus("INACTIVE");
				saveSessionDetails(sd);
			} catch(Exception e){
				System.out.println("E :"+e);
			}
		}
		
		for(CurrentUsersDTO dto : activeList){
			try{
				SessionDetails sd = getById(dto.getSid());
				sd.setStatus("ACTIVE");
				saveSessionDetails(sd);
			} catch(Exception e){
				System.out.println("E :"+e);
			}
		}
	}
}
