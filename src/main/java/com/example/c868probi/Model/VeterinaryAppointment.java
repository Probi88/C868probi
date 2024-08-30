package com.example.c868probi.Model;

import java.time.LocalDateTime;

public class VeterinaryAppointment extends Appointments{

    private Pets pets;

    private int petId;

    private String special;

    public VeterinaryAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, int appointmentContact, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int appointmentCustomerID, int appointmentUserID, String special) {
        super(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContact, appointmentType, appointmentStart, appointmentEnd, appointmentCustomerID, appointmentUserID);
        this.special = special;
    }

    public Pets getPets() {
        return pets;
    }

    public void setPets(Pets pets) {
        this.pets = pets;
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
