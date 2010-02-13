package com.jboss.example;

import java.util.*;
import java.util.Date;


public class TimeSlice {

    private Date start;
    private Date end;
    private int elapsed;
    private Set<String> classifiers;
    private BusinessDay businessDay;
    private boolean sliceCreatedByRules = false;
    private Employee employee;

    public TimeSlice(){
       classifiers = new HashSet<String>();
    }
                                                           

    
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }
    
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getElapsed() {
        return elapsed;
    }

    public void setElapsed(int duration) {
        this.elapsed = duration;
    }

 
    public void setEmployee(Employee employee){
    	this.employee =employee;
    }
    
    public Employee getEmployee(){
    	return employee;
    }
    
    
    public Set<String> getClassifiers() {
        return classifiers;
    }

    public void setClassifiers(Set<String> classifiers) {
        this.classifiers = classifiers;
    }

    public BusinessDay getBusinessDay() {
        return businessDay;
    }

    public void setBusinessDay(BusinessDay businessDay) {
        this.businessDay = businessDay;
    }

    public void addClassifier(String... classifier ){
	for (String c :  classifier){
        	if (! classifiers.contains(classifier))
    		classifiers.add(c);
    	}
    }
    
    public boolean getSliceCreatedByRules() {
        return sliceCreatedByRules;
    }

    public void setSliceCreatedByRules(boolean sliceCreatedByRules) {
        this.sliceCreatedByRules = sliceCreatedByRules;
    }


}
