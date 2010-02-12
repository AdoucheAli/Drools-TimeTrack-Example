package com.jboss.example;

import java.util.*;
import java.io.*;

public class WeeklyAccumulator implements Serializable
{

	private static final long serialVersionUID = -3456129492169541231L;
	private int dailyHours = 8;
    private int weeklyHours = 40;
    private int weeklyHoursTowardsOT=0;
    private int weeklyHoursTowardsFLSA=0;
    private Set<ScheduleClassifiers> classifiers;


    private List<BusinessDay> daysWorked;

    public WeeklyAccumulator(){
        classifiers = new HashSet<ScheduleClassifiers>();
        daysWorked = new ArrayList<BusinessDay>();
    }


    public int getDailyHours() {
        return dailyHours;
    }

    public void setDailyHours(int dailyHours) {
        this.dailyHours = dailyHours;
    }

    public int getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public Set<ScheduleClassifiers> getClassifiers() {
        return classifiers;
    }

    public void setClassifiers(Set<ScheduleClassifiers> classifiers) {
        this.classifiers = classifiers;
    }

    public void addClassifier(ScheduleClassifiers classifier){
    	this.classifiers.add(classifier);
    }
    
    public int getWeeklyHoursTowardsOT() {
        return weeklyHoursTowardsOT;
    }

    public void setWeeklyHoursTowardsOT(int weeklyHoursTowardsOT) {
        this.weeklyHoursTowardsOT = weeklyHoursTowardsOT;
    }

    public int getWeeklyHoursTowardsFLSA() {
        return weeklyHoursTowardsFLSA;
    }

    public void setWeeklyHoursTowardsFLSA(int weeklyHoursTowardsFLSA) {
        this.weeklyHoursTowardsFLSA = weeklyHoursTowardsFLSA;
    }


    public List<BusinessDay> getDaysWorked() {
        return daysWorked;
    }

    public void setDaysWorked(List<BusinessDay> daysWorked) {
        this.daysWorked = daysWorked;
    }

    public void addDayWorked(BusinessDay businessDay){
        daysWorked.add(businessDay);
    }
    
}
