package com.example.c868probi.Model;

/**
 * model class used for first level divisions entity in customer portion of scheduling application
 * */
public class FirstLevelDivisions {

   private int divisionID;

   private String division;

   private int countryID;

   /**
    * constructor used for first level divisions
    * @param divisionID
    * @param division
    * @param countryID
    * */
   public FirstLevelDivisions(int divisionID, String division, int countryID) {
      this.divisionID = divisionID;
      this.division = division;
      this.countryID = countryID;
   }

   /**
    * gets division id
    * @return divisionID
    * */
   public int getDivisionID() {
      return divisionID;
   }

   /**
    * sets division id
    * @param divisionID
    * */
   public void setDivisionID(int divisionID) {
      this.divisionID = divisionID;
   }

   /**
    * gets division name
    * @return division
    * */
   public String getDivision() {
      return division;
   }

   /**
    * sets division name
    * @param division
    * */
   public void setDivision(String division) {
      this.division = division;
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
    * when viewing a division on the division name will be displayed
    * @return division
    * */
   public String toString(){
      return division;
   }
}
