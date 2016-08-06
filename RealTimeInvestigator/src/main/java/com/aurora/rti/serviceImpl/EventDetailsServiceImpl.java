package com.aurora.rti.serviceImpl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aurora.rti.dao.EventDetailsDao;
import com.aurora.rti.emuns.EventTypes;
import com.aurora.rti.model.DeviceDetails;
import com.aurora.rti.model.EventDetails;
import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.service.BrowserDetailsService;
import com.aurora.rti.service.CommonService;
import com.aurora.rti.service.EventDetailsService;
import com.aurora.rti.util.Constants;
import com.aurora.rti.util.EventDetailsDTO;

@Service("eventDetailsService")
public class EventDetailsServiceImpl implements EventDetailsService {

	//private static final Logger logger = Logger.getLogger(EventDetailsServiceImpl.class);
	private CommonService commonService = null;
	private BrowserDetailsService browserDetailsService = null;
	private EventDetailsDao eventDetailsDao = null;
	
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	
	@Autowired
	public void setBrowserDetailsService(BrowserDetailsService browserDetailsService) {
		this.browserDetailsService = browserDetailsService;
	}

	@Autowired
	public void setEventDetailsDao(EventDetailsDao eventDetailsDao) {
		this.eventDetailsDao = eventDetailsDao;
	}

	@Transactional
	public String saveEventDetails(EventDetailsDTO dto, SessionDetails sessionDetails, DeviceDetails deviceDetails) {
		String res = Constants.FAIL;

		try {
			 String orientation = dto.getOrientation();
			 String eventType = dto.getEventType();
			 String eventTriggeredTime = dto.getEventTriggeredTime();
			 String coordinateX = dto.getCoordinateX();
			 String coordinateY = dto.getCoordinateY();
			 String screenWidth = dto.getScreenWidth();
			 String screenHeight = dto.getScreenHeight();
			 String viewportHeight = dto.getViewportHeight();
			 String viewportWidth = dto.getViewportWidth();
			 String numberOfFingers = dto.getNumberOfFingers();
			 String tagName = dto.getTagName();
			 String elementScrollTopPx = dto.getElementScrollTop();
			 String scrollTop = dto.getScrollTopPx();
			 String imageName = dto.getImageName();
			 
			 EventDetails eventDetails = new EventDetails();
			 
			 eventDetails.setCoordinateX(coordinateX);
			 eventDetails.setCoordinateY(coordinateY);
			 eventDetails.setTriggeredTime(eventTriggeredTime);
			 eventDetails.setScreenWidth(screenWidth);
			 eventDetails.setScreenHeight(screenHeight);
			 eventDetails.setOrientation(orientation);
			 eventDetails.setNumOfTaps(numberOfFingers);
			 eventDetails.setViewportHeight(viewportHeight);
			 eventDetails.setViewportWidth(viewportWidth);
			 eventDetails.setTagName(tagName);
			 
			 if(imageName.equalsIgnoreCase("-1")){
				 eventDetails.setImageName("No Image");
			 } else {
				 eventDetails.setImageName(imageName); 
			 }
			 
			 if(eventType.equalsIgnoreCase("SE")) {
				 eventDetails.setScrollTop(scrollTop);
			 } else {
				 eventDetails.setScrollTop(elementScrollTopPx); 
			 }
			 
			 if(eventType.equalsIgnoreCase("LC")) {
				 eventDetails.setEventName(EventTypes.LEFT_CLICK.name());
				 eventDetails.setEventTypes(EventTypes.LEFT_CLICK.getEventTypes());
				 
			 } else if(eventType.equalsIgnoreCase("RC")) {
				 eventDetails.setEventName(EventTypes.RIGHT_CLICK.name());
				 eventDetails.setEventTypes(EventTypes.RIGHT_CLICK.getEventTypes());
				 
			 } else if(eventType.equalsIgnoreCase("DC")) {
				 eventDetails.setEventName(EventTypes.DB_CLICK.name());
				 eventDetails.setEventTypes(EventTypes.DB_CLICK.getEventTypes());
				 
			 } else if(eventType.equalsIgnoreCase("KP")) {
				 eventDetails.setEventName(EventTypes.KEY_PRESS.name());
				 eventDetails.setEventTypes(EventTypes.KEY_PRESS.getEventTypes());
				 
			 } else if(eventType.equalsIgnoreCase("SE")) {
				 eventDetails.setEventName(EventTypes.SCROLL_EVENT.name());
				 eventDetails.setEventTypes(EventTypes.SCROLL_EVENT.getEventTypes());
				 
			 } else if(eventType.equalsIgnoreCase("TE")) {
				 eventDetails.setEventName(EventTypes.TOUCH_EVENT.name());
				 eventDetails.setEventTypes(EventTypes.TOUCH_EVENT.getEventTypes());
				 
			 } else if(eventType.equalsIgnoreCase("DZE")) {
				 eventDetails.setEventName(EventTypes.DESKTOP_ZOOM.name());
				 eventDetails.setEventTypes(EventTypes.DESKTOP_ZOOM.getEventTypes());
				 
			 } else if(eventType.equalsIgnoreCase("TZE")) {
				 eventDetails.setEventName(EventTypes.TOUCH_ZOOM.name());
				 eventDetails.setEventTypes(EventTypes.TOUCH_ZOOM.getEventTypes());
				 
			 } else if(eventType.equalsIgnoreCase("TZE_SE")) {
				 eventDetails.setEventName(EventTypes.TOUCH_ZOOM_EVENT_SCROLL.name());
				 eventDetails.setEventTypes(EventTypes.TOUCH_ZOOM_EVENT_SCROLL.getEventTypes());
				 
			 }  else if(eventType.equalsIgnoreCase("TE_SE")) {
				 eventDetails.setEventName(EventTypes.TOUCH_SCROLL.name());
				 eventDetails.setEventTypes(EventTypes.TOUCH_SCROLL.getEventTypes());
				 
			 }  else if(eventType.equalsIgnoreCase("TM")) {
				 eventDetails.setEventName(EventTypes.TOUCH_MOVE.name());
				 eventDetails.setEventTypes(EventTypes.TOUCH_MOVE.getEventTypes());
				 
			 } else if(eventType.equalsIgnoreCase("DSE")) {
				 eventDetails.setEventName(EventTypes.DESKTOP_SCROLL.name());
				 eventDetails.setEventTypes(EventTypes.DESKTOP_SCROLL.getEventTypes());
				 
			 } else if(eventType.equalsIgnoreCase("TS")) {
				 eventDetails.setEventName(EventTypes.TOUCH_START.name());
				 eventDetails.setEventTypes(EventTypes.TOUCH_START.getEventTypes());
				 
			 } else if(eventType.equalsIgnoreCase("RF")) {
				 eventDetails.setEventName(EventTypes.REFRESH_EVENT.name());
				 eventDetails.setEventTypes(EventTypes.REFRESH_EVENT.getEventTypes());
				 
			 } else if(eventType.equalsIgnoreCase("STZE")) {
				 eventDetails.setEventName(EventTypes.SWAP_TOUCH_ZOOM.name());
				 eventDetails.setEventTypes(EventTypes.SWAP_TOUCH_ZOOM.getEventTypes());
				 
			 } else if(eventType.equalsIgnoreCase("TSE")) {
				 eventDetails.setEventName(EventTypes.TOUCH_SCROLL_EVENT.name());
				 eventDetails.setEventTypes(EventTypes.TOUCH_SCROLL_EVENT.getEventTypes());
				 
			 } else {
				 System.out.println("New Type :"+eventType );
			 }
			 
			 Map<String, String> map = commonService.getCountryDateAndTime(dto.getTimeZoneOffset());
			 
			 eventDetails.setTimeZone(map.get("timeZone"));
			 eventDetails.setZoneDateTime(map.get("dateTime"));
			 
			eventDetailsDao.saveEventDetails(eventDetails);
			String status = browserDetailsService.saveBrowserDetails(dto, sessionDetails, deviceDetails, eventDetails);
			
			if(status.equalsIgnoreCase(Constants.SUCCESS)){
				res = Constants.SUCCESS;
			}
		} catch(Exception e) {
			//logger.error("++++++++++ Error in saveEventDetails in EventDetailsServiceImpl :"+e);
		}
		return res;
	}

}
