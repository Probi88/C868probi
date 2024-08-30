package com.example.c868probi.Controller;

import com.example.c868probi.DAO.AppointmentsDAO;
import com.example.c868probi.DAO.CustomerDAO;
import com.example.c868probi.DAO.PetsDAO;
import com.example.c868probi.Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PetsController implements Initializable {

    @FXML
    private TextField customerIdField;

    @FXML
    private ComboBox<Customers> customerIdComboBox;

    @FXML
    private Button petAddButton;

    @FXML
    private TableColumn<Pets, String> petAddressColumn;

    @FXML
    private TextField petAddressField;

    @FXML
    private TableColumn<Pets, String> petBreedColumn;

    @FXML
    private TextField petBreedField;

    @FXML
    private TableColumn<Customers, Integer> petCustomerIdColumn;

    @FXML
    private Button petExitButton;

    @FXML
    private TextField petIdField;

    @FXML
    private Button petModifyButton;

    @FXML
    private TableColumn<Pets, String> petNameColumn;

    @FXML
    private TextField petNameField;

    @FXML
    private TableColumn<Pets, Integer> petPetID;

    @FXML
    private TableColumn<Pets, String> petPhoneNumberColumn;

    @FXML
    private TextField petPhoneNumberField;

    @FXML
    private Button petRemoveButton;

    @FXML
    private TextField petSearchField;

    @FXML
    private TableColumn<Pets, String> petSpeciesColumn;

    @FXML
    private TextField petSpeciesField;

    @FXML
    private TableView<Pets> petTable;

    public static Pets modifyPet = null;

    ObservableList<Pets> petsList = FXCollections.observableArrayList();

    private FilteredList<Pets> filteredPets;

    @FXML
    void petAddAction(ActionEvent event) {
        try {
            int customerId = customerIdComboBox.getValue().getCustomerID();
            int petId = PetsDAO.nextPetId();
            String name = petNameField.getText();
            String address = petAddressField.getText();
            String phoneNumber = petPhoneNumberField.getText();
            String species = petSpeciesField.getText();
            String breed = petBreedField.getText();

            if(customerIdComboBox.getValue() == null) {
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
            }if (breed.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a country.");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("do you wish to enter this pet?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                PetsDAO.addPet(petId, customerId, name, address, phoneNumber, species, breed);
                petTable.setItems(PetsDAO.getAllPets());
                petTable.refresh();
                customerIdComboBox.setValue(null);
                petNameField.setText("");
                petAddressField.setText("");
                petPhoneNumberField.setText("");
                petSpeciesField.setText("");
                petBreedField.setText("");
            } else {
                alert.close();
            }
        } catch (Exception e) {
            System.out.println("done messed up");
            throw new RuntimeException(e);

        }
    }

    @FXML
    void petExitAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/MainForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Main Menu");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void petModifyAction(ActionEvent event) throws IOException, SQLException {
        Pets selectPet = petTable.getSelectionModel().getSelectedItem();
        if(selectPet == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must select a pet to continue");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        modifyPet = petTable.getSelectionModel().getSelectedItem();
        loader.setLocation(getClass().getResource("/com/example/c868probi/ModifyPetForm.fxml"));
        Parent addparent = loader.load();
        ModifyPetController modifyController = loader.getController();
        modifyController.setData(modifyPet);
        Scene scene = new Scene(addparent);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Modify Pet");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void petRemoveAction(ActionEvent event) throws SQLException {
        Pets selectedPet = (Pets)petTable.getSelectionModel().getSelectedItem();
        if(selectedPet == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must select a pet to continue");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("do you wish to delete this pet? All associated appointments will also be deleted.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                PetsDAO.deletePet(selectedPet.getPetID());
                for(Appointments appointments : AppointmentsDAO.getAllAppointments()){
                    String appointmentSpecial = appointments.getAppointmentSpecial();
                    if(appointmentSpecial != null && appointmentSpecial.contains(String.valueOf(selectedPet.getPetID()))){
                        AppointmentsDAO.deleteAppointment(appointments.getAppointmentID());
                    }
                }
                Alert Ialert = new Alert(Alert.AlertType.INFORMATION);
                Ialert.setContentText("Pet Id: " + selectedPet.getPetID() +  " and all associated appointments have been deleted." );
                Ialert.showAndWait();
                petTable.setItems(PetsDAO.getAllPets());
                petTable.refresh();
            } else {
                Alert Ealert = new Alert(Alert.AlertType.ERROR);
                Ealert.setContentText("all appointments with related pets must be deleted before a pet can be removed from the database ");
                Ealert.showAndWait();
            }
        }
    }

    @FXML
    void petSearchAction(KeyEvent event) {
    }

    private void refreshPetTable() throws SQLException {
        petsList.setAll(PetsDAO.getAllPets());
        petTable.setItems(petsList);
        petTable.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.customerIdComboBox.setItems(CustomerDAO.getAllCustomers());
            refreshPetTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        petPetID.setCellValueFactory(new PropertyValueFactory<>("petID"));
        this.petCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        petNameColumn.setCellValueFactory(new PropertyValueFactory<>("petName"));
        petAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        petPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        petSpeciesColumn.setCellValueFactory(new PropertyValueFactory<>("petSpecies"));
        petBreedColumn.setCellValueFactory(new PropertyValueFactory<>("petBreed"));

        filteredPets = new FilteredList<>(petsList, new Predicate<Pets>() {
            @Override
            public boolean test(Pets pets) {
                return true;
            }
        });
        petTable.setItems(filteredPets);
        petSearchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                filteredPets.setPredicate(new Predicate<Pets>() {
                    @Override
                    public boolean test(Pets pets) {
                        if(t1 == null || t1.isEmpty()){
                            return true;
                        }
                        String toLowerCase = t1.toLowerCase();
                        try{
                            int searchPetId = Integer.parseInt(toLowerCase);
                            if(String.valueOf(pets.getPetID()).contains(toLowerCase))/*searchPetId)*/{
                                return true;
                            }
                        } catch (NumberFormatException e) {
                        }

                        if(pets.getPetName().toLowerCase().contains(toLowerCase)){
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
    }

}
