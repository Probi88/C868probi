package com.example.c868probi.DAO;

import com.example.c868probi.Helper.JDBC;
import com.example.c868probi.Model.Pets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PetsDAO {

    public static ObservableList<Pets> getAllPets() throws SQLException {
        ObservableList<Pets> allPets = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM pets ORDER BY Pet_ID";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);

            ResultSet resultset = preparedstatement.executeQuery();
            while(resultset.next()){
                int petId = resultset.getInt("Pet_ID");
                int customerID = resultset.getInt("Customer_ID");
                String petsName = resultset.getString("Pets_Name");
                String address = resultset.getString("Address");
                String phoneNumber = resultset.getString("Phone");
                String petSpecies = resultset.getString("Pet_Species");
                String petBreed = resultset.getString("Pet_Breed");

                Pets pets = new Pets(petId, customerID, petsName, address, phoneNumber, petSpecies, petBreed);
                allPets.add(pets);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allPets;
    }

    public static ObservableList<Pets> getAllPetsSelection() throws SQLException {
        ObservableList<Pets> selectAllPets = FXCollections.observableArrayList();

        try{
            String sql = "Select Pet_ID, Customer_ID, Pets_Name, Concat(Pet_Species, ' - ', Pet_Breed) AS petTypeBreed From pets ;";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);

            ResultSet resultset = preparedstatement.executeQuery();
            while(resultset.next()){
                int petId = resultset.getInt("Pet_ID");
                int customerID = resultset.getInt("Customer_ID");
                String petsName = resultset.getString("Pets_Name");
                String petTypeBreed = resultset.getString("petTypeBreed");
                Pets customers = new Pets(petId, customerID, petsName, petTypeBreed);
                selectAllPets.add(customers);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectAllPets;
    }

    /**
     * adds a new customer to the SQL database
     * @param customerID
     * @param customerAddress
     * @param customerPhoneNumber
     * */
    public static void addPet(int petID, int customerID, String petsName, String customerAddress, String customerPhoneNumber, String petSpecies, String petBreed){
        try {
            String sql = "INSERT INTO pets(Pet_ID, Customer_ID, Pets_Name, Address, Phone, Pet_Species, Pet_Breed) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            preparedstatement.setInt(1, petID);
            preparedstatement.setInt(2, customerID);
            preparedstatement.setString(3, petsName);
            preparedstatement.setString(4, customerAddress);
            preparedstatement.setString(5, customerPhoneNumber);
            preparedstatement.setString(6, petSpecies);
            preparedstatement.setString(7, petBreed);
            int rowsAffected = preparedstatement.executeUpdate();
            if(rowsAffected > 0) {
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Failed to insert a new pet record");
                alert.showAndWait();
                throw new RuntimeException("Failed to insert a new pet record");
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
    public static int nextPetId() throws SQLException {
        String sql = "SELECT max(Pet_ID) FROM pets";
        PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedstatement.executeQuery(sql);

        if(resultSet.next()){
            int newPetId = resultSet.getInt(1);
            return newPetId + 1;
        } else {
            return 1;
        }

    }

    /**
     * deletes a customer from the SQL database if it matches a specific customer id
     * @return false if item not deleted
     * */
    public static boolean deletePet(int PetID){
        try{
            String sql = "DELETE FROM pets WHERE Pet_ID = ?";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            preparedstatement.setInt(1,PetID);
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
    public static void modifyPet(int petID, int customerID, String petsName, String customerAddress, String customerPhoneNumber, String petSpecies, String petBreed){
        try{
            String sql = "UPDATE pets SET Customer_ID = ?, Pets_Name = ?, Address = ?, Phone = ?, Pet_Species = ?, Pet_Breed = ? WHERE Pet_ID = ?";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            preparedstatement.setInt(1, customerID);
            preparedstatement.setString(2, petsName);
            preparedstatement.setString(3, customerAddress);
            preparedstatement.setString(4, customerPhoneNumber);
            preparedstatement.setString(5, petSpecies);
            preparedstatement.setString(6, petBreed);
            preparedstatement.setInt(7, petID);
            preparedstatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
