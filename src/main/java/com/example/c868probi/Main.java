package com.example.c868probi;

import com.example.c868probi.Helper.JDBC;
import com.example.c868probi.Model.Cars;
import com.example.c868probi.Model.Customers;
import com.example.c868probi.Model.Pets;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Phillip Robinson
 * Main class is used to start the application directing you to the login page of the scheduling application which will be the first page of the application
 * two lambdas one in addAppointmentMonthAction method, another in the appointmentDeleteAction method, both in the appointments controller
 * */
public class Main extends Application {

    Stage stage;
    Parent scene;

    /**
     * starts first login form page
     * @param stage
     * */
    @Override
    public void start(Stage stage) throws Exception {
        JDBC.openConnection();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/c868probi/LoginForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Login Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * main method contains launch method for the application and opens JDBC connection to SQL database
     * @param args
     * */
    public static void main(String[] args) {
        LocalDateTime currTime = LocalDateTime.now();
        System.out.println(currTime);
        launch(args);
        JDBC.closeConnection();
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        LocalDateTime nowDateTime = LocalDateTime.of(nowDate, nowTime);
        System.out.println("It's currently " + nowDateTime + " where I am");
    }

    }