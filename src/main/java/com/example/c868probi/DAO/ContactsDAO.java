package com.example.c868probi.DAO;

import com.example.c868probi.Helper.JDBC;
import com.example.c868probi.Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * this class handles database SQL query's for contacts
 * */
public class ContactsDAO {

    /**
     * retrieves all contacts from the database
     * @return allContacts
     * */
    public static ObservableList<Contacts> getAllContacts(){
        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int contactId = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                String contactEmail = resultSet.getString("Email");

                Contacts contacts = new Contacts(contactId, contactName, contactEmail);
                allContacts.add(contacts);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return allContacts;
    }

}
