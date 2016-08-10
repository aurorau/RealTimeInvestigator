package com.aurora.rti.serviceImpl;

import org.springframework.stereotype.Service;
import com.aurora.rti.emuns.DevicePlatforms;
import com.aurora.rti.emuns.DeviceStatus;
import com.aurora.rti.emuns.EventCategory;
import com.aurora.rti.emuns.ValidationStatus;
import com.aurora.rti.service.HorizontalAnalysisCommonValidation;
import com.aurora.rti.util.Constants;

@Service("horizontalAnalysisCommonValidation")
public class HorizontalAnalysisCommonValidationImpl implements HorizontalAnalysisCommonValidation {

	public String tagToImagePathValidation(String tagName, String imagePath) {
		String status = null;
		if(tagName != null && imagePath != null){
			if(tagName.equalsIgnoreCase(Constants.IMG_TAG) && imagePath.equalsIgnoreCase(Constants.NO_IMG)){
				status = ValidationStatus.FRAUD.getName();
			} else {
				status = ValidationStatus.NOT_FRAUD.getName();
			}
		} else {
			status = ValidationStatus.FRAUD.getName();
		}
		return status;
	}

	public String timeValidation(String clientTime, String actualTime) {
		String status = null;
		int clientHours = 0;
		int actualHours = 0;
		
		if(clientTime != null && actualTime != null){
			clientHours = getHours(clientTime);
			actualHours = getHours(actualTime);
			if(clientHours != actualHours){
				status = ValidationStatus.FRAUD.getName();
			} else {
				status = ValidationStatus.NOT_FRAUD.getName();
			}
		} else {
			status = ValidationStatus.FRAUD.getName();
		}
		return status;
	}
	
	public String eventToDeviceValidation(String eventCategory, String device) {
		String status = null;
		
		if(eventCategory != null && device != null){
			if(eventCategory.equalsIgnoreCase(EventCategory.DESKTOP_CATEGORY.getState()) && device.equalsIgnoreCase(DeviceStatus.DESKTOP.getName())){
				status = ValidationStatus.NOT_FRAUD.getName();
			} else if(eventCategory.equalsIgnoreCase(EventCategory.MOBILE_CATEGORY.getState()) && (device.equalsIgnoreCase(DeviceStatus.MOBILE.getName()) || device.equalsIgnoreCase(DeviceStatus.TABLET.getName()))){
				status = ValidationStatus.NOT_FRAUD.getName();
			} else if(eventCategory.equalsIgnoreCase(EventCategory.COMMON_CATEGORY.getState()) && (device.equalsIgnoreCase(DeviceStatus.MOBILE.getName()) || device.equalsIgnoreCase(DeviceStatus.DESKTOP.getName()))){
				status = ValidationStatus.NOT_FRAUD.getName();
			} else {
				status = ValidationStatus.FRAUD.getName();
			}	
		} else {
			status = ValidationStatus.FRAUD.getName();
		}
		return status;
	}
	
	public String deviceToPlatformValidation(String deviceName, String platform) {
		String status = null;
		
		if(deviceName != null && platform != null){
			if(deviceName.equalsIgnoreCase(DeviceStatus.DESKTOP.getName()) && platform.equalsIgnoreCase(DevicePlatforms.UNKNOWN.getName())){
				status = ValidationStatus.NOT_FRAUD.getName();
			} else if((deviceName.equalsIgnoreCase(DeviceStatus.MOBILE.getName()) || deviceName.equalsIgnoreCase(DeviceStatus.TABLET.getName())) && (platform.equalsIgnoreCase(DevicePlatforms.ANDROID.getName()) || platform.equalsIgnoreCase(DevicePlatforms.IOS.getName()))){
				status = ValidationStatus.NOT_FRAUD.getName();
			} else {
				status = ValidationStatus.FRAUD.getName();
			}
		} else {
			status = ValidationStatus.FRAUD.getName();
		}
		return status;
	}
	
	public String deviceToOrientationValidation(String deviceName, String orientation) {
		String status = null;
		if(deviceName != null && orientation != null){
			if(deviceName.equalsIgnoreCase(DeviceStatus.DESKTOP.getName()) && orientation.equalsIgnoreCase("-1")){
				status = ValidationStatus.NOT_FRAUD.getName();
			} else if((deviceName.equalsIgnoreCase(DeviceStatus.MOBILE.getName()) || deviceName.equalsIgnoreCase(DeviceStatus.TABLET.getName())) && (orientation.equalsIgnoreCase("0") || orientation.equalsIgnoreCase("-90") || orientation.equalsIgnoreCase("90") || orientation.equalsIgnoreCase("180"))){
				status = ValidationStatus.NOT_FRAUD.getName();
			} else {
				status = ValidationStatus.FRAUD.getName();
			}
		} else {
			status = ValidationStatus.FRAUD.getName();
		}
		return status;
	}
	

	public String eventToTapCountValidation(String eventCategory, String numOfTaps) {
		String status = null;
		if(eventCategory != null && numOfTaps != null){
			if((eventCategory.equalsIgnoreCase(EventCategory.DESKTOP_CATEGORY.getState()) || eventCategory.equalsIgnoreCase(EventCategory.COMMON_CATEGORY.getState())) && numOfTaps.equalsIgnoreCase("-1")){
				status = ValidationStatus.NOT_FRAUD.getName();
			} else if(eventCategory.equalsIgnoreCase(EventCategory.MOBILE_CATEGORY.getState()) && (numOfTaps.equalsIgnoreCase("1") || numOfTaps.equalsIgnoreCase("2"))){
				status = ValidationStatus.NOT_FRAUD.getName();
			} else {
				status = ValidationStatus.FRAUD.getName();
			}
		} else {
			status = ValidationStatus.FRAUD.getName();
		}
		return status;
	}
	
	public String deviceToDiamensionValidation(String deviceName, String viewportWidth) {
		String status = null;
		if(deviceName != null && viewportWidth != null){
			if(deviceName.equalsIgnoreCase(DeviceStatus.DESKTOP.getName()) && (Integer.parseInt(viewportWidth) > 991)){
				status = ValidationStatus.NOT_FRAUD.getName();
			} else if(deviceName.equalsIgnoreCase(DeviceStatus.TABLET.getName()) && (768 < Integer.parseInt(viewportWidth) && Integer.parseInt(viewportWidth) <= 991)) {
				status = ValidationStatus.NOT_FRAUD.getName();
			} else if(deviceName.equalsIgnoreCase(DeviceStatus.MOBILE.getName()) && (Integer.parseInt(viewportWidth) <= 768)){
				status = ValidationStatus.NOT_FRAUD.getName();
			} else {
				status = ValidationStatus.FRAUD.getName();
			}
		} else {
			status = ValidationStatus.FRAUD.getName();
		}
		return status;
	}
	
	public int getHours(String dateTime){
		String hours = null;
		int returnVal = 0;
		hours = dateTime.substring(dateTime.indexOf(" ")+1, dateTime.indexOf(":"));
		returnVal = Integer.parseInt(hours);
		return returnVal;
	}

}
