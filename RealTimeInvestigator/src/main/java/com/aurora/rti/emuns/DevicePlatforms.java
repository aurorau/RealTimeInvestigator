package com.aurora.rti.emuns;

public enum DevicePlatforms {
    ANDROID("Android"),
    IOS("Ios"),
    UNKNOWN("Unknown"),
    OTHER("Other");
     
    private String platformStatus;
     
    private DevicePlatforms(final String state){
        this.platformStatus = state;
    }
     
    public String getState(){
        return this.platformStatus;
    }
 
    @Override
    public String toString(){
        return this.platformStatus;
    }
 
    public String getName(){
        return this.name();
    }
}
