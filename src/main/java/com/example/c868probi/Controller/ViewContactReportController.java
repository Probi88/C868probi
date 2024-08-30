package com.example.c868probi.Controller;

import com.example.c868probi.DAO.ContactsDAO;
import com.example.c868probi.DAO.ReportsDAO;
import com.example.c868probi.Model.Contacts;
import com.example.c868probi.Model.Reports;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller class for report view of appointments and appointment information for each contact report table
 * */
public class ViewContactReportController implements Initializable {

    @FXML
    private ComboBox<Contacts> addAppointmentContactComboBox;

    @FXML
    private TableColumn<Reports, Integer> appointmentsCustomerIdColumn;

    @FXML
    private TableColumn<Reports, String> appointmentsDescriptionColumn;

    @FXML
    private TableColumn<Reports, Timestamp> appointmentsEndDateColumn;

    @FXML
    private TableColumn<Reports, Integer> appointmentsIdColumn;

    @FXML
    private TableColumn<Reports, Timestamp> appointmentsStartDateColumn;

    @FXML
    private TableView<Reports> appointmentsContactTable;

    @FXML
    private TableColumn<Reports, String> appointmentsTitleColumn;

    @FXML
    private TableColumn<Reports, String> appointmentsTypeColumn;

    @FXML
    private TableColumn<Reports, Integer> reportsTypeColumn;

    @FXML
    private Label contactViewDateLabel;

    @FXML
    private Button toAppointmentsButton;

    /**
     * Initializes the view for a page with a table a button and a combo box for a report about appointment information which will display based on which contact is selected in the combo box
     * @param event
     */
    @FXML
    void contactComboBoxAction(ActionEvent event) {
        Contacts contacts = addAppointmentContactComboBox.getValue();
        appointmentsContactTable.setItems(ReportsDAO.getAllAppointmentsContact(contacts.getContactID()));
        this.appointmentsIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        this.appointmentsTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        this.appointmentsDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        this.appointmentsTypeColumn.setCellValueFactory(new PropertyValueFactory<>("contactType"));
        this.appointmentsStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        this.appointmentsEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        this.appointmentsCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentCustomerID"));
    }

    public ViewContactReportController() {
    }

    /**
     * switches pages to appointment form when selecting exit button
     * @param event
     * */
    @FXML
    void toAppointmentsAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/AppointmentsForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Appointments Form");
        window.setScene(scene);
        window.show();
    }

    /**
     * initializes page and combo box, the table will be empty until a contact is chosen
     * @param url
     * @param resourceBundle
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addAppointmentContactComboBox.setItems(ContactsDAO.getAllContacts());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        LocalDateTime currDate = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        contactViewDateLabel.setText(currDate.format(dateTimeFormatter));
    }

}
