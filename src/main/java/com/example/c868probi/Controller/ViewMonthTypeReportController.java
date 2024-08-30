package com.example.c868probi.Controller;

import com.example.c868probi.DAO.ReportsDAO;
import com.example.c868probi.Model.ReportType;
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
 * Controller class for report view of month total and type total report table
 * */
public class ViewMonthTypeReportController implements Initializable {

    @FXML
    private Label monthTypeDateLabel;

    @FXML
    private TableColumn<Reports, String> reportsMonthColumn;

    @FXML
    private TableColumn<Reports, Integer> reportsTotalMonthColumn;

    @FXML
    private TableView<Reports> reportsTotalMonthTable;

    @FXML
    private TableColumn<ReportType, Integer> reportsTotalTypeColumn;

    @FXML
    private TableView<ReportType> reportsTotalTypeTable;

    @FXML
    private TableColumn<ReportType, String> reportsTypeColumn;

    @FXML
    private Button toAppointmentsButton;

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
     * Initializes the view for two tables a total appointments per month and a total appointments per appointment type
     * @param url
     * @param resourceBundle
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.reportsTotalTypeTable.setItems(ReportsDAO.getTypeTotalReport());
        this.reportsTotalTypeColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        this.reportsTypeColumn.setCellValueFactory(new PropertyValueFactory<>("monthType"));

        this.reportsTotalMonthTable.setItems(ReportsDAO.getMonthTotalReport());
        this.reportsTotalMonthColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        this.reportsMonthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));

        LocalDateTime currDate = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        monthTypeDateLabel.setText(currDate.format(dateTimeFormatter));
    }

}
