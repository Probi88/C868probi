package com.example.c868probi;

import com.example.c868probi.Controller.AppointmentsController;
import com.example.c868probi.DAO.AppointmentsDAO;
import com.example.c868probi.Helper.JDBC;
import com.example.c868probi.Model.Appointments;
import com.example.c868probi.Model.Pets;
import com.example.c868probi.Model.VeterinaryAppointment;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.c868probi.DAO.AppointmentsDAO.getAllAppointments;
import static javafx.beans.binding.Bindings.when;
import static org.junit.jupiter.api.Assertions.*;

public class AppointmentControllerTest {

    private static AppointmentsDAO appointmentsDAO;

    @BeforeAll
    public static void setUp(){
        JDBC.openConnection();
    }

    @Test
    public boolean overlapAppointmentTest(Appointments newAppointments){
        List<Appointments> allAppointments = AppointmentsDAO.getAllAppointments();
        for(Appointments appointments : allAppointments){
            if(appointments.getAppointmentID() != newAppointments.getAppointmentID() && appointments.getAppointmentCustomerID() == newAppointments.getAppointmentCustomerID()) {
                LocalDateTime startTime = appointments.getAppointmentStart();
                LocalDateTime endTime = appointments.getAppointmentEnd();
                LocalDateTime startTimeCompare = newAppointments.getAppointmentStart();
                LocalDateTime endTimeCompare = newAppointments.getAppointmentEnd();
                if(startTimeCompare.isAfter(startTime) && startTimeCompare.isBefore(endTime) ||
                        endTimeCompare.isAfter(startTime) && endTimeCompare.isBefore(endTime) ||
                        startTimeCompare.isBefore(startTime) && endTimeCompare.isAfter(endTime) ||
                        (startTimeCompare.equals(startTime) || endTimeCompare.equals(endTime))){
                    return true;
                }
            }
        }
        return false;
    }

    @Test
    public void testOverlap(){
        Appointments existingApp = new VeterinaryAppointment(998, "test1", "test descrip",
                "clinic", 1, "Veterinary", LocalDateTime.now(), LocalDateTime.now().plusHours(2),
                1, 1, "2");
        AppointmentsDAO.addAppointment(existingApp.getAppointmentID(), existingApp.getAppointmentTitle(), existingApp.getAppointmentDescription(),
                existingApp.getAppointmentLocation(), existingApp.getAppointmentContact(), existingApp.getAppointmentType(), existingApp.getAppointmentStart(),
                existingApp.getAppointmentEnd(), existingApp.getAppointmentCustomerID(), existingApp.getAppointmentUserID(), existingApp.getAppointmentSpecial());
        Appointments newAppointment = new VeterinaryAppointment(999, "test2", "test descrip2",
                "clinic", 1, "Veterinary", LocalDateTime.now().plusMinutes(30), LocalDateTime.now().plusHours(3),
                1, 1, "2");
        boolean overlapCheck = overlapAppointmentTest(newAppointment);
        assertTrue(overlapCheck, "appointments do overlap");
        System.out.println("appointments " + existingApp.getAppointmentID() + " and " + newAppointment.getAppointmentID() + " do overlap");
    }

    @Test
    public void testNoOverlap(){
        Appointments existingApp = new VeterinaryAppointment(1000, "test1", "test descrip",
                "clinic", 1, "Veterinary", LocalDateTime.now(), LocalDateTime.now().plusHours(2),
                1, 1, "2");
        AppointmentsDAO.addAppointment(existingApp.getAppointmentID(), existingApp.getAppointmentTitle(), existingApp.getAppointmentDescription(),
                existingApp.getAppointmentLocation(), existingApp.getAppointmentContact(), existingApp.getAppointmentType(), existingApp.getAppointmentStart(),
                existingApp.getAppointmentEnd(), existingApp.getAppointmentCustomerID(), existingApp.getAppointmentUserID(), existingApp.getAppointmentSpecial());
        Appointments newAppointment = new VeterinaryAppointment(1001, "test2", "test descrip2", "clinic",
                1, "Veterinary", LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(5),
                1, 1, "2");
        boolean overlapCheck = overlapAppointmentTest(newAppointment);
        assertFalse(overlapCheck, "appointments do not overlap");
        System.out.println("appointments " + existingApp.getAppointmentID() + " and " + newAppointment.getAppointmentID() + " do not overlap");
    }

    @AfterAll
    public static void tearDown(){
        try {
            String sql = "DELETE FROM appointments WHERE appointment_id >= 998";
            PreparedStatement preparedStatement =JDBC.connection.prepareStatement(sql);
            int stmt = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JDBC.closeConnection();
    }

}
