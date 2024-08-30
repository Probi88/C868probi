package com.example.c868probi.Controller;

import com.example.c868probi.DAO.PetsDAO;
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

public class PetSelectionController implements Initializable {

    @FXML
    private TableColumn<Pets, String> petSelectionBreedTypeColumn;

    @FXML
    private Button petSelectionCancelButton;

    @FXML
    private TableColumn<Pets, Integer> petSelectionCustomerIDColumn;

    @FXML
    private TableColumn<Pets, Integer> petSelectionIDColumn;

    @FXML
    private TableColumn<Pets, String> petSelectionNameColumn;

    @FXML
    private Button petSelectionSelectButton;

    @FXML
    private TableView<Pets> petSelectionTable;

    private Pets selectedPet;

    public Pets getSelectedPet() {
        return selectedPet;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            petSelectionTable.setItems(PetsDAO.getAllPetsSelection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        petSelectionIDColumn.setCellValueFactory(new PropertyValueFactory<>("petID"));
        petSelectionNameColumn.setCellValueFactory(new PropertyValueFactory<>("petName"));
        petSelectionBreedTypeColumn.setCellValueFactory(new PropertyValueFactory<>("petTypeBreed"));
        petSelectionCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    @FXML
    void petSelectionCancelAction(ActionEvent event) {
        Stage stage = (Stage) petSelectionCancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void petSelectionSelectAction(ActionEvent event) {
        selectedPet = petSelectionTable.getSelectionModel().getSelectedItem();
        if (selectedPet != null) {
            Stage stage = (Stage) petSelectionSelectButton.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a pet.");
            alert.showAndWait();
            return;
        }
    }

}
