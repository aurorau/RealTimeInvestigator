package com.aurora.rti.util;

public class UserCountDTO {
	private Long sid;
	private int countId;
	private String deviceType;
	private String cssStatus;
	
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public int getCountId() {
		return countId;
	}
	public void setCountId(int countId) {
		this.countId = countId;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getCssStatus() {
		return cssStatus;
	}
	public void setCssStatus(String cssStatus) {
		this.cssStatus = cssStatus;
	}
	
}
