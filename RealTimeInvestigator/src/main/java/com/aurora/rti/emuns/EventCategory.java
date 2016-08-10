package com.aurora.rti.emuns;

public enum EventCategory {
	MOBILE_CATEGORY("MC"),
	DESKTOP_CATEGORY("DC"),
	COMMON_CATEGORY("CC");

    private String state;
    private EventCategory(final String state){
        this.state = state;
    }
     
    public String getState(){
        return this.state;
    }
 
    @Override
    public String toString(){
        return this.state;
    }
 
    public String getName(){
        return this.name();
    }
}
