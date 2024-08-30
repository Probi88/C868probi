package com.example.c868probi.Model;

public class Pets extends Customers{

    int petID;

    String petName;

    String petSpecies;

    String petBreed;

    String petTypeBreed;

    /**
     * constructor for new customers
     *
     * @param customerID
     * @param customerAddress
     * @param customerPhoneNumber
     */
    public Pets(int petID, int customerID, String petName, String customerAddress, String customerPhoneNumber, String petSpecies, String petBreed) {
        super(customerID, customerAddress, customerPhoneNumber);
        this.petID = petID;
        this.petName = petName;
        this.petSpecies = petSpecies;
        this.petBreed = petBreed;
    }

    public Pets(int customerID, String petName){
        super(customerID);
        this.petName = petName;
    }

    public Pets(int petID, int customerID, String petName, String petTypeBreed) {
        super(customerID);
        this.petID = petID;
        this.petName = petName;
        this.petTypeBreed = petTypeBreed;
    }

    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetSpecies() {
        return petSpecies;
    }

    public void setPetSpecies(String petSpecies) {
        this.petSpecies = petSpecies;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetTypeBreed(){
        //return petSpecies + " - " + petBreed;
        return petTypeBreed;
    }

    public void setPetTypeBreed(String petTypeBreed) {
        this.petTypeBreed = petTypeBreed;
        //this.petTypeBreed = petSpecies + " - " + petBreed;
    }

    @Override
    public String toString() {
        return "Pets{" +
                "petName='" + petName + '\'' +
                ", petSpecies='" + petSpecies + '\'' +
                ", petBreed='" + petBreed + '\'' +
                '}';
    }
}
