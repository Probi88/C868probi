package com.example.c868probi.Controller;

import com.example.c868probi.DAO.AppointmentsDAO;
import com.example.c868probi.DAO.UserDAO;
import com.example.c868probi.Helper.JDBC;
import com.example.c868probi.Model.Appointments;
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
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * controller for handling the login form, depending on locale language can display can english or French
 * */
public class LoginController implements Initializable {

    @FXML
    private Button loginButton;

    @FXML
    private Button loginExitButton;

    @FXML
    private Label loginLocationDisplay;

    @FXML
    private Label loginLocationLabel;

    @FXML
    private TextField loginPasswordField;

    @FXML
    private Label loginPasswordLabel;

    @FXML
    private Label loginTitleLabel;

    @FXML
    private TextField loginUsernameField;

    @FXML
    private Label loginUsernameLabel;

    ResourceBundle rb = ResourceBundle.getBundle("Login", Locale.getDefault());

    Boolean loginValid = false;

    boolean upcomingAppointment = false;

    /**
     * initializes login controller, including the locale based on system settings, as well as checking and displaying the zone id of the system
     * @param url
     * @param resourceBundle
     * */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Locale locale = Locale.getDefault();
            Locale.setDefault(locale);
            ZoneId zone = ZoneId.systemDefault();
            if(Locale.getDefault().getLanguage().equals("fr")) {
                loginTitleLabel.setText(rb.getString("Title"));
                loginUsernameLabel.setText(rb.getString("username"));
                loginPasswordLabel.setText(rb.getString("password"));
                loginLocationLabel.setText(rb.getString("location"));
                loginButton.setText(rb.getString("login"));
                loginExitButton.setText(rb.getString("Exit"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Locale locale = new Locale("fr");
        ResourceBundle bundle = ResourceBundle.getBundle("Login", locale);
        ZoneId currZone = ZoneId.systemDefault();
        loginLocationDisplay.setText(String.valueOf(currZone));
        locale = Locale.getDefault();
        System.out.println(locale);
        String language = locale.getDisplayLanguage();
        String country = locale.getDisplayCountry();
        System.getProperty("user.country");
        System.getProperty("user.language");
        LocalDateTime currTime = LocalDateTime.now();
    }

    /**
     * method which checks username and password fields upon clicking login button and if valid login will go to main menu page, will also check and display an alert if there is an upcoming appointment or if no upcoming appointment
     * @param event
     * */
    @FXML
    void loginButtonAction(ActionEvent event) throws IOException {
        if(JDBC.connection == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("JDBC connection not found");
            alert.setContentText("An error has occurred, JDBC connection can not be established");
            alert.showAndWait();
            return;
        }
        String Username = loginUsernameField.getText();
        String Password = loginPasswordField.getText();
        ObservableList<Appointments> getAllAppointments = AppointmentsDAO.getAllAppointments();
        LocalDateTime appointmentTimeMinusAlert = LocalDateTime.now().minusMinutes(15);
        LocalDateTime nowLDT = LocalDateTime.now();
        LocalDateTime plus15LDT = nowLDT.plusMinutes(15);
        ObservableList<Appointments> scheduledAppts = FXCollections.observableArrayList();

        if (Username.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("error"));
            alert.setHeaderText(rb.getString("error"));
            alert.setContentText(rb.getString("errorUsername"));
            alert.showAndWait();
            return;
        } else if (Password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("error"));
            alert.setHeaderText(rb.getString("error"));
            alert.setContentText(rb.getString("errorPassword"));
            alert.showAndWait();
            return;
        }
        boolean loginValid;
        try {
            loginValid = UserDAO.validateUser(Username, Password);
            if (loginValid == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("error"));
                alert.setHeaderText(rb.getString("error"));
                alert.setContentText(rb.getString("errorLogin"));
                alert.showAndWait();
                loginActivityRecord(Username, false);
                return;
            }
            LocalDateTime currTime = LocalDateTime.now();
            LocalDateTime appointmentTimeAlert = currTime.plusMinutes(15);
            boolean upcomingAppointment = false;

            for (Appointments appointments : AppointmentsDAO.getAllAppointments()) {
                LocalDateTime appointmentStartTime; //= appointments.getAppointmentStart();
                appointmentStartTime = appointments.getAppointmentStart();
                if (appointmentStartTime.isAfter(currTime) && appointmentStartTime.isBefore(appointmentTimeAlert)) {
                    upcomingAppointment = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment reminder");
                    alert.setContentText("There is an upcoming appointment at " + appointments.getAppointmentStart() + " for Appointment Id: " + appointments.getAppointmentID());
                    alert.showAndWait();
                    break;
                }
            }
            if (upcomingAppointment == false) {
                System.out.println("There are no upcoming appointments");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment reminder");
                alert.setContentText("There are no upcoming appointments");
                alert.showAndWait();
            }

            Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/MainForm.fxml"));
            Scene scene = new Scene(addparent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Main Menu");
            window.setScene(scene);
            window.show();
            loginValid = true;

            loginActivityRecord(Username, loginValid);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * this method will record login activity regardless of if login is considered successful or a failed login validation
     * @param Username
     * @param loginValid
     * */
    private void loginActivityRecord(String Username, boolean loginValid) throws IOException {
        try {
            LocalDateTime loginDateTime = LocalDateTime.now();
            ZoneId loginTimeZone = ZoneId.systemDefault();
            System.out.println(loginDateTime + " test");
            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print("Username: " + Username + " ");

            if (loginValid == true) {
                printWriter.print("Successful login attempt");
            } else {
                printWriter.print("Failed login attempt");
            }
            printWriter.print(" on login date and time: " + loginDateTime);
            printWriter.print(" time zone of: " + loginTimeZone);
            printWriter.println("");
            printWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * a button which displays a confirmation when confirmed will exit the application
     * @param event
     * */
    @FXML
    void loginExitButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(rb.getString("confirmTitle"));
        alert.setHeaderText(rb.getString("confirm"));
        alert.setContentText(rb.getString("confirmText"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
            System.out.println("Exiting");
        } else {
            alert.close();
        }
    }

    @FXML
    void loginPasswordAction(ActionEvent event) {

    }

    @FXML
    void loginUsernameAction(ActionEvent event) {

    }

}
