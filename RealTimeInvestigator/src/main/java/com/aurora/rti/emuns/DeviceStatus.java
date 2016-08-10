package com.aurora.rti.emuns;

public enum DeviceStatus {
    DESKTOP("Desktop"),
    MOBILE("Mobile"),
    TABLET("Tablet"),
    OTHER("Other");
     
    private String deviceStatus;
     
    private DeviceStatus(final String state){
        this.deviceStatus = state;
    }
     
    public String getState(){
        return this.deviceStatus;
    }
 
    @Override
    public String toString(){
        return this.deviceStatus;
    }
 
    public String getName(){
        return this.name();
    }
}
