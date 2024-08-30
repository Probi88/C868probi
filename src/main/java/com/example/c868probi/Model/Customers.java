package com.example.c868probi.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;

/**
 * model class used for creation of customers in scheduling application
 * */
public class Customers {

    private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    private int customerID;

    private String customerName;

    private String customerAddress;

    private String customerPostalCode;

    private String customerPhoneNumber;

    private String customerDivision;

    private String countryData;

    /**
     * constructor for new customers
     * @param customerID
     * @param customerName
     * @param customerAddress
     * @param customerPostalCode
     * @param customerPhoneNumber
     * @param customerDivision the first level divisions of a selected country
     * @param countryData
     * */
    public Customers(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, String customerDivision, String countryData) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerDivision = customerDivision;
        this.countryData = countryData;

    }

    public Customers(int customerID, String customerAddress, String customerPhoneNumber) {
        this.customerID = customerID;
        this.customerAddress = customerAddress;
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public Customers(int customerID) {
        this.customerID = customerID;
    }

    /**
     * gets customer id
     * @return customerID
     * */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * sets customer id
     * @param customerID
     * */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * gets customer name
     * @return customerName
     * */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * sets customer name
     * @param customerName
     * */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * gets customer address
     * @return customerAddress
     * */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * sets customer address
     * @param customerAddress
     * */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * gets customer postal code
     * @return customerPostalCode
     * */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * sets customer postal code
     * @param customerPostalCode
     * */
    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    /**
     * gets customer phone number
     * @return customerPhoneNumber
     * */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    /**
     * sets customer phone number
     * @param customerPhoneNumber
     * */
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    /**
     * gets customer first level division
     * @return customerDivision
     * */
    public String getCustomerDivision() {
        return customerDivision;
    }

    /**
     * sets customer first level division
     * @param customerDivision
     * */
    public void setCustomerDivision(String customerDivision) {
        this.customerDivision = customerDivision;
    }

    /**
     * gets customer country data
     * @return countryData
     * */
    public String getCountryData() {
        return countryData;
    }

    /**
     * sets customer country data
     * @param countryData
     * */
    public void setCountryData(String countryData) {
        this.countryData = countryData;
    }

    public static ObservableList<Customers> getAllCustomers() {
        return allCustomers;
    }

    public static void setAllCustomers(ObservableList<Customers> allCustomers) {
        Customers.allCustomers = allCustomers;
    }

    /**
     * when viewing a customer only the name would be displayed
     * @return customerName
     * */
    @Override
    public String toString() {
        //return "Customers{" +
          //      "customerID=" + customerID +
            //    '}';
        return "Id: " + customerID + " " + customerName;
    }



}