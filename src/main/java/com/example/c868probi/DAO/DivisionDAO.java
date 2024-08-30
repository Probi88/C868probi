package com.example.c868probi.DAO;

import com.example.c868probi.Helper.JDBC;
import com.example.c868probi.Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * this class handles database SQL query's for first level divisions
 * */
public class DivisionDAO {

    /**
     * retrieves all first level divisions from database
     * @return allDivision an observablelist of all first level divisions
     * */
    public static ObservableList<FirstLevelDivisions> getAllDivisions() {
        ObservableList<FirstLevelDivisions> allDivision = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions, countries WHERE first_level_divisions.Country_ID = countries.Country_ID AND countries.Country = ?";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultset = preparedstatement.executeQuery();

            while (resultset.next()) {
                int divisionID = resultset.getInt("Division_ID");
                String division = resultset.getString("Division");
                int countryID = resultset.getInt("Country_ID");

                FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionID, division, countryID);
                allDivision.add(firstLevelDivisions);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allDivision;
    }

    /**
     *  retrieves first level divisions based on a specific country
     * @param countryName
     * @return countryDivision
     * */
    public static ObservableList<FirstLevelDivisions> countryGetDivision(String countryName) {
        ObservableList<FirstLevelDivisions> countryDivision = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM first_level_divisions, countries WHERE first_level_divisions.Country_ID = countries.Country_ID AND countries.Country = ?";
            PreparedStatement preparedstatement = JDBC.connection.prepareStatement(sql);
            preparedstatement.setString(1, countryName);
            ResultSet resultset = preparedstatement.executeQuery();

            while(resultset.next()){
                int divisionID = resultset.getInt("Division_ID");
                String division = resultset.getString("Division");
                int countryID = resultset.getInt("Country_ID");

                FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionID, division, countryID);
                countryDivision.add(firstLevelDivisions);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    return countryDivision;
    }

}
