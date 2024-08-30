package com.example.c868probi.Controller;

import com.example.c868probi.DAO.AppointmentsDAO;
import com.example.c868probi.DAO.ContactsDAO;
import com.example.c868probi.DAO.CustomerDAO;
import com.example.c868probi.DAO.UserDAO;
import com.example.c868probi.Model.*;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

/**
 * controller for adding, deleting, and displaying appointments
 */
public class AppointmentsController implements Initializable {

    @FXML
    private ComboBox<Contacts> addAppointmentContactComboBox;

    @FXML
    private ComboBox<Customers> addAppointmentCustomerIDComboBox;

    @FXML
    private ComboBox<String> addAppointmentTypeComboBox;

    @FXML
    private TextField addAppointmentDescriptionField;

    @FXML
    private DatePicker addAppointmentEndDatePicker;

    @FXML
    private ComboBox<LocalTime> addAppointmentEndTimeComboBox;

    @FXML
    private TextField addAppointmentIDField;

    @FXML
    private TextField addAppointmentLocationField;

    @FXML
    private DatePicker addAppointmentStartDatePicker;

    @FXML
    private ComboBox<LocalTime> addAppointmentStartTimeComboBox;

    @FXML
    private TextField addAppointmentTitleField;

    @FXML
    private TextField addAppointmentTypeField;

    @FXML
    private TextField appointmentsSearchField;

    @FXML
    private ComboBox<Users> addAppointmentUserIDComboBox;

    @FXML
    private Button appointmentAddButton;

    @FXML
    private Button appointmentDeleteButton;

    @FXML
    private Button appointmentExitButton;

    @FXML
    private Button appointmentModifyButton;

    @FXML
    private Button appointmentContactViewButton;

    @FXML
    private Button appointmentUserViewButton;

    @FXML
    private Button appointmentViewMonthTypeButton;

    @FXML
    private ToggleGroup appointmentsRadioToggle;

    @FXML
    private TableColumn<Appointments, Integer> appointmentsContactColumn;

    @FXML
    private TableColumn<Appointments, Integer> appointmentsCustomerIdColumn;

    @FXML
    private TableColumn<Appointments, String> appointmentsDescriptionColumn;

    @FXML
    private TableColumn<Appointments, Timestamp> appointmentsEndDateColumn;

    @FXML
    private TableColumn<Appointments, Integer> appointmentsIdColumn;

    @FXML
    private TableColumn<Appointments, String> appointmentsLocationColumn;

    @FXML
    private TableColumn<Appointments, Timestamp> appointmentsStartDateColumn;

    @FXML
    private TableColumn<Appointments, String> appointmentsTitleColumn;

    @FXML
    private TableColumn<Appointments, String> appointmentsTypeColumn;

    @FXML
    private TableColumn<Appointments, Integer> appointmentsUserIDColumn;

    @FXML
    private TableColumn<Appointments, String> appointmentsSpecialColumn;

    @FXML
    private RadioButton radioAllAppointments;

    @FXML
    private RadioButton radioMonthAppointments;

    @FXML
    private RadioButton radioWeekAppointments;

    @FXML
    private RadioButton radioTypeAppointments;

    @FXML
    private RadioButton radioContactAppointments;

    @FXML
    public TableView<Appointments> appointmentsTable;

    public static Appointments modifyAppointments;

    static String[] typeList = new String[]{"Planning Session", "Dentist", "Veterinary", "Car Checkup", "Doctor Checkup"};

    static ObservableList<String> appointmentTypes = FXCollections.observableArrayList(typeList);

    ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

    private FilteredList<Appointments> filteredAppointments;

    private static int selectedPetId = 1;

    public static void setSelectedPet(int petId) {
        selectedPetId = petId;
    }

    private Pets petSelectionPopup() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/c868probi/PetSelectionForm.fxml"));
            Parent root = fxmlLoader.load();
            PetSelectionController petSelectionController = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("Select a Pet");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            return petSelectionController.getSelectedPet();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Cars carSelectionPopup() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/c868probi/CarSelectionForm.fxml"));
            Parent root = fxmlLoader.load();
            CarSelectionController carSelectionController = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("Select a Car");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            return carSelectionController.getSelectedCar();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void appointmentsSearchAction(KeyEvent event) {

    }

    /**
     * when the View By Contact button is selected the user is taken to the Contact report page
     *
     * @param event
     */
    @FXML
    void appointmentViewContactAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/ViewByContactForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Contact Report");
        window.setScene(scene);
        window.show();
    }

    /**
     * when the View By Month and Type button is selected the user is taken to the Month and Type report page
     *
     * @param event
     */
    @FXML
    void appointmentViewMonthTypeAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/ViewByMonthAndTypeForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Month and Type Report");
        window.setScene(scene);
        window.show();
    }

    /**
     * when the View By Customer Divisions button is selected the user is taken to the Customer Divisions report page
     *
     * @param event
     */
    @FXML
    void appointmentViewUserAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/ViewByDivisionTotalForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Customer Divisions Report");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void addAppointmentContactAction(ActionEvent event) {
        ObservableList<Appointments> allAppointments = AppointmentsDAO.getAllAppointments();
        ObservableList<Appointments> appointmentsContact = FXCollections.observableArrayList();
        if (radioContactAppointments.isSelected()) {
            allAppointments.sort(Comparator.comparing(Appointments::getAppointmentContact));
            appointmentsTable.setItems(allAppointments);
        }
    }

    /**
     * radio button which displays all appointments on table when selected, this is selected by default when running the application
     *
     * @param event
     */
    @FXML
    void addAppointmentAllAction(ActionEvent event) {
        if (radioAllAppointments.isSelected()) {
            appointmentsTable.setItems(filteredAppointments);
            appointmentsTable.refresh();
        }
    }

    @FXML
    void addAppointmentTypeAction(ActionEvent event) {
        ObservableList<Appointments> allAppointments = AppointmentsDAO.getAllAppointments();
        ObservableList<Appointments> appointmentsType = FXCollections.observableArrayList();
        if (radioTypeAppointments.isSelected()) {
            allAppointments.sort(Comparator.comparing(Appointments::getAppointmentType));
            appointmentsTable.setItems(allAppointments);
            appointmentsTable.refresh();
            for (Appointments appointments : allAppointments) {
                String type = appointments.getAppointmentType();
                boolean typeUsed = false;
                for (Appointments appointmentsTest : appointmentsType) {
                    if (appointmentsTest.getAppointmentType().equals(type)) {
                        typeUsed = true;
                        break;
                    }
                }
                if (typeUsed == false) {
                    appointmentsType.add(appointments);
                }
            }
            FXCollections.sort(appointmentsType, Comparator.comparing(Appointments::getAppointmentType));
        }
    }

    /**
     * radio button which displays all appointments during the current month on the table
     * lambda #1 replaces for loop and if statement making filtered appointments by month much easier to read when reviewing code
     *
     * @param event
     */
    @FXML
    void addAppointmentMonthAction(ActionEvent event) {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        if (radioMonthAppointments.isSelected()) {
            LocalDateTime todayDate = LocalDateTime.now();
            Month currentMonth = todayDate.getMonth();
            ObservableList<Appointments> appointmentsMonth = AppointmentsDAO.getAllAppointments().filtered(
                    appointments -> appointments.getAppointmentStart().getMonth() == currentMonth);
            appointmentsTable.setItems(appointmentsMonth);
            appointmentsTable.refresh();
        }
    }

    /**
     * a radio button which displays all appointments on the table sorted based on week, this method also features a check for duplications to ensure only one appointment id is on the table at one time
     *
     * @param event
     */
    @FXML
    void addAppointmentWeekAction(ActionEvent event) {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        ObservableList<Appointments> appointmentsWeek = AppointmentsDAO.getAppointmentsWeek();
        boolean dupeCheck = false;
        if (radioWeekAppointments.isSelected()) {
            LocalDate todayDate = LocalDate.now();
            LocalDate startOfWeek = todayDate.with(DayOfWeek.MONDAY);
            LocalDate endOfWeek = todayDate.with(DayOfWeek.SUNDAY);
            appointmentsTable.setItems(appointmentsWeek);
            appointmentsTable.refresh();
        }
    }


    /**
     * checks for logic errors or empty text boxes/non-selected combo boxes or date pickers, if no errors will save and add a new appointment and details
     *
     * @param event
     */
    @FXML
    void appointmentAddAction(ActionEvent event) throws SQLException {
        try {
            int appointmentId = AppointmentsDAO.nextAppointmentId();
            String title = addAppointmentTitleField.getText();
            String description = addAppointmentDescriptionField.getText();
            String location = addAppointmentLocationField.getText();
            LocalDate startDate = addAppointmentStartDatePicker.getValue();
            LocalDate endDate = addAppointmentEndDatePicker.getValue();
            LocalTime startTime = addAppointmentStartTimeComboBox.getValue();
            LocalTime endTime = addAppointmentEndTimeComboBox.getValue();
            String selectedType = addAppointmentTypeComboBox.getSelectionModel().getSelectedItem();
            String special = null;
            Appointments newAppointment = null;

            if (title.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter an appointment title.");
                alert.showAndWait();
                return;
            }
            if (description.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter an appointment description.");
                alert.showAndWait();
                return;
            } else if (location.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a location.");
                alert.showAndWait();
                return;
            } else if (addAppointmentContactComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select an appointment contact.");
                alert.showAndWait();
                return;
            } else if (startDate == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter an appointment start date.");
                alert.showAndWait();
                return;
            } else if (endDate == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter an appointment end date.");
                alert.showAndWait();
                return;
            } else if (endDate.isBefore(startDate)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The end appointment date can not be before the appointment start date.");
                alert.showAndWait();
                return;
            } else if (startTime == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter an appointment start time.");
                alert.showAndWait();
                return;
            } else if (endTime == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter an appointment end time.");
                alert.showAndWait();
                return;
            } else if (endTime.isBefore(startTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The end appointment time can not be before the appointment start time.");
                alert.showAndWait();
                return;
            } else if (addAppointmentCustomerIDComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a customer Id.");
                alert.showAndWait();
                return;
            } else if (addAppointmentUserIDComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a user Id.");
                alert.showAndWait();
                return;
            } else {
                title = addAppointmentTitleField.getText();
                int contact = addAppointmentContactComboBox.getValue().getContactID();
                int customerId = addAppointmentCustomerIDComboBox.getValue().getCustomerID();
                int userId = addAppointmentUserIDComboBox.getValue().getUserID();
                LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

                if (endDateTime.isBefore(startDateTime)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("The end appointment date and time can not be before the appointment start date and time.");
                    alert.showAndWait();
                    return;
                } else if (title.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please enter an appointment title.");
                    alert.showAndWait();
                    return;
                }
                for(Appointments appointments : AppointmentsDAO.getAllAppointments()){
                    if(appointments.getAppointmentStart().isAfter(startDateTime) && appointments.getAppointmentStart().isBefore(endDateTime) || appointments.getAppointmentStart().isBefore(startDateTime) && appointments.getAppointmentEnd().isAfter(startDateTime) || appointments.getAppointmentStart().equals(startDateTime)){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Appointments must not have an overlap in times with other appointments.");
                        alert.showAndWait();
                        return;
                    }
                }
                if ("Veterinary".equals(selectedType)) {
                    Pets selectPet = petSelectionPopup();
                    if(selectPet == null){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Please select a pet before creating an appointment");
                        alert.showAndWait();
                        return;
                    } else {
                        special = String.valueOf(selectPet.getPetID());
                        newAppointment = new VeterinaryAppointment(appointmentId, title, description, location, contact, selectedType, startDateTime, endDateTime, customerId, userId, special);
                        ((VeterinaryAppointment) newAppointment).setPets(selectPet);
                        }
                    } else if ("Car Checkup".equals(selectedType)) {
                        Cars selectCar = carSelectionPopup();
                    if(selectCar == null){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Please select a car before creating an appointment");
                        alert.showAndWait();
                        return;
                    } else {
                            special = String.valueOf(selectCar.getCarID());
                            newAppointment = new CarCheckupAppointment(appointmentId, title, description, location, contact, selectedType, startDateTime, endDateTime, customerId, userId, special);
                            ((CarCheckupAppointment) newAppointment).setCars(selectCar);
                            }
                        } else {
                            newAppointment = new NormalAppointment(appointmentId, title, description, location, contact, selectedType, startDateTime, endDateTime, customerId, userId, special);
                        }
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("do you wish to enter this appointment?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            AppointmentsDAO.addAppointment(appointmentId, title, description, location, contact, selectedType, startDateTime, endDateTime, customerId, userId, special);
                            appointmentsTable.setItems(AppointmentsDAO.getAllAppointments());
                            appointmentsTable.refresh();
                            addAppointmentTitleField.setText("");
                            addAppointmentDescriptionField.setText("");
                            addAppointmentLocationField.setText("");
                            addAppointmentTypeComboBox.setValue(null);
                            addAppointmentContactComboBox.setValue(null);
                            addAppointmentCustomerIDComboBox.setValue(null);
                            addAppointmentStartTimeComboBox.setValue(null);
                            addAppointmentEndTimeComboBox.setValue(null);
                            addAppointmentUserIDComboBox.setValue(null);
                        } else {
                            alert.close();
                        }
                    }
        }catch (NumberFormatException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * when an item on the appointments table is selected and the delete button is clicked a conformation alert will appear if confirmed the selected appointment will be deleted from the database and table, and an alert will display confirming the delete
     * lambda #2 by placing my alerts and delete code within this lambda for the delete and associated appointment delete, it can be easier to read and paint a better picture on what this code within the lambda is trying to accomplish
     *
     * @param event
     */
    @FXML
    public void appointmentDeleteAction(ActionEvent event) {
        Appointments selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must select an appointment to continue");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("do you wish to delete this Appointment?");
            //lambda
            alert.showAndWait().filter(result -> result == ButtonType.OK).ifPresent((result -> {
                AppointmentsDAO.deleteAppointment(selectedAppointment.getAppointmentID());
                appointmentsTable.setItems(AppointmentsDAO.getAllAppointments());
                appointmentsTable.refresh();
                Alert deleteAlert = new Alert(Alert.AlertType.INFORMATION);
                deleteAlert.setContentText("Appointment Id: " + selectedAppointment.getAppointmentID() + " of appointment type: " + selectedAppointment.getAppointmentType() + " has been deleted.");
                deleteAlert.showAndWait();
            }));
        }
    }

    /**
     * when the exit button is clicked user will exit to Main Menu page
     *
     * @param event
     */
    @FXML
    void appointmentExitAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/MainForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Main Menu");
        window.setScene(scene);
        window.show();
    }

    /**
     * when a user selects a appointment on the table and clicks the modify button, user will go to the modify appointment page with all the details of the selected appointment
     *
     * @param event
     */
    @FXML
    void appointmentModifyAction(ActionEvent event) throws IOException {
        Appointments selectAppointment = (Appointments) appointmentsTable.getSelectionModel().getSelectedItem();
        if (selectAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must select an appointment to continue");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        modifyAppointments = appointmentsTable.getSelectionModel().getSelectedItem();
        loader.setLocation(getClass().getResource("/com/example/c868probi/ModifyAppointmentsForm.fxml"));
        Parent addparent = loader.load();
        ModifyAppointmentsController modifyController = loader.getController();
        modifyController.setData(modifyAppointments);
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Modify Appointment");
        window.setScene(scene);
        window.show();
    }

    /**
     * Initializes the appointment table, combo boxes and the start and end date picker as well as start and end time combo boxes with the correct local time conversion and the business hours time between 8:00am to 10:00pm
     *
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<LocalTime> appointmentTime = FXCollections.observableArrayList();
        try {
            addAppointmentContactComboBox.setItems(ContactsDAO.getAllContacts());
            addAppointmentUserIDComboBox.setItems(UserDAO.getAllUsers());
            addAppointmentCustomerIDComboBox.setItems(CustomerDAO.getAllCustomers());
            addAppointmentStartTimeComboBox.setItems(appointmentTime);
            addAppointmentEndTimeComboBox.setItems(appointmentTime);
            addAppointmentTypeComboBox.setItems(appointmentTypes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        appointmentList.setAll(AppointmentsDAO.getAllAppointments());
        filteredAppointments = new FilteredList<>(appointmentList);
        appointmentsTable.setItems(filteredAppointments);

        this.appointmentsTable.setItems(AppointmentsDAO.getAllAppointments());
        this.appointmentsIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        this.appointmentsTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        this.appointmentsDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        this.appointmentsLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        this.appointmentsContactColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentContact"));
        this.appointmentsTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        this.appointmentsStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        this.appointmentsEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        this.appointmentsCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentCustomerID"));
        this.appointmentsUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentUserID"));
        this.appointmentsSpecialColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentSpecial"));
        filteredAppointments = new FilteredList<>(appointmentList, new Predicate<Appointments>() {
            @Override
            public boolean test(Appointments appointments) {
                return true;
            }
        });
        appointmentsSearchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                filteredAppointments.setPredicate(new Predicate<Appointments>() {
                    @Override
                    public boolean test(Appointments appointments) {
                        if(t1 == null || t1.isEmpty()){
                            return true;
                        }
                        String toLowerCase = t1.toLowerCase();
                        try{
                            int searchAppointmentsId = Integer.parseInt(toLowerCase);
                            if(String.valueOf(appointments.getAppointmentID()).contains(toLowerCase)){
                                return true;
                            }
                        } catch (NumberFormatException e) {
                        }
                        if(appointments.getAppointmentTitle().toLowerCase().contains(toLowerCase)){
                            return true;
                        } else if(appointments.getAppointmentType().toLowerCase().contains(toLowerCase)){
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
        appointmentsTable.setItems(filteredAppointments);
        ZoneId zoneId = ZoneId.systemDefault();
        LocalTime startDateTime = LocalTime.of(8, 0);
        LocalTime endDateTime = LocalTime.of(22, 0);
        LocalTime currTime = startDateTime;
        LocalDate localDate = LocalDate.now();
        addAppointmentStartDatePicker.setValue(localDate);
        addAppointmentEndDatePicker.setValue(localDate);
        LocalDateTime startLocalDateTime = LocalDateTime.of(localDate, startDateTime);
        LocalDateTime endLocalDateTime = LocalDateTime.of(localDate, endDateTime);
        ZonedDateTime startDateZone = startLocalDateTime.atZone(ZoneId.of("US/Eastern"));
        ZonedDateTime endDateZone = endLocalDateTime.atZone(ZoneId.of("US/Eastern"));
        ZonedDateTime startDateTimeZone = startDateZone.withZoneSameInstant(zoneId);
        ZonedDateTime endDateTimeZone = endDateZone.withZoneSameInstant(zoneId);
        startDateTime = startDateTimeZone.toLocalTime();
        endDateTime = endDateTimeZone.toLocalTime();

        while (currTime.isBefore(endDateTime)) {
            appointmentTime.add(currTime);
            currTime = currTime.plusMinutes(30);
        }

        ObservableList<String> formatTimes = FXCollections.observableArrayList();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");
        for (LocalTime localTime : appointmentTime) {
            formatTimes.add(localTime.format(timeFormat));
        }
    }

}

