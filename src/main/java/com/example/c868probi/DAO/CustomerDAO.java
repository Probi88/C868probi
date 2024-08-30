package com.example.c868probi.DAO;

import com.example.c868probi.Helper.JDBC;
import com.example.c868probi.Model.Country;
import com.example.c868probi.Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * this class handles database SQL query's for customers
 * */
public class CustomerDAO {

    /**
     * retrieves all customers from the database
     * @return allCustomers
     * */
    public static ObservableList<Customers> getAllCustomers() throws SQLException {
        ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

        try{
            String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, first_level_divisions.division, countries.country from customers inner join first_level_divisions on first_level_divisions.Division_ID = customers.Division_ID inner join countries on countries.country_id = first_level_divisions.country_id ORDER BY customers.Customer_ID";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);

            ResultSet resultset = preparedstatement.executeQuery();
            while(resultset.next()){
                int customerId = resultset.getInt("Customer_ID");
                String customerName = resultset.getString("Customer_Name");
                String address = resultset.getString("Address");
                String postalCode = resultset.getString("Postal_Code");
                String phoneNumber = resultset.getString("Phone");
                String customerDivision = resultset.getString("Division");
                String countryData = resultset.getString("Country");

                Customers customers = new Customers(customerId, customerName, address, postalCode, phoneNumber, customerDivision, countryData);
                allCustomers.add(customers);
            }
            //return null;//fixme remove
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allCustomers;
    }

    /**
     * adds a new customer to the SQL database
     * @param customerID
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhoneNumber
     * @param customerDivision
     * @param country
     * */
    public static void addCustomer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, int customerDivision, String country){

        try {
            int newCustomerId = nextCustomerId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String sql = "INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            preparedstatement.setInt(1, customerID);
            preparedstatement.setString(2, customerName);
            preparedstatement.setString(3, customerAddress);
            preparedstatement.setString(4, customerPostalCode);
            preparedstatement.setString(5, customerPhoneNumber);
            preparedstatement.setInt(6, customerDivision);
            int rowsAffected = preparedstatement.executeUpdate();
            if(rowsAffected > 0) {
                //ResultSet
            } else {
                //
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Failed to insert a new customer record");
                alert.showAndWait();
                throw new RuntimeException("Failed to insert a new customer record");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * used to find the maximum customer id from the database
     * @return next available customer id
     * */
    public static int nextCustomerId() throws SQLException {
        String sql = "SELECT max(Customer_ID) FROM customers";
        PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedstatement.executeQuery(sql);

        if(resultSet.next()){
            int newCustomerId = resultSet.getInt(1);
            return newCustomerId + 1;
        } else {
            return 1;
        }

    }

    /**
     * deletes a customer from the SQL database if it matches a specific customer id
     * @param CustomerID
     * @return false if item not deleted
     * */
    public static boolean deleteCustomer(int CustomerID){
        try{
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            preparedstatement.setInt(1,CustomerID);
            int stmt = preparedstatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * modifies the customers details in the database based on a specific customer id
     * @param customerID
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhoneNumber
     * @param customerDivision
     * @param country
     * */
    public static void modifyCustomer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, int customerDivision, String country){
        try{
            String sql = "UPDATE customers SET  Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            preparedstatement.setString(1, customerName);
            preparedstatement.setString(2, customerAddress);
            preparedstatement.setString(3, customerPostalCode);
            preparedstatement.setString(4, customerPhoneNumber);
            preparedstatement.setInt(5, customerDivision);
            preparedstatement.setInt(6, customerID);
            preparedstatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<Integer> getAllCustomerIds(){
        ObservableList<Integer> customerIDs = FXCollections.observableArrayList();

        try{
            String sql = "SELECT customer_id FROM customers";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultset = preparedstatement.executeQuery();

            while(resultset.next()){
                customerIDs.add(resultset.getInt("customer_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerIDs;
    }

    /**
     * retrieves country names from database
     * @return CountryNames observable list of country names
     * */
    public static ObservableList<Country> getCountryName() throws SQLException {
        ObservableList<Country> CountryNames = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Country from countries";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultset = preparedstatement.executeQuery();

            while(resultset.next()){
                String countryName = resultset.getString("Country");
                Country country = new Country(countryName);
                CountryNames.add(country);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return CountryNames;
    }

    /**
     * retrieves country ids from database
     * @return CountryID observable list of country ids
     * */
    public static ObservableList<Country> getCountryID() throws SQLException {
        ObservableList<Country> CountryID = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Country from countries";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultset = preparedstatement.executeQuery();

            while(resultset.next()){
                String countryName = resultset.getString("Country");
                Country country = new Country(countryName);
                CountryID.add(country);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return CountryID;
    }

    /**
     * retrieves all countries from the database
     * @return allCountries observables list of all countries
     * */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from countries";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultset = preparedstatement.executeQuery();

            while(resultset.next()){
                String countryName = resultset.getString("Country");
                int countryID = resultset.getInt("Country_ID");
                Country country = new Country(countryName, countryID);
                allCountries.add(country);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allCountries;
    }

}
