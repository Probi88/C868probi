package com.example.c868probi.Controller;

import com.example.c868probi.DAO.AppointmentsDAO;
import com.example.c868probi.DAO.CarDAO;
import com.example.c868probi.DAO.CustomerDAO;
import com.example.c868probi.DAO.PetsDAO;
import com.example.c868probi.Model.Appointments;
import com.example.c868probi.Model.Cars;
import com.example.c868probi.Model.Customers;
import com.example.c868probi.Model.Pets;
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
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class CarsController implements Initializable {

    @FXML
    private Button carAddButton;

    @FXML
    private TableColumn<Cars, String> carAddressColumn;

    @FXML
    private TextField carAddressField;

    @FXML
    private TableColumn<Cars, Integer> carCarID;

    @FXML
    private TableColumn<Cars, Integer> carCustomerIdColumn;

    @FXML
    private Button carExitButton;

    @FXML
    private TextField carIdField;

    @FXML
    private TableColumn<Cars, String> carLicenseColumn;

    @FXML
    private TextField carLicensePlateField;

    @FXML
    private TableColumn<Cars, String> carMakeColumn;

    @FXML
    private TextField carMakeField;

    @FXML
    private TableColumn<Cars, String> carModelColumn;

    @FXML
    private TextField carModelField;

    @FXML
    private Button carModifyButton;

    @FXML
    private TableColumn<Cars, String> carPhoneNumberColumn;

    @FXML
    private TextField carPhoneNumberField;

    @FXML
    private Button carRemoveButton;

    @FXML
    private TextField carSearchField;

    @FXML
    private TableView<Cars> carTable;

    @FXML
    private ComboBox<Integer> carYearComboBox;

    @FXML
    private TableColumn<Cars, Integer> carYearColumn;

    @FXML
    private ComboBox<Customers> customerIdComboBox;

    @FXML
    private TextField customerIdField;

    ObservableList<Cars> carList = FXCollections.observableArrayList();

    private FilteredList<Cars> filteredCars;

    public static Cars modifyCar = null;

    @FXML
    void carAddAction(ActionEvent event) {
        try{
            int customerId = customerIdComboBox.getValue().getCustomerID();
            int carId = CarDAO.nextCarId();
            String carLicense = carLicensePlateField.getText();
            String address = carAddressField.getText();
            String phoneNumber = carPhoneNumberField.getText();
            String carModel = carModelField.getText();
            String carMake = carMakeField.getText();
            int carYear = carYearComboBox.getValue();

            if(customerIdComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a customer id.");
                alert.showAndWait();
                return;
            } else
            if(carLicense.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a car license plate.");
                alert.showAndWait();
                return;
            } else if(address.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter an address.");
                alert.showAndWait();
                return;
            } else if(phoneNumber.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a phone number.");
                alert.showAndWait();
                return;
            } else if(carModel.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a car model.");
                alert.showAndWait();
                return;
            }else if (carMake.isEmpty()){ //== null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a car make");
                alert.showAndWait();
                return;
            } else if(carYearComboBox.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a car year");
                alert.showAndWait();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("do you wish to enter this car?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                CarDAO.addCar(carId, customerId, carLicense, address, phoneNumber, carModel, carMake, carYear);
                carTable.setItems(CarDAO.getAllCars());
                carTable.refresh();
                customerIdComboBox.setValue(null);
                carLicensePlateField.setText("");
                carAddressField.setText("");
                carPhoneNumberField.setText("");
                carModelField.setText("");
                carMakeField.setText("");
                carYearComboBox.setValue(null);
            } else {
                alert.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void carExitAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/MainForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Main Menu");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void carModifyAction(ActionEvent event) throws IOException, SQLException {
        Cars selectCar = carTable.getSelectionModel().getSelectedItem();
        if(selectCar == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must select a car to continue");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        modifyCar = carTable.getSelectionModel().getSelectedItem();
        loader.setLocation(getClass().getResource("/com/example/c868probi/ModifyCarForm.fxml"));
        Parent addparent = loader.load();
        ModifyCarController modifyController = loader.getController();
        modifyController.setData(modifyCar);
        Scene scene = new Scene(addparent);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Modify Car");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void carRemoveAction(ActionEvent event) throws SQLException {
        Cars selectedCar = carTable.getSelectionModel().getSelectedItem();
        if(selectedCar == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must select a car to continue");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("do you wish to delete this car? All associated appointments will also be deleted.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                CarDAO.deleteCar(selectedCar.getCarID());
                for(Appointments appointments : AppointmentsDAO.getAllAppointments()){
                    String appointmentSpecial = appointments.getAppointmentSpecial();
                    if(appointmentSpecial != null && appointmentSpecial.contains(String.valueOf(selectedCar.getCarID()))){
                        AppointmentsDAO.deleteAppointment(appointments.getAppointmentID());
                    }
                }
                Alert Ialert = new Alert(Alert.AlertType.INFORMATION);
                Ialert.setContentText("Car Id: " + selectedCar.getCarID() +  " and all associated appointments have been deleted." );
                Ialert.showAndWait();
                carTable.setItems(CarDAO.getAllCars());
                carTable.refresh();
            } else {
                Alert Ealert = new Alert(Alert.AlertType.ERROR);
                Ealert.setContentText("all appointments with related cars must be deleted before a car can be removed from the database ");
                Ealert.showAndWait();
            }
        }
    }

    @FXML
    void carSearchAction(KeyEvent event) {
    }

    private void refreshCarTable() throws SQLException {
        carList.setAll(CarDAO.getAllCars());
        carTable.setItems(carList);
        carTable.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.customerIdComboBox.setItems(CustomerDAO.getAllCustomers());
            refreshCarTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        carCarID.setCellValueFactory(new PropertyValueFactory<>("carID"));
        carCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        carLicenseColumn.setCellValueFactory(new PropertyValueFactory<>("carLicensePlate"));
        carAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        carPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        carModelColumn.setCellValueFactory(new PropertyValueFactory<>("carModel"));
        carMakeColumn.setCellValueFactory(new PropertyValueFactory<>("carMake"));
        carYearColumn.setCellValueFactory(new PropertyValueFactory<>("carYear"));


        filteredCars = new FilteredList<>(carList, new Predicate<Cars>() {
            @Override
            public boolean test(Cars cars) {
                return true;
            }
        });
        carTable.setItems(filteredCars);
        carSearchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                filteredCars.setPredicate(new Predicate<Cars>() {
                    @Override
                    public boolean test(Cars cars) {
                        if(t1 == null || t1.isEmpty()){
                            return true;
                        }
                        String toLowerCase = t1.toLowerCase();
                        try{
                            int searchCarId = Integer.parseInt(toLowerCase);
                            if(String.valueOf(cars.getCarID()).contains(toLowerCase)){
                                return true;
                            }
                        } catch (NumberFormatException e) {
                        }
                        if(cars.getCarLicensePlate().toLowerCase().contains(toLowerCase)){
                            return true;
                        }
                        return false;
                    }
                });
            }
        });

        int currentYear = LocalDate.now().getYear();
        for(int carYear = 1965; carYear <= currentYear; carYear++){
           carYearComboBox.getItems().add(carYear);

        }
        //1965
    }

}