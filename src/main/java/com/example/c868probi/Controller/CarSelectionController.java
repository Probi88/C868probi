package com.example.c868probi.Controller;

import com.example.c868probi.DAO.CarDAO;
import com.example.c868probi.DAO.PetsDAO;
import com.example.c868probi.Model.Cars;
import com.example.c868probi.Model.Pets;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CarSelectionController implements Initializable {

    @FXML
    private TableColumn<Cars, String> carSelectionIDColumn;

    @FXML
    private TableColumn<Cars, String> carSelectionLicenseColumn;

    @FXML
    private TableColumn<Cars, String> carSelectionMakeModelColumn;

    @FXML
    private Button carSelectionCancelButton;

    @FXML
    private TableColumn<Cars, Integer> carSelectionCustomerIDColumn;

    @FXML
    private Button carSelectionSelectButton;

    @FXML
    private TableView<Cars> carSelectionTable;

    private Cars selectedCar;

    public Cars getSelectedCar() {
        return selectedCar;
    }

    @FXML
    void carSelectionCancelAction(ActionEvent event) {
        Stage stage = (Stage) carSelectionCancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void carSelectionSelectAction(ActionEvent event) {
        selectedCar = carSelectionTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            Stage stage = (Stage) carSelectionSelectButton.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a car.");
            alert.showAndWait();
            return;
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            carSelectionTable.setItems(CarDAO.getAllCarsSelection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        carSelectionIDColumn.setCellValueFactory(new PropertyValueFactory<>("carID"));
        carSelectionLicenseColumn.setCellValueFactory(new PropertyValueFactory<>("carLicensePlate"));
        carSelectionMakeModelColumn.setCellValueFactory(new PropertyValueFactory<>("carMakeModel"));
        carSelectionCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

}
