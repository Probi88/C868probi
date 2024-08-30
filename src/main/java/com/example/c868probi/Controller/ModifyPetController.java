package com.example.c868probi.Controller;

import com.example.c868probi.DAO.CustomerDAO;
import com.example.c868probi.DAO.DivisionDAO;
import com.example.c868probi.DAO.PetsDAO;
import com.example.c868probi.Model.Country;
import com.example.c868probi.Model.Customers;
import com.example.c868probi.Model.FirstLevelDivisions;
import com.example.c868probi.Model.Pets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.c868probi.Controller.ModifyCustomerController.customers;

public class ModifyPetController implements Initializable {

        @FXML
        private TextField modifyPetAddressField;

        @FXML
        private TextField modifyPetBreedField;

        @FXML
        private Button modifyPetCancelButton;

        @FXML
        private ComboBox<Customers> modifyPetCustomerIDField;

        @FXML
        private TextField modifyPetIDField;

        @FXML
        private TextField modifyPetNameField;

        @FXML
        private TextField modifyPetPhoneNumberField;

        @FXML
        private Button modifyPetSaveButton;

        @FXML
        private TextField modifyPetSpeciesField;

        @FXML
        void modifyPetCancelAction(ActionEvent event) throws IOException {
                Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/PetForm.fxml"));
                Scene scene = new Scene(addparent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("Pet Form");
                window.setScene(scene);
                window.show();
        }

        @FXML
        void modifyPetSaveAction(ActionEvent event) throws IOException {
        try {
            int customerId = modifyPetCustomerIDField.getValue().getCustomerID();
            int petId = Integer.parseInt(modifyPetIDField.getText());
            String name = modifyPetNameField.getText();
            String address = modifyPetAddressField.getText();
            String phoneNumber = modifyPetPhoneNumberField.getText();
            String species = modifyPetSpeciesField.getText();
            String breed = modifyPetBreedField.getText();

            if(modifyPetCustomerIDField.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a customer name.");
                alert.showAndWait();
                return;
            } else
            if(name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a customer name.");
                alert.showAndWait();
                return;
            } else if(address.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter an address.");
                alert.showAndWait();
                return;
            } else if(phoneNumber.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a postal code.");
                alert.showAndWait();
                return;
            } else if(species.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a phone number.");
                alert.showAndWait();
                return;
            }if (breed.isEmpty()){ //== null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a country.");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("do you wish to modify this Pet?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                PetsDAO.modifyPet(petId, customerId, name, address, phoneNumber, species, breed);
            } else {
                    return;
            }
        } catch (Exception e) {
            System.out.println("done messed up");
            throw new RuntimeException(e);

        }
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/PetForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Pet Form");
        window.setScene(scene);
        window.show();
    }

        //@Override
        public void initialize(URL url, ResourceBundle resourceBundle){

            try {
                modifyPetCustomerIDField.setItems(CustomerDAO.getAllCustomers());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public void setData(Pets selectedPets) throws SQLException {
            for (Customers customers:CustomerDAO.getAllCustomers()){
                if(customers.getCustomerID() == selectedPets.getCustomerID()){
                    modifyPetCustomerIDField.setValue(customers);
                }
            }
                modifyPetIDField.setText(String.valueOf(selectedPets.getPetID()));
                modifyPetNameField.setText(selectedPets.getPetName());
                modifyPetAddressField.setText(selectedPets.getCustomerAddress());
                modifyPetPhoneNumberField.setText(selectedPets.getCustomerPhoneNumber());
                modifyPetSpeciesField.setText(selectedPets.getPetSpecies());
                modifyPetBreedField.setText(selectedPets.getPetBreed());
        }

}

