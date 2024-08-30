package com.example.c868probi.Controller;

import com.example.c868probi.DAO.AppointmentsDAO;
import com.example.c868probi.DAO.CustomerDAO;
import com.example.c868probi.DAO.DivisionDAO;
import com.example.c868probi.Model.Appointments;
import com.example.c868probi.Model.Country;
import com.example.c868probi.Model.Customers;
import com.example.c868probi.Model.FirstLevelDivisions;
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

/**
 * controller for adding, deleting and displaying customers
 * */
public class CustomerController implements Initializable {
    @FXML
    public ComboBox<FirstLevelDivisions> customerDivisionCodeComboBox;

    @FXML
    private Button customerAddButton;

    @FXML
    private TableColumn<Customers, String> customerAddressColumn;

    @FXML
    private TextField customerAddressField;

    @FXML
    private TableColumn<Customers, String> customerCountryDataColumn;

    @FXML
    private TableColumn<Customers, String> customerDivisionCodeColumn;

    @FXML
    private ComboBox<Country> customerCountryDataComboBox;

    @FXML
    private Button customerExitButton;

    @FXML
    private TableColumn<Customers, Integer> customerIdColumn;

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField customersSearchField;

    @FXML
    private Button customerModifyButton;

    @FXML
    private TableColumn<Customers, String> customerNameColumn;

    @FXML
    private TextField customerNameField;

    @FXML
    private TableColumn<Customers, String> customerPhoneNumberColumn;

    @FXML
    private TextField customerPhoneNumberField;

    @FXML
    private TableColumn<Customers, String> customerPostalCodeColumn;

    @FXML
    private TextField customerPostalCodeField;

    @FXML
    private Button customerRemoveButton;

    @FXML
    private TableView<Customers> customerTable;

    public static Customers modifyCustomer = null;

    ObservableList<Customers> customerList = FXCollections.observableArrayList();

    private FilteredList<Customers> filteredCustomers;

    public static boolean toInt(String value){
        try {
            Integer.parseInt(value);
        } catch(NumberFormatException nfe){
            return false;
        } catch (NullPointerException e){
            return false;
        }
        return true;
    }

    @FXML
    public void customersSearchAction(KeyEvent event) {

    }

    /**
     * used to populate first level division combo box based on what country is selected
     * @param event
     * */
    @FXML
    void addCustomerSetDivision(ActionEvent event){
        Country country = customerCountryDataComboBox.getValue();
        try {
            customerDivisionCodeComboBox.setDisable(false);
            customerDivisionCodeComboBox.setItems(DivisionDAO.countryGetDivision(country.getCountryName()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * checks for logic errors or empty text boxes/non-selected combo boxes, if no errors will save and add a new customer and details
     * @param event
     * */
    @FXML
    void customerAddAction(ActionEvent event) {
        try {
            int customerId = CustomerDAO.nextCustomerId();
            String name = customerNameField.getText();
            String address = customerAddressField.getText();
            String postalCode = customerPostalCodeField.getText();
            String phoneNumber = customerPhoneNumberField.getText();
            FirstLevelDivisions divisionCode = customerDivisionCodeComboBox.getValue();
            String country = String.valueOf(customerCountryDataComboBox.getValue());

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
            } else if(postalCode.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a postal code.");
                alert.showAndWait();
                return;
            } else if(phoneNumber.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a phone number.");
                alert.showAndWait();
                return;
            }if (country == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a country.");
                alert.showAndWait();
                return;
            } if (divisionCode == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a first level division.");
                alert.showAndWait();
                return;
            }
            int customerDivision = divisionCode.getDivisionID();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("do you wish to enter this customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                CustomerDAO.addCustomer(customerId, name, address, postalCode, phoneNumber, customerDivision, country);
                customerTable.setItems(CustomerDAO.getAllCustomers());
                customerTable.refresh();
                customerNameField.setText("");
                customerAddressField.setText("");
                customerPostalCodeField.setText("");
                customerPhoneNumberField.setText("");
                customerDivisionCodeComboBox.setValue(null);
                customerCountryDataComboBox.setValue(null);
            } else {
                alert.close();
            }
        } catch (Exception e) {
            System.out.println("done messed up");
            throw new RuntimeException(e);

        }
    }

    /**
     * when exit button is selected page will switch to main menu page
     * @param event
     * */
    @FXML
    void customerExitAction(ActionEvent event) throws IOException {
        Parent addparent = FXMLLoader.load(getClass().getResource("/com/example/c868probi/MainForm.fxml"));
        Scene scene = new Scene(addparent);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Main Menu");
        window.setScene(scene);
        window.show();
    }

    /**
     * when a customer item is selected on the table and the modify button clicked will be taken to modify customers form page, otherwise an alert will display
     * @param event
     * */
    @FXML
    void customerModifyAction(ActionEvent event) throws IOException {
        Customers selectCostumer = (Customers)customerTable.getSelectionModel().getSelectedItem();
        if(selectCostumer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must select a customer to continue");
            alert.showAndWait();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        modifyCustomer = customerTable.getSelectionModel().getSelectedItem();
        loader.setLocation(getClass().getResource("/com/example/c868probi/ModifyCustomersForm.fxml"));
        Parent addparent = loader.load();
        ModifyCustomerController modifyController = loader.getController();
        modifyController.setData(modifyCustomer);
        Scene scene = new Scene(addparent);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Modify Customer");
        window.setScene(scene);
        window.show();

    }

    /**
     * when a customer item is selected on the table and the delete button clicked an alert confirmation alert will display if confirmed customer item and any associated appointments on the appointments for with the same customer id will be deleted
     * @param event
     * */
    @FXML
    void customerRemoveAction(ActionEvent event) throws SQLException {
        Customers selectedCustomer = (Customers)customerTable.getSelectionModel().getSelectedItem();
        ObservableList<Appointments> allAppointments = AppointmentsDAO.getAllAppointments();
        if(selectedCustomer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("you must select a customer to continue");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("do you wish to delete this customer? all associated appointments will also be deleted");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                CustomerDAO.deleteCustomer(selectedCustomer.getCustomerID());
                Alert Ialert = new Alert(Alert.AlertType.INFORMATION);
                Ialert.setContentText("Customer Id: " + selectedCustomer.getCustomerID() +  " and all associated appointments have been deleted." );
                Ialert.showAndWait();
                customerTable.setItems(CustomerDAO.getAllCustomers());
                customerTable.refresh();
            } else {
                Alert Ealert = new Alert(Alert.AlertType.ERROR);
                Ealert.setContentText("all customer related appointments must be deleted before a customer can be deleted");
                Ealert.showAndWait();
            }
        }
    }

    private void refreshCustomerTable() throws SQLException {
        customerList.setAll(CustomerDAO.getAllCustomers());
        customerTable.setItems(customerList);
        customerTable.refresh();
    }

    /**
     * initializes customer table and the country combo box
     * @param url
     * @param resourceBundle
     * */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.customerCountryDataComboBox.setItems(CustomerDAO.getAllCountries());
            refreshCustomerTable();
            this.customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            this.customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            this.customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            this.customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            this.customerPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
            this.customerDivisionCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
            this.customerCountryDataColumn.setCellValueFactory(new PropertyValueFactory<>("countryData"));

            filteredCustomers = new FilteredList<>(customerList, new Predicate<Customers>() {
                @Override
                public boolean test(Customers customers) {
                return true;
            }
            });
            customerTable.setItems(filteredCustomers);
            customersSearchField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    filteredCustomers.setPredicate(new Predicate<Customers>() {
                        @Override
                        public boolean test(Customers customers) {
                            if(t1 == null || t1.isEmpty()){
                                return true;
                            }
                            String toLowerCase = t1.toLowerCase();
                            try{
                                int searchCustomerId = Integer.parseInt(toLowerCase);
                                if(String.valueOf(customers.getCustomerID()).contains(toLowerCase)) {
                                    return true;
                                }
                            } catch (NumberFormatException e) {
                            }
                            if(customers.getCustomerName().toLowerCase().contains(toLowerCase)){
                                return true;
                            }
                            return false;
                        }
                    });
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }

}