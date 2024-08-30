package com.example.c868probi.DAO;

import com.example.c868probi.Helper.JDBC;
import com.example.c868probi.Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * this class handles database SQL query's for users
 * */
public class UserDAO {

/**
 * validates user upon login by checking username and password with those within the database
 * @param Username
 * @param Password
 * @return true if user is valid, false if not valid
 * */
public static boolean validateUser(String Username, String Password) throws SQLException {
    String sql = "SELECT * FROM users WHERE User_name = ? AND Password = ?";
    PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);

    preparedStatement.setString(1, Username);
    preparedStatement.setString(2, Password);
    try{
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        return(resultSet.next());

    } catch (Exception e) {
        //throw new RuntimeException(e);
        return false;
    }

}

/**
 * all users are retrieved from the database and returned
 * @return allUsers
 * */
    public static ObservableList<Users> getAllUsers(){
        ObservableList<Users> allUsers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int userId = resultSet.getInt("User_ID");
                String username = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");
                Timestamp createDate = resultSet.getTimestamp("Create_Date");
                Timestamp lastUpdated = resultSet.getTimestamp("Last_Update");

                Users users = new Users(userId, username, password, createDate.toLocalDateTime(), lastUpdated.toLocalDateTime());
                allUsers.add(users);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }

}
