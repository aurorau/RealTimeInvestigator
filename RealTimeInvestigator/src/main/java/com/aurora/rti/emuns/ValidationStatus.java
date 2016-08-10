package com.aurora.rti.emuns;

public enum ValidationStatus {
    FRAUD("Fraud"),
    NOT_FRAUD("Not_Fraud");
     
    private String state;
     
    private ValidationStatus(final String state){
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
