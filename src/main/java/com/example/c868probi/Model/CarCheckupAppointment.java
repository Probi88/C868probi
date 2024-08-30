package com.example.c868probi.Model;

import java.time.LocalDateTime;

public class CarCheckupAppointment extends Appointments{

    private Cars cars;

    private String special;

    public CarCheckupAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, int appointmentContact, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int appointmentCustomerID, int appointmentUserID, String special) {//Cars cars) {
        super(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContact, appointmentType, appointmentStart, appointmentEnd, appointmentCustomerID, appointmentUserID);
        this.special = special;
    }

    public Cars getCars() {
        return cars;
    }

    public void setCars(Cars cars) {
        this.cars = cars;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    @Override
    public String getAppointmentSpecial() {
        return getSpecial();
    }

}
