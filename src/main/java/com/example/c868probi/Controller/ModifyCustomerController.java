package com.example.c868probi.Controller;

import com.example.c868probi.DAO.CustomerDAO;
import com.example.c868probi.DAO.DivisionDAO;
import com.example.c868probi.Model.Country;
import com.example.c868probi.Model.Customers;
import com.example.c868probi.Model.FirstLevelDivisions;
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

/**
 * controller for modifying a customer
 * */
public class ModifyCustomerController implements Initializable {

    @FXML
    private Button customerModifyCancelButton;

    @FXML
    private Button customerModifySaveButton;

    @FXML
    private TextField modifyCustomerAddressField;

    @FXML
    private ComboBox<Country> modifyCustomerCountryDataCombo;

    @FXML
    private ComboBox<FirstLevelDivisions> modifyCustomerDivisionCodeCombo;

    @FXML
    private TextField modifyCustomerIDField;

    @FXML
    private TextField modifyCustomerNameField;

    @FXML
    private TextField modifyCustomerPhoneNumberField;

    @FXML
    private TextField modifyCustomerPostalCodeField;

    public static Customers customers = null;

    /**
     * switches pages to customer form when selecting the cancel button
     * @param event
     * */
    @FXML
    void modifyCustomerCancelAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/CustomerForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Customers Form");
        window.setScene(scene);
        window.show();
    }

    /**
     * checks for logic errors or empty text boxes/non-selected combo boxes, if no errors will save and modify selected customer details then return to customer form
     * @param event
     * */
    @FXML
    void modifyCustomerSaveAction(ActionEvent event) throws IOException {
        try {
            int customerId = Integer.parseInt(modifyCustomerIDField.getText());
            String name = modifyCustomerNameField.getText();
            String address = modifyCustomerAddressField.getText();
            String postalCode = modifyCustomerPostalCodeField.getText();
            String phoneNumber = modifyCustomerPhoneNumberField.getText();
            int customerDivision = modifyCustomerDivisionCodeCombo.getValue().getDivisionID();
            String country = String.valueOf(modifyCustomerCountryDataCombo.getValue());

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
            } else if(postalCode.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a postal code.");
                alert.showAndWait();
                return;
            } else if(phoneNumber.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a phone number.");
                alert.showAndWait();
                return;
            } else if (country == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a country.");
                alert.showAndWait();
                return;
            } else if (modifyCustomerDivisionCodeCombo.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a Division.");
                alert.showAndWait();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("do you wish to modify this customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                CustomerDAO.modifyCustomer(customerId, name, address, postalCode, phoneNumber, customerDivision, country);
            } else {
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/CustomerForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Customers Form");
        window.setScene(scene);
        window.show();
    }

    /**
     * used to populate first level division combo box based on what country is selected
     * @param event
     * */
    @FXML
    void modifyCustomerSetDivision(ActionEvent event) {
        Country country = modifyCustomerCountryDataCombo.getValue();
        System.out.println(country.getCountryName());
        try {
            modifyCustomerDivisionCodeCombo.setItems(DivisionDAO.countryGetDivision(country.getCountryName()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * initializes country combo box with country data
     * @param url
     * @param resourceBundle
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            this.modifyCustomerCountryDataCombo.setItems(CustomerDAO.getAllCountries());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * sets customer text fields and combo boxes based on the customer selected from the customer form
     * @param customers
     * */
    public void setData(Customers customers){
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        try {
            modifyCustomerIDField.setText(String.valueOf(customers.getCustomerID()));
            modifyCustomerNameField.setText(customers.getCustomerName());
            modifyCustomerAddressField.setText(customers.getCustomerAddress());
            modifyCustomerPostalCodeField.setText(customers.getCustomerPostalCode());
            modifyCustomerPhoneNumberField.setText(customers.getCustomerPhoneNumber());
            this.modifyCustomerCountryDataCombo.setItems(CustomerDAO.getAllCountries());
            for(Country country : CustomerDAO.getAllCountries()){
                if(country.getCountryName().equals(customers.getCountryData())){
                    modifyCustomerCountryDataCombo.setValue(country);
                }
            }
            Country selectCountry = modifyCustomerCountryDataCombo.getValue();
            if(selectCountry != null){
                modifyCustomerDivisionCodeCombo.setItems(DivisionDAO.countryGetDivision(selectCountry.getCountryName()));
                for (FirstLevelDivisions firstLevelDivisions : DivisionDAO.countryGetDivision(selectCountry.getCountryName())) {
                    if (firstLevelDivisions.getDivision().equals(customers.getCustomerDivision())) {
                        modifyCustomerDivisionCodeCombo.setValue(firstLevelDivisions);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}