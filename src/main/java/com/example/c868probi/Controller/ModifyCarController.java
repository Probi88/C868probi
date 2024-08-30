package com.example.c868probi.Controller;

import com.example.c868probi.DAO.CarDAO;
import com.example.c868probi.DAO.CustomerDAO;
import com.example.c868probi.DAO.PetsDAO;
import com.example.c868probi.Model.Cars;
import com.example.c868probi.Model.Customers;
import com.example.c868probi.Model.Pets;
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
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyCarController implements Initializable {

    @FXML
    private TextField modifyCarAddressField;

    @FXML
    private Button modifyCarCancelButton;

    @FXML
    private ComboBox<Customers> modifyCarCustomerIDField;

    @FXML
    private TextField modifyCarIDField;

    @FXML
    private TextField modifyCarLicenseField;

    @FXML
    private TextField modifyCarMakeField;

    @FXML
    private TextField modifyCarModelField;

    @FXML
    private TextField modifyCarPhoneNumberField;

    @FXML
    private Button modifyCarSaveButton;

    @FXML
    private ComboBox<Integer> modifyCarYearIDField;

    @FXML
    void modifyCarCancelAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/CarForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Car Form");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void modifyCarSaveAction(ActionEvent event) throws IOException {
        try {
            int customerId = modifyCarCustomerIDField.getValue().getCustomerID();
            int carId = Integer.parseInt(modifyCarIDField.getText());
            String license = modifyCarLicenseField.getText();
            String address = modifyCarAddressField.getText();
            String phoneNumber = modifyCarPhoneNumberField.getText();
            String model = modifyCarModelField.getText();
            String make = modifyCarMakeField.getText();
            int carYear = modifyCarYearIDField.getValue();

            if(modifyCarCustomerIDField.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a customer name.");
                alert.showAndWait();
                return;
            } else
            if(license.isEmpty()) {
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
            } else if(model.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a phone number.");
                alert.showAndWait();
                return;
            }if (make.isEmpty()){ //== null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a country.");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("do you wish to modify this Pet?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                CarDAO.modifyCar(carId, customerId, license, address, phoneNumber, model, make, carYear);
            } else {
                return;
            }
        } catch (Exception e) {
            System.out.println("done messed up");
            throw new RuntimeException(e);
        }
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/CarForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Car Form");
        window.setScene(scene);
        window.show();
    }

    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            modifyCarCustomerIDField.setItems(CustomerDAO.getAllCustomers());
            int currentYear = LocalDate.now().getYear();
            for(int carYear = 1965; carYear <= currentYear; carYear++){
                modifyCarYearIDField.getItems().add(carYear);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setData(Cars selectedCars) throws SQLException {
        for (Customers customers : CustomerDAO.getAllCustomers()){
            if(customers.getCustomerID() == selectedCars.getCustomerID()){
                modifyCarCustomerIDField.setValue(customers);
            }
        }
        for(Integer year : modifyCarYearIDField.getItems()){
            if(year.equals(selectedCars.getCarYear())) {
                modifyCarYearIDField.setValue(year);
            }
        }
        modifyCarIDField.setText(String.valueOf(selectedCars.getCarID()));
        modifyCarLicenseField.setText(selectedCars.getCarLicensePlate());
        modifyCarAddressField.setText(selectedCars.getCustomerAddress());
        modifyCarPhoneNumberField.setText(selectedCars.getCustomerPhoneNumber());
        modifyCarModelField.setText(selectedCars.getCarModel());
        modifyCarMakeField.setText(selectedCars.getCarMake());

    }

}


