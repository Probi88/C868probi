package com.example.c868probi.Model;

import java.time.LocalDateTime;

public class NormalAppointment extends Appointments{

    private String special;

    public NormalAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, int appointmentContact, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int appointmentCustomerID, int appointmentUserID, String special) {
        super(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContact, appointmentType, appointmentStart, appointmentEnd, appointmentCustomerID, appointmentUserID);
        this.special = special;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    @Override
    public String getAppointmentSpecial() {
        return "";
    }

}
