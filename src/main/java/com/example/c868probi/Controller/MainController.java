package com.example.c868probi.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * controller for main menu
 * */
public class MainController implements Initializable {

    @FXML
    private Button mainAppointmentsButton;

    @FXML
    private Button mainCustomerButton;

    @FXML
    private Button mainExitButton;

    @FXML
    private Button mainReportsButton;

    @FXML
    private Button mainCarButton;

    LocalDateTime currTime = LocalDateTime.now();

    /**
     * a button which will take the user to the appointments form
     * @param event
     * */
    @FXML
    void mainAppointmentsAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/AppointmentsForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Appointments Form");
        window.setScene(scene);
        window.show();
    }

    /**
     * a button which will take the user to the customers form
     * @param event
     * */
    @FXML
    void mainCustomerAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/CustomerForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Customers Form");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void mainPetAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/PetForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Pets Form");
        window.setScene(scene);
        window.show();
    }

    /**
     * a button which will display a confirmation, if confirm is selected the application will close
     * @param event
     * */
    @FXML
    void mainExitAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setContentText("Would you like to exit the application?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
            System.out.println("Exiting");
        } else {
            alert.close();
        }
    }

    @FXML
    void mainReportsAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("ReportsForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("test title");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void mainCarAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/CarForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Car Form");
        window.setScene(scene);
        window.show();
    }

    /**
     * initializes the main menu page
     * @param url
     * @param resourceBundle
     * */
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}

