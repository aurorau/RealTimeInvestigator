package com.aurora.rti.daoImpl;

import org.springframework.stereotype.Repository;

import com.aurora.rti.dao.DeviceDetailsDao;
import com.aurora.rti.model.DeviceDetails;
import com.aurora.rti.util.AbstractDao;

@Repository("deviceDetailsDao")
public class DeviceDetailsDaoImpl extends AbstractDao<Integer, DeviceDetails> implements DeviceDetailsDao {

	public void saveDeviceDetails(DeviceDetails deviceDetails) {
		persist(deviceDetails);
	}

}
