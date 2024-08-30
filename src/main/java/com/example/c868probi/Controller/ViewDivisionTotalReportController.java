package com.example.c868probi.Controller;

import com.example.c868probi.DAO.ReportsDAO;
import com.example.c868probi.Model.Reports;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller class for report view of total customers per first level division report table
 * */
public class ViewDivisionTotalReportController implements Initializable {

    @FXML
    private Label divisionTotalDateLabel;

    @FXML
    private TableColumn<Reports, String> reportsDivisionColumn;

    @FXML
    private TableColumn<Reports, Integer> reportsDivisionTotalColumn;

    @FXML
    private TableView<Reports> reportsDivisionTotalTable;

    @FXML
    private Button toAppointmentsButton;

    /**
     * Initializes the view for the report table with columns for customers and first level divisions
     * @param url
     * @param resourceBundle
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportsDivisionTotalTable.setItems(ReportsDAO.getDivisionTotalReport());
        reportsDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("Division"));
        reportsDivisionTotalColumn.setCellValueFactory(new PropertyValueFactory<>("totalCustomers"));

        LocalDateTime currDate = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        divisionTotalDateLabel.setText(currDate.format(dateTimeFormatter));
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

}
