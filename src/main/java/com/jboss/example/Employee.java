package com.jboss.example;

import java.util.*;
import java.io.*;

public class Employee implements Serializable
{
	
	private static final long serialVersionUID = -2314929432875690308L;
	private String firstName = "Default First Name";
    private String lastName = "Default Last Name";
    private String payruleId = "JTP_CBU072";
    private String cbuCode = "CBU072";
    private Date startDate;
    private int yearsOfService;
    private int monthsInService;
    private long employeeNumber;
    private int perWeekHours = 40;
    private int pmsID = 1;
    private String title = "unknown";
    private String titleSuffix = "";
    private char payClass = 'A';

    private Set<EmployeeClassifiers> classifiers;
    private WeeklyAccumulator schedule;


    public Employee() {

        // Default to example date of 3/11/02
        startDate = new GregorianCalendar(2002,11,3).getTime();


        classifiers = new HashSet<EmployeeClassifiers>();

        // Default to example data
        

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPayruleId() {
        return payruleId;
    }

    public void setPayruleId(String payruleId) {
        this.payruleId = payruleId;
    }

    public String getCbuCode() {
        return cbuCode;
    }

    public void setCbuCode(String cbuCode) {
        this.cbuCode = cbuCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getYearsOfService() {
        return yearsOfService;
    }

    public void setYearsOfService(int yearsOfService) {
        this.yearsOfService = yearsOfService;
    }

    public int getMonthsInService() {
        return monthsInService;
    }

    public void setMonthsInService(int monthsInService) {
        this.monthsInService = monthsInService;
    }

    public long getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(long employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public int getPerWeekHours() {
        return perWeekHours;
    }

    public void setPerWeekHours(int perWeekHours) {
        this.perWeekHours = perWeekHours;
    }

    public int getPmsID() {
        return pmsID;
    }

    public void setPmsID(int pmsID) {
        this.pmsID = pmsID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public char getPayClass() {
        return payClass;
    }

    public void setPayClass(char payClass) {
        this.payClass = payClass;
    }

    public Set<EmployeeClassifiers> getClassifiers() {
        return classifiers;
    }

    public void setClassifiers(Set<EmployeeClassifiers> classifiers) {
        this.classifiers = classifiers;
    }


    public void addClassifier(EmployeeClassifiers classifier){
    
    	this.classifiers.add(classifier);
    }
    
    
    public String getTitleSuffix() {
        return titleSuffix;
    }

    public void setTitleSuffix(String titleSuffix) {
        this.titleSuffix = titleSuffix;
    }

    public WeeklyAccumulator getSchedule() {
        return schedule;
    }

    public void setSchedule(WeeklyAccumulator schedule) {
        this.schedule = schedule;
    }
    
}
