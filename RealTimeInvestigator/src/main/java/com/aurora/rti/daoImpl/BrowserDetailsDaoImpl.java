package com.aurora.rti.daoImpl;

import org.springframework.stereotype.Repository;
import com.aurora.rti.dao.BrowserDetailsDao;
import com.aurora.rti.model.BrowserDetails;
import com.aurora.rti.util.AbstractDao;

@Repository("browserDetailsDao")
public class BrowserDetailsDaoImpl extends AbstractDao<Integer, BrowserDetails> implements BrowserDetailsDao {

	public void saveBrowserDetails(BrowserDetails browserDetails) {
		persist(browserDetails);
	}

}
