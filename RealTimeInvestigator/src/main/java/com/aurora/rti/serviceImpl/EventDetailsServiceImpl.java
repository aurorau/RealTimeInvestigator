package com.aurora.rti.serviceImpl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aurora.rti.emuns.EventCategory;
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

	private CommonService commonService = null;
	private BrowserDetailsService browserDetailsService = null;
	
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	
	@Autowired
	public void setBrowserDetailsService(BrowserDetailsService browserDetailsService) {
		this.browserDetailsService = browserDetailsService;
	}


	@Transactional
	public String saveEventDetails(EventDetailsDTO dto, SessionDetails sessionDetails, DeviceDetails deviceDetails) throws Exception {
		String res = Constants.FAIL;

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
		 String cssStatus = dto.getCssStatus();
		 
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
		 eventDetails.setCssStatus(cssStatus);
		 
		 if(imageName.equalsIgnoreCase("-1")){
			 eventDetails.setImageName(Constants.NO_IMG);
		 } else {
			 eventDetails.setImageName(imageName); 
		 }
		 
		 if(eventType.equalsIgnoreCase("SE")) {
			 eventDetails.setScrollTop(scrollTop);
		 } else {
			 eventDetails.setScrollTop(elementScrollTopPx); 
		 }
		 
		 if(eventType.equalsIgnoreCase(EventTypes.LEFT_CLICK.getEventTypes())) {
			 eventDetails.setEventName(EventTypes.LEFT_CLICK.name());
			 eventDetails.setEventTypes(EventTypes.LEFT_CLICK.getEventTypes());
			 eventDetails.setEventCategory(EventCategory.DESKTOP_CATEGORY.getState());
			 
		 } else if(eventType.equalsIgnoreCase(EventTypes.RIGHT_CLICK.getEventTypes())) {
			 eventDetails.setEventName(EventTypes.RIGHT_CLICK.name());
			 eventDetails.setEventTypes(EventTypes.RIGHT_CLICK.getEventTypes());
			 eventDetails.setEventCategory(EventCategory.DESKTOP_CATEGORY.getState());
			 
		 } else if(eventType.equalsIgnoreCase(EventTypes.DB_CLICK.getEventTypes())) {
			 eventDetails.setEventName(EventTypes.DB_CLICK.name());
			 eventDetails.setEventTypes(EventTypes.DB_CLICK.getEventTypes());
			 eventDetails.setEventCategory(EventCategory.DESKTOP_CATEGORY.getState());
			 
		 } else if(eventType.equalsIgnoreCase(EventTypes.SCROLL_EVENT.getEventTypes())) {
			 eventDetails.setEventName(EventTypes.SCROLL_EVENT.name());
			 eventDetails.setEventTypes(EventTypes.SCROLL_EVENT.getEventTypes());
			 eventDetails.setEventCategory(EventCategory.DESKTOP_CATEGORY.getState());
			 
		 } else if(eventType.equalsIgnoreCase(EventTypes.TOUCH_START.getEventTypes())) {
			 eventDetails.setEventName(EventTypes.TOUCH_START.name());
			 eventDetails.setEventTypes(EventTypes.TOUCH_START.getEventTypes());
			 eventDetails.setEventCategory(EventCategory.MOBILE_CATEGORY.getState());
			 
		 }  else if(eventType.equalsIgnoreCase(EventTypes.TOUCH_MOVE.getEventTypes())) {
			 eventDetails.setEventName(EventTypes.TOUCH_MOVE.name());
			 eventDetails.setEventTypes(EventTypes.TOUCH_MOVE.getEventTypes());
			 eventDetails.setEventCategory(EventCategory.MOBILE_CATEGORY.getState());
			 
		 }  else if(eventType.equalsIgnoreCase(EventTypes.TOUCH_ZOOM.getEventTypes())) {
			 eventDetails.setEventName(EventTypes.TOUCH_ZOOM.name());
			 eventDetails.setEventTypes(EventTypes.TOUCH_ZOOM.getEventTypes());
			 eventDetails.setEventCategory(EventCategory.MOBILE_CATEGORY.getState());
			 
		 } else if(eventType.equalsIgnoreCase(EventTypes.SWAP_TOUCH_ZOOM.getEventTypes())) {
			 eventDetails.setEventName(EventTypes.SWAP_TOUCH_ZOOM.name());
			 eventDetails.setEventTypes(EventTypes.SWAP_TOUCH_ZOOM.getEventTypes());
			 eventDetails.setEventCategory(EventCategory.MOBILE_CATEGORY.getState());
			 
		 } else if(eventType.equalsIgnoreCase(EventTypes.REFRESH_EVENT.getEventTypes())) {
			 eventDetails.setEventName(EventTypes.REFRESH_EVENT.name());
			 eventDetails.setEventTypes(EventTypes.REFRESH_EVENT.getEventTypes());
			 eventDetails.setEventCategory(EventCategory.COMMON_CATEGORY.getState());
			 
		 } else if(eventType.equalsIgnoreCase(EventTypes.KEY_PRESS.getEventTypes())) {
			 eventDetails.setEventName(EventTypes.KEY_PRESS.name());
			 eventDetails.setEventTypes(EventTypes.KEY_PRESS.getEventTypes());
			 eventDetails.setEventCategory(EventCategory.COMMON_CATEGORY.getState());
			 
		 } else {
			 System.out.println("New Type :"+eventType );
		 }
		 
		 Map<String, String> map = commonService.getCountryDateAndTime(dto.getTimeZoneOffset());
		 
		 eventDetails.setTimeZone(map.get("timeZone"));
		 eventDetails.setZoneDateTime(map.get("dateTime"));
		 
		//eventDetailsDao.saveEventDetails(eventDetails);
		String status = browserDetailsService.saveBrowserDetails(dto, sessionDetails, deviceDetails, eventDetails);
		
		if(status.equalsIgnoreCase(Constants.SUCCESS)){
			res = Constants.SUCCESS;
		}

		return res;
	}

}
