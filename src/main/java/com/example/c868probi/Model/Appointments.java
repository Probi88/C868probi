package com.example.c868probi.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * model class used for creation of appointments within scheduling application
 * */
public abstract class Appointments {

    private static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

    private int appointmentID;

    private String appointmentTitle;

    private String appointmentDescription;

    private String appointmentLocation;

    private int appointmentContact;

    private String appointmentType;

    private LocalDateTime appointmentStart;

    private LocalDateTime appointmentEnd;

    private int appointmentCustomerID;

    private int appointmentUserID;

    private String appointmentSpecial;

    /**
     * constructor for new appointments
     * @param appointmentID
     * @param appointmentTitle
     * @param appointmentDescription
     * @param appointmentLocation
     * @param appointmentContact employee who was responsible for creating the appointment in the application
     * @param appointmentType
     * @param appointmentStart start date and time of appointment
     * @param appointmentEnd end date and time of appointment
     * @param appointmentCustomerID Id of customer whom the appointment is for
     * @param appointmentUserID value Id of user associated with appointment
     * */
    public Appointments(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, int appointmentContact, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int appointmentCustomerID, int appointmentUserID) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentContact = appointmentContact;
        this.appointmentType = appointmentType;
        this.appointmentStart =  appointmentStart;
        this.appointmentEnd =  appointmentEnd;
        this.appointmentCustomerID = appointmentCustomerID;
        this.appointmentUserID = appointmentUserID;
    }

    /**
     * sets appointment id
     * @param appointmentID
     * */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * sets appointment title
     * @param appointmentTitle
     * */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    /**
     * sets appointment description
     * @param appointmentDescription
     * */
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    /**
     * sets appointment location
     * @param appointmentLocation
     * */
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    /**
     * sets appointment contact
     * @param appointmentContact
     * */
    public void setAppointmentContact(int appointmentContact) {
        this.appointmentContact = appointmentContact;
    }

    /**
     * sets appointment type
     * @param appointmentType
     * */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    /**
     * sets appointment Start date and time
     * @param appointmentStart
     * */
    public void setAppointmentStart(LocalDateTime appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    /**
     * sets appointment End date and time
     * @param appointmentEnd
     * */
    public void setAppointmentEnd(LocalDateTime appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    /**
     * sets appointment customer id
     * @param appointmentCustomerID
     * */
    public void setAppointmentCustomerID(int appointmentCustomerID) {
        this.appointmentCustomerID = appointmentCustomerID;
    }

    /**
     * sets appointment user id
     * @param appointmentUserID
     * */
    public void setAppointmentUserID(int appointmentUserID) {
        this.appointmentUserID = appointmentUserID;
    }

    /**
     * gets appointment id
     * @return appointmentID
     * */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * gets appointment Title
     * @return appointmentTitle
     * */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    /**
     * gets appointment Description
     * @return appointmentDescription
     * */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /**
     * gets appointment Location
     * @return appointmentLocation
     * */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    /**
     * gets appointment Contact
     * @return appointmentContact
     * */
    public int getAppointmentContact() {
        return appointmentContact;
    }

    /**
     * gets appointment Type
     * @return appointmentType
     * */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * gets appointment Start date and time
     * @return appointmentStart
     * */
    public LocalDateTime getAppointmentStart() {
        return appointmentStart;
    }

    /**
     * gets appointment End date and time
     * @return appointmentEnd
     * */
    public LocalDateTime getAppointmentEnd() {
        return appointmentEnd;
    }

    /**
     * gets appointment customer id
     * @return appointmentCustomerID
     * */
    public int getAppointmentCustomerID() {
        return appointmentCustomerID;
    }

    /**
     * gets appointment user id
     * @return appointmentUserID
     * */
    public int getAppointmentUserID() {
        return appointmentUserID;
    }

    public String getAppointmentSpecial() {
        return appointmentSpecial;
    }

    public void setAppointmentSpecial(String appointmentSpecial) {
        this.appointmentSpecial = appointmentSpecial;
    }

    public static ObservableList<Appointments> getAllAppointments() {
        return allAppointments;
    }

    public static void setAllAppointments(ObservableList<Appointments> allAppointments) {
        Appointments.allAppointments = allAppointments;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID=" + appointmentID +
                ", appointmentTitle='" + appointmentTitle + '\'' +
                ", appointmentDescription='" + appointmentDescription + '\'' +
                ", appointmentLocation='" + appointmentLocation + '\'' +
                ", appointmentContact='" + appointmentContact + '\'' +
                ", appointmentType='" + appointmentType + '\'' +
                ", appointmentStart='" + appointmentStart + '\'' +
                ", appointmentEnd='" + appointmentEnd + '\'' +
                ", appointmentCustomerID=" + appointmentCustomerID +
                ", appointmentUserID=" + appointmentUserID +
                '}';
    }

}
