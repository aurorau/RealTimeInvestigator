package com.aurora.rti.serviceImpl;

import javax.transaction.Transactional;
import nl.bitwalker.useragentutils.Manufacturer;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aurora.rti.dao.DeviceDetailsDao;
import com.aurora.rti.model.DeviceDetails;
import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.service.DeviceDetailsService;
import com.aurora.rti.service.EventDetailsService;
import com.aurora.rti.util.Constants;
import com.aurora.rti.util.EventDetailsDTO;

@Service("deviceDetailsService")
public class DeviceDetailsServiceImpl implements DeviceDetailsService {
	//private static final Logger logger = Logger.getLogger(SessionDetailsServiceImpl.class);
	private EventDetailsService eventDetailsService = null;
	private DeviceDetailsDao deviceDetailsDao = null;
	
	@Autowired
	public void setEventDetailsService(EventDetailsService eventDetailsService) {
		this.eventDetailsService = eventDetailsService;
	}
	
	@Autowired
	public void setDeviceDetailsDao(DeviceDetailsDao deviceDetailsDao) {
		this.deviceDetailsDao = deviceDetailsDao;
	}
	
	@Transactional
	public String saveDeviceDetails(EventDetailsDTO dto, SessionDetails sessionDetails) {
		String res = Constants.FAIL;
		
		try {
			UserAgent userAgent1 = UserAgent.parseUserAgentString(dto.getUserAgent());
			OperatingSystem agent = userAgent1.getOperatingSystem(); 
	        String deviceName = agent.getDeviceType().getName();
	        String osName = agent.getName();
	        Manufacturer osManufacture = agent.getManufacturer();
	        String orientation = dto.getOrientation();
	        
			DeviceDetails deviceDetails = new DeviceDetails();
			deviceDetails.setDeviceName(deviceName);
			deviceDetails.setOrientation(orientation);
			deviceDetails.setOsManufacture(osManufacture.toString());
			deviceDetails.setOsName(osName);
			
			deviceDetailsDao.saveDeviceDetails(deviceDetails);
			String status = eventDetailsService.saveEventDetails(dto, sessionDetails, deviceDetails);
			
			if(status.equalsIgnoreCase(Constants.SUCCESS)){
				res = Constants.SUCCESS;
			}
		} catch(Exception e) {
			//logger.error("++++++++++ Error in saveDeviceDetails in DeviceDetailsServiceImpl :"+e);
		}
		return res;
	}

}
