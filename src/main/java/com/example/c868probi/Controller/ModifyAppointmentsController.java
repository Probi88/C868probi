package com.example.c868probi.Controller;

import com.example.c868probi.DAO.AppointmentsDAO;
import com.example.c868probi.DAO.ContactsDAO;
import com.example.c868probi.DAO.CustomerDAO;
import com.example.c868probi.DAO.UserDAO;
import com.example.c868probi.Model.*;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * controller for modifying appointments
 * */
public class ModifyAppointmentsController implements Initializable {

    @FXML
    private Button appointmentModifyCancelButton;

    @FXML
    private Button appointmentModifySaveButton;

    @FXML
    private Button modifyAppointmentSpecialButton;

    @FXML
    private Label modifyAppointmentSpecialLabel;

    @FXML
    private ComboBox<Contacts> modifyAppointmentContactComboBox;

    @FXML
    private ComboBox<Customers> modifyAppointmentCustomerIDComboBox;

    @FXML
    private ComboBox<String> modifyAppointmentTypeComboBox;

    @FXML
    private TextField modifyAppointmentDescriptionField;

    @FXML
    private DatePicker modifyAppointmentEndDatePicker;

    @FXML
    private ComboBox<LocalTime> modifyAppointmentEndTimeComboBox;

    @FXML
    private TextField modifyAppointmentIDField;

    @FXML
    private TextField modifyAppointmentLocationField;

    @FXML
    private DatePicker modifyAppointmentStartDatePicker;

    @FXML
    private ComboBox<LocalTime> modifyAppointmentStartTimeComboBox;

    @FXML
    private TextField modifyAppointmentTitleField;

    @FXML
    private TextField modifyAppointmentSpecialField;

    @FXML
    private ComboBox<Users> modifyAppointmentUserIDComboBox;

    public static Appointments selectedAppointments;

    @FXML
    void modifyAppointmentSpecialAction(ActionEvent event) {
        String appointmentType = modifyAppointmentTypeComboBox.getValue();
        if(appointmentType.equals("Veterinary")){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/c868probi/PetSelectionForm.fxml"));
                Parent root = fxmlLoader.load();
                PetSelectionController petSelectionController = fxmlLoader.getController();
                Stage stage = new Stage();
                stage.setTitle("Select a Pet");
                stage.setScene(new Scene(root));
                stage.showAndWait();
                String selectedPetId = String.valueOf(petSelectionController.getSelectedPet().getPetID());
                modifyAppointmentSpecialField.setText(selectedPetId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if(appointmentType.equals("Car Checkup")){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/c868probi/CarSelectionForm.fxml"));
                Parent root = fxmlLoader.load();
                CarSelectionController carSelectionController = fxmlLoader.getController();
                Stage stage = new Stage();
                stage.setTitle("Select a Car");
                stage.setScene(new Scene(root));
                stage.showAndWait();
                String selectedCarId = String.valueOf(carSelectionController.getSelectedCar().getCarID());
                modifyAppointmentSpecialField.setText(selectedCarId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void modifyAppointmentTypeAction(ActionEvent event) {
        if(Objects.equals(modifyAppointmentTypeComboBox.getValue(), "Veterinary")){
            modifyAppointmentSpecialButton.setVisible(true);
            modifyAppointmentSpecialField.setVisible(true);
            modifyAppointmentSpecialLabel.setVisible(true);
        } else if(Objects.equals(modifyAppointmentTypeComboBox.getValue(), "Car Checkup")){
            modifyAppointmentSpecialButton.setVisible(true);
            modifyAppointmentSpecialField.setVisible(true);
            modifyAppointmentSpecialLabel.setVisible(true);
        } else {
            modifyAppointmentSpecialButton.setVisible(false);
            modifyAppointmentSpecialField.setVisible(false);
            modifyAppointmentSpecialLabel.setVisible(false);
        }
    }

    /**
     * switches pages to appointments form when selecting the cancel button
     * @param event
     * */
    @FXML
    void modifyAppointmentCancelAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/AppointmentsForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Appointments Form");
        window.setScene(scene);
        window.show();
    }

    /**
     *  checks for logic errors or empty text boxes/non-selected combo boxes or empty date selectors, if no errors will save and modify selected appointment details then return to the appointments form
     * @param event
     * */
    @FXML
    void modifyAppointmentSaveAction(ActionEvent event) throws IOException {
        try {
            int appointmentId = Integer.parseInt(modifyAppointmentIDField.getText());
            String title = modifyAppointmentTitleField.getText();
            String description = modifyAppointmentDescriptionField.getText();
            String location = modifyAppointmentLocationField.getText();
            String type = modifyAppointmentTypeComboBox.getValue();
            LocalDate startDate = modifyAppointmentStartDatePicker.getValue();
            LocalDate endDate = modifyAppointmentEndDatePicker.getValue();
            LocalTime startTime = modifyAppointmentStartTimeComboBox.getValue();
            LocalTime endTime = modifyAppointmentEndTimeComboBox.getValue();
            String special = modifyAppointmentSpecialField.getText();

            if(title.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter an appointment title.");
                alert.showAndWait();
                return;
            } else if(description.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter an appointment description.");
                alert.showAndWait();
                return;
            } else if(location.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a location.");
                alert.showAndWait();
                return;
            } else if(modifyAppointmentContactComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select an appointment contact.");
                alert.showAndWait();
                return;
            } else if(modifyAppointmentTypeComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter an appointment type.");
                alert.showAndWait();
                return;
            } else if(startDate == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter an appointment start date.");
                alert.showAndWait();
                return;
            } else if(endDate == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter an appointment end date.");
                alert.showAndWait();
                return;
            } else if(endDate.isBefore(startDate)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The end appointment date can not be before the appointment start date.");
                alert.showAndWait();
                return;
            } else if(startTime == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter an appointment start time.");
                alert.showAndWait();
                return;
            } else if(endTime == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter an appointment end time.");
                alert.showAndWait();
                return;
            } else if(endTime.isBefore(startTime)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The end appointment time can not be before the appointment start time.");
                alert.showAndWait();
                return;
            }  else if(modifyAppointmentCustomerIDComboBox.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a customer Id.");
                alert.showAndWait();
                return;
            } else if(modifyAppointmentUserIDComboBox.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a user Id.");
                alert.showAndWait();
                return;
            }
            else {
                location = modifyAppointmentLocationField.getText();
                int contact = modifyAppointmentContactComboBox.getValue().getContactID();
                int customerId = modifyAppointmentCustomerIDComboBox.getValue().getCustomerID();
                int userId = modifyAppointmentUserIDComboBox.getValue().getUserID();
                LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

                if (endDateTime.isBefore(startDateTime)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("The end appointment date and time can not be before the appointment start date and time.");
                    alert.showAndWait();
                    return;
                } else if(location.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please enter a location.");
                    alert.showAndWait();
                    return;
                }
                if(modifyAppointmentTypeComboBox.getValue().equals("Car Checkup") && special.contains("Pet")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please select a Car.");
                    alert.showAndWait();
                    return;
                } else if(modifyAppointmentTypeComboBox.getValue().equals("Veterinary") && special.contains("Car")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please select a Pet.");
                    alert.showAndWait();
                    return;
                }
                if(selectedAppointments instanceof VeterinaryAppointment veterinaryAppointment){
                    veterinaryAppointment.setSpecial(special);
                } else if(selectedAppointments instanceof CarCheckupAppointment carCheckupAppointment){
                    carCheckupAppointment.setSpecial(special);
                }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("do you wish to modify this appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                AppointmentsDAO.modifyAppointment(appointmentId, title, description, location, contact, type, startDateTime, endDateTime, customerId, userId, special);
            } else {
                return;
            }
        }
    } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/AppointmentsForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Appointments Form");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void appointmentExitAction(ActionEvent event) throws IOException {

    }

    /**
     * sets appointment text fields, combo boxes and date pickers based on the appointment selected from the appointments form
     * @param selectedAppointments
     * */
    public void setData(Appointments selectedAppointments){
        try{
            modifyAppointmentIDField.setText((String.valueOf(selectedAppointments.getAppointmentID())));
            modifyAppointmentTitleField.setText(selectedAppointments.getAppointmentTitle());
            modifyAppointmentDescriptionField.setText(selectedAppointments.getAppointmentDescription());
            modifyAppointmentLocationField.setText(selectedAppointments.getAppointmentLocation());
            for (Contacts contacts:ContactsDAO.getAllContacts()){
                if(contacts.getContactID() == selectedAppointments.getAppointmentContact()){
                    modifyAppointmentContactComboBox.setValue(contacts);
                }
            }
            for(String type:modifyAppointmentTypeComboBox.getItems()){
                if(type.equals(selectedAppointments.getAppointmentType())){
                    modifyAppointmentTypeComboBox.setValue(type);
                }
            }
            modifyAppointmentStartDatePicker.setValue(selectedAppointments.getAppointmentStart().toLocalDate());
            modifyAppointmentEndDatePicker.setValue(selectedAppointments.getAppointmentEnd().toLocalDate());
            modifyAppointmentStartTimeComboBox.setValue(selectedAppointments.getAppointmentStart().toLocalTime());
            modifyAppointmentEndTimeComboBox.setValue(selectedAppointments.getAppointmentEnd().toLocalTime());
            for (Customers customers:CustomerDAO.getAllCustomers()){
                if(customers.getCustomerID() == selectedAppointments.getAppointmentCustomerID()){
                    modifyAppointmentCustomerIDComboBox.setValue(customers);
                }
            }
            for (Users users:UserDAO.getAllUsers()){
                if(users.getUserID() == selectedAppointments.getAppointmentUserID()){
                    modifyAppointmentUserIDComboBox.setValue(users);
                }
            }
            String appointmentType = selectedAppointments.getAppointmentType();
            if(appointmentType.equals("Veterinary")){
                modifyAppointmentSpecialField.setText(selectedAppointments.getAppointmentSpecial());
                modifyAppointmentSpecialButton.setVisible(true);
                modifyAppointmentSpecialField.setVisible(true);
                modifyAppointmentSpecialLabel.setVisible(true);
            } else if(appointmentType.equals("Car Checkup")){
                modifyAppointmentSpecialField.setText(selectedAppointments.getAppointmentSpecial());
                modifyAppointmentSpecialButton.setVisible(true);
                modifyAppointmentSpecialField.setVisible(true);
                modifyAppointmentSpecialLabel.setVisible(true);
            } else {
                modifyAppointmentSpecialButton.setVisible(false);
                modifyAppointmentSpecialField.setVisible(false);
                modifyAppointmentSpecialLabel.setVisible(false);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initializes the modify appointment combo boxes and the start and end date picker as well as start and end time combo boxes with the correct local time conversion and the business hours time between 8:00am to 10:00pm
     * @param url
     * @param resourceBundle
     * */
    public void initialize(URL url, ResourceBundle resourceBundle){
        ObservableList<LocalTime> appointmentTime = FXCollections.observableArrayList();
        try{
            modifyAppointmentContactComboBox.setItems(ContactsDAO.getAllContacts());
            modifyAppointmentTypeComboBox.setItems(AppointmentsController.appointmentTypes);
            modifyAppointmentUserIDComboBox.setItems(UserDAO.getAllUsers());
            modifyAppointmentCustomerIDComboBox.setItems(CustomerDAO.getAllCustomers());
            modifyAppointmentStartTimeComboBox.setItems(appointmentTime);
            modifyAppointmentEndTimeComboBox.setItems(appointmentTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ZoneId zoneId = ZoneId.systemDefault();
        LocalTime startDateTime = LocalTime.of(8, 0);
        LocalTime endDateTime = LocalTime.of(22, 0);
        LocalTime currTime = startDateTime;
        LocalDate localDate = LocalDate.now();
        modifyAppointmentStartDatePicker.setValue(localDate);
        modifyAppointmentEndDatePicker.setValue(localDate);
        LocalDateTime startLocalDateTime = LocalDateTime.of(localDate, startDateTime);
        LocalDateTime endLocalDateTime = LocalDateTime.of(localDate, endDateTime);
        ZonedDateTime startDateZone = startLocalDateTime.atZone(ZoneId.of("US/Eastern"));
        ZonedDateTime endDateZone = endLocalDateTime.atZone(ZoneId.of("US/Eastern"));
        ZonedDateTime startDateTimeZone = startDateZone.withZoneSameInstant(zoneId);
        ZonedDateTime endDateTimeZone = endDateZone.withZoneSameInstant(zoneId);
        startDateTime = startDateTimeZone.toLocalTime();
        endDateTime = endDateTimeZone.toLocalTime();
        while(currTime.isBefore(endDateTime)){
            appointmentTime.add(currTime);
            currTime = currTime.plusMinutes(30);
        }
        ObservableList<String> formatTimes = FXCollections.observableArrayList();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");
        for(LocalTime localTime : appointmentTime){
            formatTimes.add(localTime.format(timeFormat));
        }
    }

}
