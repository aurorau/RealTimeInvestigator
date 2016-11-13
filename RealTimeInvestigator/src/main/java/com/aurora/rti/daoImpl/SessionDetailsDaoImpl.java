package com.aurora.rti.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.aurora.rti.dao.SessionDetailsDao;
import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.util.AbstractDao;
import com.aurora.rti.util.CurrentUsersDTO;
import com.aurora.rti.util.UserCountDTO;
import com.aurora.rti.util.UserDetailsDTO;

@Repository("sessionDetailsDao")
public class SessionDetailsDaoImpl extends AbstractDao<Integer, SessionDetails> implements SessionDetailsDao {

	public void saveSessionDetails(SessionDetails sessionDetails) {
		persist(sessionDetails);
		
	}
	
	public SessionDetails getSessionDetailsByCreationTimeById(long l, String sessionId) throws Exception{
		SessionDetails sessionDetails = null;
		
		Criteria criteria = createEntityCriteria()
				.add(Restrictions.eq("status", "ACTIVE"))
				.add(Restrictions.eq("sessionId", sessionId));
		sessionDetails = (SessionDetails) criteria.uniqueResult();
		return sessionDetails;
	}

	public SessionDetails getById(Long sid) throws Exception{
		SessionDetails sessionDetails = null;
		
		Criteria criteria = createEntityCriteria()
				.add(Restrictions.eq("SID", sid));
		sessionDetails = (SessionDetails) criteria.uniqueResult();
		return sessionDetails;
	}
	
	public void changeSessionStatus(String currentTime, String beforeTime, String beforeHeartBeatTime)throws Exception{

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

	public List<UserCountDTO> getCurrentUserCountList(String sortField,int order, int start, int gridTableSize, String searchq)throws Exception {
		int count = start;
		
		List<UserCountDTO> dtoList = null;
		List<UserCountDTO> dtoListCount = new ArrayList<UserCountDTO>();
		
		Criteria criteria = createEntityCriteria()
				.add(Restrictions.eq("status", "ACTIVE"));
		criteria.addOrder(Order.asc("SID"));
		criteria.setFirstResult(start)
        		.setMaxResults(gridTableSize);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("cssStatus").as("cssStatus"))
				.add(Projections.property("SID").as("sid")));
		dtoList = criteria.setResultTransformer(Transformers.aliasToBean(UserCountDTO.class)).list();
		
		if(dtoList.size() > 0 ){
			for(UserCountDTO dto : dtoList) {
				dto.setCountId(count+1);
				dtoListCount.add(dto);
				count +=1;
			}	
		}
		return dtoListCount;
	}

	public int getCurrentUserCount(String searchq) throws Exception {
		
		int totalRowCount =0;
		Criteria criteria = createEntityCriteria()
				.add(Restrictions.eq("status", "ACTIVE"))
				.setProjection(Projections.count("SID"));
		
		Long value = (Long) criteria.uniqueResult();
		totalRowCount = Integer.valueOf(value.intValue());
		return totalRowCount;
	}

	public List<SessionDetails> getActiveSessions() throws Exception {
		List<SessionDetails> sessionDetailsList = null;
		
		Criteria criteria = createEntityCriteria()
				.add(Restrictions.eq("status", "ACTIVE"));
		sessionDetailsList = criteria.list();
		return sessionDetailsList;
	}

	public int getSessionTimeOutCountBySessionId(String sessionId)throws Exception {
		int totalRowCount =0;
		Criteria criteria = createEntityCriteria()
				.add(Restrictions.eq("sessionId", sessionId))
				.add(Restrictions.eq("status", "INACTIVE"))
				.setProjection(Projections.count("SID"));
		Long value = (Long) criteria.uniqueResult();
		totalRowCount = Integer.valueOf(value.intValue());
		return totalRowCount;
	}
}
