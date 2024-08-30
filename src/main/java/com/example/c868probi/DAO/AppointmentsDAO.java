package com.example.c868probi.DAO;

import com.example.c868probi.Helper.JDBC;
import com.example.c868probi.Model.Appointments;
import com.example.c868probi.Model.CarCheckupAppointment;
import com.example.c868probi.Model.NormalAppointment;
import com.example.c868probi.Model.VeterinaryAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * this class handles database SQL query's for appointments
 * */
public class AppointmentsDAO {

    /**
     * retrieves all appointments from SQL database
     * @return allAppointments
     * */
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments ORDER BY Appointment_ID";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultset = preparedStatement.executeQuery();
            while(resultset.next()){
                int appointmentID = resultset.getInt("Appointment_ID");
                String appointmentTitle = resultset.getString("Title");
                String appointmentDescription = resultset.getString("Description");
                String appointmentLocation = resultset.getString("Location");
                int appointmentContact = resultset.getInt("Contact_ID");
                String appointmentType = resultset.getString("Type");
                Timestamp appointmentStart = resultset.getTimestamp("Start");
                Timestamp appointmentEnd = resultset.getTimestamp("End");
                int appointmentCustomerID = resultset.getInt("Customer_ID");
                int appointmentUserID = resultset.getInt("User_ID");
                String appointmentSpecial = resultset.getString("Special");

                Appointments appointments;
                if("Veterinary".equals(appointmentType)){
                    appointments = new VeterinaryAppointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContact, appointmentType, appointmentStart.toLocalDateTime(), appointmentEnd.toLocalDateTime(), appointmentCustomerID, appointmentUserID, appointmentSpecial);
                } else if("Car Checkup".equals(appointmentType)){
                    appointments = new CarCheckupAppointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContact, appointmentType, appointmentStart.toLocalDateTime(), appointmentEnd.toLocalDateTime(), appointmentCustomerID, appointmentUserID, appointmentSpecial);
                } else {
                    appointments = new NormalAppointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContact, appointmentType, appointmentStart.toLocalDateTime(), appointmentEnd.toLocalDateTime(), appointmentCustomerID, appointmentUserID, appointmentSpecial);
                }
                allAppointments.add(appointments);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    return allAppointments;

    }

    /**
     * this adds a new appointment to the SQL database
     * @param appointmentID
     * @param appointmentTitle
     * @param appointmentDescription
     * @param appointmentLocation
     * @param appointmentContact
     * @param appointmentType
     * @param appointmentStart
     * @param appointmentEnd
     * @param appointmentCustomerID
     * @param appointmentUserID
     * */
    public static void addAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, int appointmentContact, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int appointmentCustomerID, int appointmentUserID){
        try{
            int newAppointmentId = nextAppointmentId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try{
            String sql = "INSERT INTO appointments(Appointment_ID, Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            preparedstatement.setInt(1, appointmentID);
            preparedstatement.setString(2, appointmentTitle);
            preparedstatement.setString(3, appointmentDescription);
            preparedstatement.setString(4, appointmentLocation);
            preparedstatement.setInt(5, appointmentContact);
            preparedstatement.setString(6, appointmentType);
            preparedstatement.setTimestamp(7, Timestamp.valueOf(appointmentStart));
            preparedstatement.setTimestamp(8, Timestamp.valueOf(appointmentEnd));
            preparedstatement.setInt(9, appointmentCustomerID);
            preparedstatement.setInt(10, appointmentUserID);
            int rowsAffected = preparedstatement.executeUpdate();
            if(rowsAffected > 0) {
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Failed to insert a new appointment record");
                alert.showAndWait();
                throw new RuntimeException("Failed to insert a new appointment record");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * this adds a new appointment to the SQL database
     * @param appointmentID
     * @param appointmentTitle
     * @param appointmentDescription
     * @param appointmentLocation
     * @param appointmentContact
     * @param appointmentType
     * @param appointmentStart
     * @param appointmentEnd
     * @param appointmentCustomerID
     * @param appointmentUserID
     * */
    public static void addAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, int appointmentContact, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int appointmentCustomerID, int appointmentUserID, String appointmentSpecial){
       try{
           int newAppointmentId = nextAppointmentId();
           String special = null;
           if(appointmentType.equals("Veterinary") && !appointmentSpecial.contains("Pet Id:")){
               special = "Pet Id: " + appointmentSpecial;
           } else if(appointmentType.equals("Car Checkup") && !appointmentSpecial.contains("Car Id:")){
               special = "Car Id: " + appointmentSpecial;
           } else {
               special = appointmentSpecial;
           }
            String sql = "INSERT INTO appointments(Appointment_ID, Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID, Special) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            preparedstatement.setInt(1, appointmentID);
            preparedstatement.setString(2, appointmentTitle);
            preparedstatement.setString(3, appointmentDescription);
            preparedstatement.setString(4, appointmentLocation);
            preparedstatement.setInt(5, appointmentContact);
            preparedstatement.setString(6, appointmentType);
            preparedstatement.setTimestamp(7, Timestamp.valueOf(appointmentStart));
            preparedstatement.setTimestamp(8, Timestamp.valueOf(appointmentEnd));
            preparedstatement.setInt(9, appointmentCustomerID);
            preparedstatement.setInt(10, appointmentUserID);
            preparedstatement.setString(11, special);
            int rowsAffected = preparedstatement.executeUpdate();
        if(rowsAffected > 0) {
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed to insert a new appointment record");
            alert.showAndWait();
            throw new RuntimeException("Failed to insert a new appointment record");
        }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * used to find the maximum appointment id from the database
     * @return next available appointment id
     * */
    public static int nextAppointmentId() throws SQLException {
        String sql = "SELECT max(Appointment_ID) FROM appointments";
        PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedstatement.executeQuery(sql);

        if(resultSet.next()){
            int newAppointmentId = resultSet.getInt(1);
            return newAppointmentId + 1;
        } else {
            return 1;
        }

    }

    /**
     * deletes an appointment from the SQL database if it matches a specific appointment id
     * @param appointmentID
     * @return returns false if unable to delete an appointment
     * */
    public static boolean deleteAppointment(int appointmentID){
        try{
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            preparedstatement.setInt(1,appointmentID);
            int stmt = preparedstatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * modifies the appointments details in the SQL database based on a specific appointment id
     * @param appointmentID
     * @param appointmentTitle
     * @param appointmentDescription
     * @param appointmentLocation
     * @param appointmentContact
     * @param appointmentType
     * @param appointmentStart
     * @param appointmentEnd
     * @param appointmentCustomerID
     * @param appointmentUserID
     * */
    public static void modifyAppointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, int appointmentContact, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int appointmentCustomerID, int appointmentUserID, String appointmentSpecial){
        try{
        String special = appointmentSpecial;
        if("Veterinary".equals(appointmentType)){
            if(!appointmentSpecial.startsWith("Pet Id:")){
            special = "Pet Id: " + appointmentSpecial;
            }
        } else if("Car Checkup".equals(appointmentType)) {
            if (!appointmentSpecial.startsWith("Car Id:")) {
                special = "Car Id: " + appointmentSpecial;
            }
        }
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Contact_ID = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Special = ? WHERE Appointment_ID = ?";
                    //"UPDATE appointments SET Title = ?, Description = ?, Location = ?, Contact_ID = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Special = " + "CASE WHEN Type = 'Veterinary' THEN CONCAT('Pet Id: ', ?) " + "WHEN Type = 'Car Checkup' THEN CONCAT('Car Id: ', ?) " + "ELSE ? END " + "WHERE Appointment_ID = ?";//    CAST(? AS CHAR) WHERE Appointment_ID = ?";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            preparedstatement.setString(1, appointmentTitle);
            preparedstatement.setString(2, appointmentDescription);
            preparedstatement.setString(3, appointmentLocation);
            preparedstatement.setInt(4, appointmentContact);
            preparedstatement.setString(5, appointmentType);
            preparedstatement.setTimestamp(6, Timestamp.valueOf(appointmentStart));
            preparedstatement.setTimestamp(7, Timestamp.valueOf(appointmentEnd));
            preparedstatement.setInt(8, appointmentCustomerID);
            preparedstatement.setInt(9, appointmentUserID);
            preparedstatement.setString(10, special);
//            preparedstatement.setString(11, special);
//            preparedstatement.setString(12, special);
            preparedstatement.setInt(11, appointmentID);
            preparedstatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * used to retrieve appointments from the database ordered by week in ascending order
     * @return allAppointments an observable list of appointments ordered by week
     * */
    public static ObservableList<Appointments> getAppointmentsWeek() {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments ORDER BY (start)asc";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultset = preparedStatement.executeQuery();
            while(resultset.next()){
                int appointmentID = resultset.getInt("Appointment_ID");
                String appointmentTitle = resultset.getString("Title");
                String appointmentDescription = resultset.getString("Description");
                String appointmentLocation = resultset.getString("Location");
                int appointmentContact = resultset.getInt("Contact_ID");
                String appointmentType = resultset.getString("Type");
                Timestamp appointmentStart = resultset.getTimestamp("Start");
                Timestamp appointmentEnd = resultset.getTimestamp("End");
                int appointmentCustomerID = resultset.getInt("Customer_ID");
                int appointmentUserID = resultset.getInt("User_ID");
                String appointmentSpecial = resultset.getString("Special");

                Appointments appointments;
                if("Veterinary".equals(appointmentType)){
                    appointments = new VeterinaryAppointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContact, appointmentType, appointmentStart.toLocalDateTime(), appointmentEnd.toLocalDateTime(), appointmentCustomerID, appointmentUserID, appointmentSpecial);
                } else if("Car Checkup".equals(appointmentType)){
                    appointments = new CarCheckupAppointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContact, appointmentType, appointmentStart.toLocalDateTime(), appointmentEnd.toLocalDateTime(), appointmentCustomerID, appointmentUserID, appointmentSpecial);
                } else {
                    appointments = new NormalAppointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContact, appointmentType, appointmentStart.toLocalDateTime(), appointmentEnd.toLocalDateTime(), appointmentCustomerID, appointmentUserID, appointmentSpecial);
                }
                allAppointments.add(appointments);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allAppointments;

    }

}
