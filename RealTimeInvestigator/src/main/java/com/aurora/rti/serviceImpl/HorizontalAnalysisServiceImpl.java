package com.aurora.rti.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aurora.rti.service.HorizontalAnalysisCommonValidation;
import com.aurora.rti.service.HorizontalAnalysisMobileValidation;
import com.aurora.rti.service.HorizontalAnalysisService;
import com.aurora.rti.util.UserDetailsDTO;

@Service("horizontalAnalysisService")
public class HorizontalAnalysisServiceImpl implements HorizontalAnalysisService {

	private HorizontalAnalysisCommonValidation horizontalAnalysisCommonValidation = null;
	private HorizontalAnalysisMobileValidation horizontalAnalysisMobileValidation = null;
	
	@Autowired
	public void setHorizontalAnalysisCommonValidation(HorizontalAnalysisCommonValidation horizontalAnalysisCommonValidation) {
		this.horizontalAnalysisCommonValidation = horizontalAnalysisCommonValidation;
	}
	
	@Autowired
	public void setHorizontalAnalysisMobileValidation(HorizontalAnalysisMobileValidation horizontalAnalysisMobileValidation) {
		this.horizontalAnalysisMobileValidation = horizontalAnalysisMobileValidation;
	}

	public List<UserDetailsDTO> horizontalAnalysis(List<UserDetailsDTO> list) {
		
		String eventStatus = null;
		String commonValidationStatus = null;
		
		List<UserDetailsDTO> returnList = new ArrayList<UserDetailsDTO>();
		
		for(UserDetailsDTO dto:list){
			commonValidationStatus = commonValidation(dto);
			eventStatus = commonValidationStatus;
			dto.setEventStatus(eventStatus);
			returnList.add(dto);
		}
		
		return returnList;
	}
	
	public String commonValidation(UserDetailsDTO dto){
		String tagToImagePathValidationStatus = null;
		String timeValidationStatus = null;
		String eventToDeviceValidationStatus = null;
		String eventToTapCountValidationStatus = null;
		String deviceToPlatformValidationStatus = null;
		String deviceToOrientationValidationStatus = null;
		String deviceToDiamensionValidationStatus = null;
		
		tagToImagePathValidationStatus = horizontalAnalysisCommonValidation.tagToImagePathValidation(dto.getTagName(), dto.getImageName());
		timeValidationStatus = horizontalAnalysisCommonValidation.timeValidation(dto.getEventTriggeredTime(), dto.getZoneDateTime());
		eventToDeviceValidationStatus = horizontalAnalysisCommonValidation.eventToDeviceValidation(dto.getEventCategory(),dto.getDeviceName());
		eventToTapCountValidationStatus = horizontalAnalysisCommonValidation.eventToTapCountValidation(dto.getEventCategory(), dto.getNumOfTaps());
		deviceToPlatformValidationStatus = horizontalAnalysisCommonValidation.deviceToPlatformValidation(dto.getDeviceName(), dto.getPlatform());;
		deviceToOrientationValidationStatus = horizontalAnalysisCommonValidation.deviceToOrientationValidation(dto.getDeviceName(),dto.getOrientation());
		deviceToDiamensionValidationStatus = horizontalAnalysisCommonValidation.deviceToDiamensionValidation(dto.getDeviceName(), dto.getViewportWidth());
		
		System.err.println("================================================================");
		
		System.out.println("TagToImagePathValidationStatus      :"+tagToImagePathValidationStatus);
		System.out.println("TimeValidationStatus                :"+timeValidationStatus);
		System.out.println("EventToDeviceValidationStatus       :"+eventToDeviceValidationStatus);
		System.out.println("EventToTapCountValidationStatus     :"+eventToTapCountValidationStatus);
		System.out.println("DeviceToPlatformValidationStatus    :"+deviceToPlatformValidationStatus);
		System.out.println("DeviceToOrientationValidationStatus :"+deviceToOrientationValidationStatus);
		System.out.println("DeviceToDiamensionValidationStatus  :"+deviceToDiamensionValidationStatus);
		
		System.err.println("================================================================");
		
		return deviceToDiamensionValidationStatus;
	}

}
