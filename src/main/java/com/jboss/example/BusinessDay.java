package com.jboss.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class BusinessDay {

    private Employee employee;
    private Date date;
    private List<TimeSlice> slices;
    

    public BusinessDay(){
        slices = new ArrayList<TimeSlice>();

    }

    public List<TimeSlice> getSlices() {
        return slices;
    }

    public void setSlices(List<TimeSlice> slices) {
        this.slices = slices;
    }


    public void addTimeSlice(TimeSlice slice){
    	slice.setEmployee(this.getEmployee());
        slice.setBusinessDay(this);
        slices.add(slice);
    }

    public void setDate(Date date){
    	this.date = date;
    }
    
    public void setDate(int year, int month, int date){
    	this.date = new GregorianCalendar(year, month, date).getTime();
    }

    public Date getDate(){
    	return date;
    }
    

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    
}
