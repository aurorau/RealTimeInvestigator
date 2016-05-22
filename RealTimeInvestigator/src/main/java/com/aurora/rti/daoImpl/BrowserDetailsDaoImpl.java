package com.aurora.rti.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.aurora.rti.dao.BrowserDetailsDao;
import com.aurora.rti.model.BrowserDetails;
import com.aurora.rti.util.AbstractDao;
import com.aurora.rti.util.UserDetailsDTO;

@Repository("browserDetailsDao")
public class BrowserDetailsDaoImpl extends AbstractDao<Integer, BrowserDetails> implements BrowserDetailsDao {

	public void saveBrowserDetails(BrowserDetails browserDetails) throws Exception{
		persist(browserDetails);
	}

	public List<UserDetailsDTO> analyseUserBySessionId(Long sid)throws Exception {
		List<UserDetailsDTO> list = null;

		Criteria criteria = createEntityCriteria();
		criteria.createAlias("sessionDetails", "sessionDetails");
		criteria.createAlias("deviceDetails", "deviceDetails");
		criteria.createAlias("eventDetails", "eventDetails");
		criteria.add(Restrictions.eq("sessionDetails.SID", sid));
		criteria.addOrder(Order.desc("eventDetails.EID"));
		
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("sessionDetails.SID").as("sid"))
				.add(Projections.property("sessionDetails.lastAccessTime").as("lastAccessTime"))
				.add(Projections.property("sessionDetails.sessionCreatedTime").as("firstAccessTime"))
				.add(Projections.property("eventDetails.EID").as("eid"))
				.add(Projections.property("eventDetails.triggeredTime").as("eventTriggeredTime"))
				.add(Projections.property("eventDetails.eventTypes").as("eventName"))
				.add(Projections.property("eventDetails.coordinateX").as("coordinateX"))
				.add(Projections.property("eventDetails.coordinateY").as("coordinateY"))
				.add(Projections.property("eventDetails.screenWidth").as("screenWidth"))
				.add(Projections.property("eventDetails.screenHeight").as("screenHeight"))
				.add(Projections.property("eventDetails.orientation").as("orientation"))
				.add(Projections.property("eventDetails.viewportHeight").as("viewportHeight"))
				.add(Projections.property("eventDetails.viewportWidth").as("viewportWidth"))
				.add(Projections.property("eventDetails.numOfTaps").as("numOfTaps"))
				.add(Projections.property("eventDetails.tagName").as("tagName"))
				.add(Projections.property("eventDetails.scrollTop").as("scrollTop"))
				.add(Projections.property("eventDetails.timeZone").as("timeZone"))
				.add(Projections.property("eventDetails.zoneDateTime").as("zoneDateTime"))
				.add(Projections.property("eventDetails.imageName").as("imageName"))
				.add(Projections.property("BID").as("bid"))
				.add(Projections.property("browserName").as("browserName"))
				.add(Projections.property("browserVersion").as("browserVersion"))
				.add(Projections.property("userAgetntId").as("userAgentId"))
				.add(Projections.property("deviceDetails.DID").as("did"))
				.add(Projections.property("deviceDetails.osName").as("osName"))
				.add(Projections.property("deviceDetails.deviceName").as("deviceName")));
		list = criteria.setResultTransformer(Transformers.aliasToBean(UserDetailsDTO.class)).list();
		return list;
	}

	public List<UserDetailsDTO> getUserDetailsBySessionId(String sortField,int order, int start, int gridTableSize, String searchq,Long sessionPK) throws Exception {
		List<UserDetailsDTO> list = null;
		
		Criteria criteria = createEntityCriteria();
			criteria.createAlias("sessionDetails", "sessionDetails");
			criteria.createAlias("deviceDetails", "deviceDetails");
			criteria.createAlias("eventDetails", "eventDetails");
			criteria.add(Restrictions.eq("sessionDetails.SID", sessionPK));
			criteria.addOrder(Order.desc("eventDetails.EID"));
			criteria.setFirstResult(start)
			        .setMaxResults(gridTableSize);
			
			if(!searchq.isEmpty() && searchq != null) {
				criteria.add(Restrictions.disjunction()
				        .add(Restrictions.ilike("deviceDetails.deviceName", searchq, MatchMode.ANYWHERE))
				        .add(Restrictions.ilike("browserName", searchq, MatchMode.ANYWHERE))
				        .add(Restrictions.ilike("eventDetails.eventName", searchq, MatchMode.ANYWHERE))
				        .add(Restrictions.ilike("eventDetails.orientation", searchq, MatchMode.ANYWHERE)));
			}
			
			criteria.setProjection(Projections.projectionList()
					.add(Projections.property("sessionDetails.SID").as("sid"))
					.add(Projections.property("sessionDetails.lastAccessTime").as("lastAccessTime"))
					.add(Projections.property("sessionDetails.sessionCreatedTime").as("firstAccessTime"))
					.add(Projections.property("eventDetails.EID").as("eid"))
					.add(Projections.property("eventDetails.triggeredTime").as("eventTriggeredTime"))
					.add(Projections.property("eventDetails.eventTypes").as("eventName"))
					.add(Projections.property("eventDetails.coordinateX").as("coordinateX"))
					.add(Projections.property("eventDetails.coordinateY").as("coordinateY"))
					.add(Projections.property("eventDetails.screenWidth").as("screenWidth"))
					.add(Projections.property("eventDetails.screenHeight").as("screenHeight"))
					.add(Projections.property("eventDetails.orientation").as("orientation"))
					.add(Projections.property("eventDetails.viewportHeight").as("viewportHeight"))
					.add(Projections.property("eventDetails.viewportWidth").as("viewportWidth"))
					.add(Projections.property("eventDetails.numOfTaps").as("numOfTaps"))
					.add(Projections.property("eventDetails.tagName").as("tagName"))
					.add(Projections.property("eventDetails.scrollTop").as("scrollTop"))
					.add(Projections.property("eventDetails.timeZone").as("timeZone"))
					.add(Projections.property("eventDetails.zoneDateTime").as("zoneDateTime"))
					.add(Projections.property("eventDetails.imageName").as("imageName"))
					.add(Projections.property("BID").as("bid"))
					.add(Projections.property("browserName").as("browserName"))
					.add(Projections.property("browserVersion").as("browserVersion"))
					.add(Projections.property("userAgetntId").as("userAgentId"))
					.add(Projections.property("deviceDetails.DID").as("did"))
					.add(Projections.property("deviceDetails.osName").as("osName"))
					.add(Projections.property("deviceDetails.deviceName").as("deviceName")));
			list = criteria.setResultTransformer(Transformers.aliasToBean(UserDetailsDTO.class)).list();
			return list;
	}

	public int getUserDetailsCountBySessionId(String searchq, Long sessionPK) throws Exception {
		
		int totalRowCount =0;
		Criteria criteria = createEntityCriteria();
				criteria.createAlias("sessionDetails", "sessionDetails");
				criteria.createAlias("deviceDetails", "deviceDetails");
				criteria.createAlias("eventDetails", "eventDetails");
				criteria.add(Restrictions.eq("sessionDetails.SID",sessionPK));
				criteria.setProjection(Projections.count("eventDetails.EID"));
		if(!searchq.isEmpty()) {
			criteria.add(Restrictions.disjunction()
			        .add(Restrictions.ilike("deviceDetails.deviceName", searchq, MatchMode.ANYWHERE))
			        .add(Restrictions.ilike("browserName", searchq, MatchMode.ANYWHERE))
			        .add(Restrictions.ilike("eventDetails.eventName", searchq, MatchMode.ANYWHERE))
			        .add(Restrictions.ilike("eventDetails.orientation", searchq, MatchMode.ANYWHERE)));
		}
		Long value = (Long) criteria.uniqueResult();
		totalRowCount = Integer.valueOf(value.intValue());
		return totalRowCount;
	}
}
