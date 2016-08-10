package com.aurora.rti.service;

public interface HorizontalAnalysisCommonValidation {
	String tagToImagePathValidation(String tagName, String imagePath);
	String timeValidation(String clientTime, String actualTime);
	String eventToDeviceValidation(String eventCategory, String device);
	String deviceToPlatformValidation(String deviceName, String platform);
	String deviceToOrientationValidation(String deviceName, String orientation);
	String eventToTapCountValidation(String eventCategory, String numOfTaps);
	String deviceToDiamensionValidation(String deviceName, String viewportWidth);
}
