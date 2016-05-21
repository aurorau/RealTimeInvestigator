package com.aurora.rti.daoImpl;

import org.springframework.stereotype.Repository;
import com.aurora.rti.dao.EventDetailsDao;
import com.aurora.rti.model.EventDetails;
import com.aurora.rti.util.AbstractDao;

@Repository("eventDetailsDao")
public class EventDetailsDaoImpl extends AbstractDao<Integer, EventDetails> implements EventDetailsDao {

	public void saveEventDetails(EventDetails eventDetails) {
		persist(eventDetails);
	}

}
