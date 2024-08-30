package com.example.c868probi.Model;

import java.time.LocalDateTime;

/**
 * model class used for reports of reports within appointments page
 * */
public class Reports {

    private int contactId;

    private int contactAppointmentId;

    private String contactTitle;

    private String contactDescription;

    private String contactLocation;

    private int Contact;

    private String contactType;

    private String month;

    private Integer total;

    private LocalDateTime contactStart;

    private LocalDateTime contactEnd;

    private int contactCustomerId;

    private int contactUserID;

    private Integer totalCustomers;

    private String Division;

    /**
     * constructor for new report object for total amount of appointments within each month
     * @param total
     * @param month
     * */
    public Reports(Integer total, String month) {
        this.total = total;
        this.month = month;
    }

    /**
     * constructor for new report object for appointements based on contact
     * @param contactAppointmentId
     * @param contactTitle
     * @param contactDescription
     * @param contactType
     * @param contactStart
     * @param contactEnd
     * @param contactCustomerId
     * */
    public Reports(int contactAppointmentId, String contactTitle, String contactDescription, String contactType, LocalDateTime contactStart, LocalDateTime contactEnd, int contactCustomerId) {
        this.contactAppointmentId = contactAppointmentId;
        this.contactTitle = contactTitle;
        this.contactDescription = contactDescription;
        this.contactType = contactType;
        this.contactStart = contactStart;
        this.contactEnd =  contactEnd;
        this.contactCustomerId = contactCustomerId;
    }

    /**
     * constructor for new report object for total amount of customers within first level divisions
     * @param Division
     * @param totalCustomers
     * */
    public Reports(String Division, Integer totalCustomers) {
        this.Division = Division;
        this.totalCustomers = totalCustomers;
    }

    /**
     * gets contact id
     * @return contactId
     * */
    public int getContactID() {
        return contactId;
    }

    /**
     * sets contact id
     * @param contactId
     * */
    public void setContactID(int contactId) {
        this.contactId = contactId;
    }

    /**
     *gets appointment id
     * @return contactAppointmentId
     * */
    public int getAppointmentID() {
        return contactAppointmentId;
    }

    /**
     * sets appointment id
     * @param contactAppointmentId
     * */
    public void setAppointmentID(int contactAppointmentId) {
        this.contactAppointmentId = contactAppointmentId;
    }

    /**
     * gets appointment title
     * @return contactTitle
     * */
    public String getAppointmentTitle() {
        return contactTitle;
    }

    /**
     * sets appointment title
     * @param contactTitle
     * */
    public void setAppointmentTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    /**
     *gets appointment description
     * @return contactDescription
     * */
    public String getAppointmentDescription() {
        return contactDescription;
    }

    /**
     *sets appointment description
     * @param contactDescription
     * */
    public void setAppointmentDescription(String contactDescription) {
        this.contactDescription = contactDescription;
    }

    /**
     * gets appointment location
     * @return contactLocation
     * */
    public String getAppointmentLocation() {
        return contactLocation;
    }

    /**
     * sets appointment location
     * @param contactLocation
     * */
    public void setAppointmentLocation(String contactLocation) {
        this.contactLocation = contactLocation;
    }

    /**
     * gets appointment contact
     * @return Contact
     * */
    public int getAppointmentContact() {
        return Contact;
    }

    /**
     * sets appointment contact
     * @param Contact
     * */
    public void setAppointmentContact(int Contact) {
        this.Contact = Contact;
    }

    /**
     * gets appointment type
     * @return contactType
     * */
    public String getContactType() {
        return contactType;
    }

    /**
     * sets appointment type
     * @param contactType
     * */
    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    /**
     * gets appointment start date and time
     * @return contactStart
     * */
    public LocalDateTime getAppointmentStart() {
        return contactStart;
    }

    /**
     * sets appointment start date and time
     * @param contactStart
     * */
    public void setAppointmentStart(LocalDateTime contactStart) {
        this.contactStart = contactStart;
    }

    /**
     * gets appointment end date and time
     * @return contactEnd
     * */
    public LocalDateTime getAppointmentEnd() {
        return contactEnd;
    }

    /**
     * sets appointment end date and time
     * @param contactEnd
     * */
    public void setAppointmentEnd(LocalDateTime contactEnd) {
        this.contactEnd = contactEnd;
    }

    /**
     * gets customer Id
     * @return contactCustomerId
     * */
    public int getAppointmentCustomerID() {
        return contactCustomerId;
    }

    /**
     * sets customer id
     * @param contactCustomerId
     * */
    public void setAppointmentCustomerID(int contactCustomerId) {
        this.contactCustomerId = contactCustomerId;
    }

    /**
     * gets user id
     * @return contactUserId
     * */
    public int getAppointmentUserID() {
        return contactUserID;
    }

    /**
     * sets user id
     * @param contactUserID
     * */
    public void setAppointmentUserID(int contactUserID) {
        this.contactUserID = contactUserID;
    }

    /**
     * gets month
     * @return month
     * */
    public String getMonth() {
        return month;
    }

    /**
     * sets month
     * @param month
     * */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * gets total number
     * @return total
     * */
    public Integer getTotal() {
        return total;
    }

    /**
     * sets total number
     * @param total
     * */
    public void setAppointmentTotal(Integer total) {
        this.total = total;
    }

    /**
     * gets total customers number
     * @return totalCustomers
     * */
    public Integer getTotalCustomers() {
        return totalCustomers;
    }

    /**
     * sets total customers number
     * @param totalCustomers
     * */
    public void setTotalCustomers(Integer totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    /**
     * gets first level division
     * @return Division
     * */
    public String getDivision() {
        return Division;
    }

    /**
     * sets first level division
     * @param division
     * */
    public void setDivision(String division) {
        Division = division;
    }
}
