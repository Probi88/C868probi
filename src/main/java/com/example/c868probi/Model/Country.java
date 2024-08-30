package com.example.c868probi.Model;

/**
 * model used for country entity in customer portion of scheduling application
 * */
public class Country {

    private String countryName;

    private int countryID;

    /**
     * constructor for country objects
     * @param countryName
     * @param countryID
     * */
    public Country(String countryName, int countryID) {
        this.countryName = countryName;
        this.countryID = countryID;
    }

    /**
     * constructor for new country object with just the name
     * @param countryName
     * */
    public Country(String countryName) {
        this.countryName = countryName;
    }

    /**
     * gets country name
     * @return countryName
     * */
    public String getCountryName() {
        return countryName;
    }

    /**
     * sets country name
     * @param countryName
     * */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * gets country id
     * @return countryID
     * */
    public int getCountryID() {
        return countryID;
    }

    /**
     * sets country id
     * @param countryID
     * */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * when viewing countries will only display the country name
     * @return countryName
     * */
    @Override
    public String toString() {
        /*return "Country{" +
                "countryName='" + countryName + '\'' +
                ", countryID=" + countryID +
                '}';*/
    return countryName;
    }
}
