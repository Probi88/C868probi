package com.example.c868probi.DAO;

import com.example.c868probi.Helper.JDBC;
import com.example.c868probi.Model.ReportType;
import com.example.c868probi.Model.Reports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * this class handles database SQL query's for reports
 * */
public class ReportsDAO {

    /**
     * retrieves from the database months as well as counting how many appointments are there in each month as total
     * @return monthTotalReport an observablelist of the report
     * */
    public static ObservableList<Reports> getMonthTotalReport(){
        ObservableList<Reports> monthTotalReport = FXCollections.observableArrayList();

        try {
            String sql = "SELECT monthname(start) AS month_name, count(*) AS total From appointments\n" + "GROUP BY month_name;";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultset = preparedStatement.executeQuery();
            while(resultset.next()){
                Integer total = resultset.getInt("total");
                String month = resultset.getString("month_name");
                Reports reports = new Reports(total, month); //fixme here
                monthTotalReport.add(reports);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return monthTotalReport;
    }

    /**
     * retrieves from the database types of appointments as well as counting how many appointments are there for each appointment type as total
     * @return typeTotalReport an observablelist of the report
     * */
    public static ObservableList<ReportType> getTypeTotalReport(){
        ObservableList<ReportType> typeTotalReport = FXCollections.observableArrayList();

        try {
            String sql = "SELECT type, count(*) AS total From appointments\n" + "GROUP BY type;";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultset = preparedStatement.executeQuery();
            while(resultset.next()) {
                Integer total = resultset.getInt("total");
                String monthType = resultset.getString("type");
                ReportType reportType = new ReportType(total, monthType);
                typeTotalReport.add(reportType);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
            return typeTotalReport;
    }

    /**
     * retrieves from the database appointment information like Appointment id, Title, Description, Type, Start, End, and Customer id, based on a specific contact
     * @param contactId used to find which appointment information will be sent back and displayed based on which contact id is selected
     * @return allAppointments an observablelist of the report
     * */
    public static ObservableList<Reports> getAllAppointmentsContact(int contactId) {
        ObservableList<Reports> allAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Type, Start, End, Customer_ID FROM appointments WHERE Contact_ID = ?";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            preparedStatement.setInt(1, contactId);
            ResultSet resultset = preparedStatement.executeQuery();
            while(resultset.next()){
                int contactAppointmentId = resultset.getInt("Appointment_ID");
                String contactTitle = resultset.getString("Title");
                String contactDescription = resultset.getString("Description");
                String contactType = resultset.getString("Type");
                Timestamp contactStart = resultset.getTimestamp("Start");
                Timestamp contactEnd = resultset.getTimestamp("End");
                int contactCustomerId = resultset.getInt("Customer_ID");

                Reports reports = new Reports(contactAppointmentId, contactTitle, contactDescription, contactType, contactStart.toLocalDateTime(), contactEnd.toLocalDateTime(), contactCustomerId);
                allAppointments.add(reports);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allAppointments;

    }

    /**
     * retrieves from the database the first level division, and a count of total customers per first level division
     * @return divisionTotalReport an observablelist of the report
     * */
    public static ObservableList<Reports> getDivisionTotalReport(){
        ObservableList<Reports> divisionTotalReport = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Division, count(Customer_ID) as total_customers From customers left Join first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID\n" + "GROUP BY Customer_ID;";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultset = preparedStatement.executeQuery();
            while(resultset.next()) {
                String Division = resultset.getString("Division");
                Integer totalCustomers = resultset.getInt("total_customers");
                Reports reports = new Reports(Division, totalCustomers);
                divisionTotalReport.add(reports);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return divisionTotalReport;
    }
}
