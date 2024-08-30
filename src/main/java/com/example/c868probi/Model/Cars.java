package com.example.c868probi.Model;

public class Cars extends Customers{

    int carID;

    String carLicensePlate;

    String carModel;

    String carMake;

    int carYear;

    String carMakeModel;

    public Cars(int carID, int customerID, String carLicensePlate, String carModel, String customerAddress, String customerPhoneNumber, String carMake, int carYear) {
        super(customerID, customerAddress, customerPhoneNumber);
        this.carID = carID;
        this.carLicensePlate = carLicensePlate;
        this.carModel = carModel;
        this.carMake = carMake;
        this.carYear = carYear;
    }

    public Cars(int customerID, String carLicensePlate){
        super(customerID);
        this.carLicensePlate = carLicensePlate;
    }

    public Cars(int carID, int customerID, String carLicensePlate, String carMakeModel){
        super(customerID);
        this.carID = carID;
        this.carLicensePlate = carLicensePlate;
        this.carMakeModel = carMakeModel;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public int getCarYear() {
        return carYear;
    }

    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    public String getCarLicensePlate() {
        return carLicensePlate;
    }

    public void setCarLicensePlate(String carLicensePlate) {
        this.carLicensePlate = carLicensePlate;
    }

    public String getCarMakeModel() {
        return carMakeModel;
    }

    public void setCarMakeModel(String carMakeModel) {
        this.carMakeModel = carMakeModel;
    }

    @Override
    public String toString() {
        return "Cars{" +
                "carID=" + carID +
                ", carModel='" + carModel + '\'' +
                ", carMake='" + carMake + '\'' +
                ", carYear=" + carYear +
                ", carLicensePlate='" + carLicensePlate + '\'' +
                '}';
    }
}
