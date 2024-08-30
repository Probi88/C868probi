package com.example.c868probi.DAO;

import com.example.c868probi.Helper.JDBC;
import com.example.c868probi.Model.Cars;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarDAO {

    public static ObservableList<Cars> getAllCars() throws SQLException {
        ObservableList<Cars> allCars = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM cars ORDER BY Car_ID";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);

            ResultSet resultset = preparedstatement.executeQuery();
            while(resultset.next()){
                int carId = resultset.getInt("Car_ID");
                int customerID = resultset.getInt("Customer_ID");
                String carLicensePlate = resultset.getString("Car_License_Plate");
                String address = resultset.getString("Address");
                String phoneNumber = resultset.getString("Phone");
                String carModel = resultset.getString("Car_Model");
                String carMake = resultset.getString("Car_Make");
                int carYear = resultset.getInt("Car_Year");

                Cars cars = new Cars(carId, customerID, carLicensePlate, address, phoneNumber, carModel, carMake, carYear );
                allCars.add(cars);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allCars;
    }

    public static ObservableList<Cars> getAllCarsSelection() throws SQLException {
        ObservableList<Cars> selectAllCars = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Car_ID, Customer_ID, Car_License_Plate, Car_Model, Car_Make from cars";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);

            ResultSet resultset = preparedstatement.executeQuery();
            while(resultset.next()){
                int carId = resultset.getInt("Car_ID");
                int customerID = resultset.getInt("Customer_ID");
                String carsLicensePlate = resultset.getString("Car_License_Plate");
                String carModel = resultset.getString("Car_Model");
                String carMake = resultset.getString("Car_Make");
                String carTypeBreed = carModel + " - " + carMake;

                Cars cars = new Cars(carId, customerID, carsLicensePlate, carTypeBreed);
                selectAllCars.add(cars);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectAllCars;
    }

    /**
     * adds a new customer to the SQL database
     * @param customerID
     * @param customerAddress
     * @param customerPhoneNumber
     * */
    public static void addCar(int carID, int customerID, String carLicensePlate, String customerAddress, String customerPhoneNumber, String carModel, String carMake, int carYear){
        try {
            String sql = "INSERT INTO cars(Car_ID, Customer_ID, Car_License_Plate, Address, Phone, Car_Model, Car_Make, Car_Year) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            preparedstatement.setInt(1, carID);
            preparedstatement.setInt(2, customerID);
            preparedstatement.setString(3, carLicensePlate);
            preparedstatement.setString(4, customerAddress);
            preparedstatement.setString(5, customerPhoneNumber);
            preparedstatement.setString(6, carModel);
            preparedstatement.setString(7, carMake);
            preparedstatement.setInt(8, carYear);
            int rowsAffected = preparedstatement.executeUpdate();
            if(rowsAffected > 0) {
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Failed to insert a new car record");
                alert.showAndWait();
                throw new RuntimeException("Failed to insert a new car record");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * used to find the maximum customer id from the database
     *
     * @return next available customer id
     */
    public static int nextCarId() throws SQLException {
        String sql = "SELECT max(Car_ID) FROM cars";
        PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedstatement.executeQuery(sql);

        if(resultSet.next()){
            int newCarId = resultSet.getInt(1);
            return newCarId + 1;
        } else {
            return 1;
        }

    }

    /**
     * deletes a customer from the SQL database if it matches a specific customer id
     * @return false if item not deleted
     * */
    public static boolean deleteCar(int CarID){
        try{
            String sql = "DELETE FROM cars WHERE Car_ID = ?";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            preparedstatement.setInt(1, CarID);
            int stmt = preparedstatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * modifies the customers details in the database based on a specific customer id
     * @param customerID
     * @param customerAddress
     * @param customerPhoneNumber
     * */
    public static void modifyCar(int carID, int customerID, String carLicensePlate, String customerAddress, String customerPhoneNumber, String carModel, String carMake, int carYear){
        try{
            String sql = "UPDATE cars SET Customer_ID = ?, Car_License_Plate = ?, Address = ?, Phone = ?, Car_Model = ?, Car_Make = ?, Car_Year = ? WHERE Car_ID = ?";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            preparedstatement.setInt(1, customerID);
            preparedstatement.setString(2, carLicensePlate);
            preparedstatement.setString(3, customerAddress);
            preparedstatement.setString(4, customerPhoneNumber);
            preparedstatement.setString(5, carModel);
            preparedstatement.setString(6, carMake);
            preparedstatement.setInt(7, carYear);
            preparedstatement.setInt(8, carID);

            preparedstatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
