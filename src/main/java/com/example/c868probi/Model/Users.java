package com.example.c868probi.Model;

import java.time.LocalDateTime;

/**
 * model class used for users in scheduling application for login and appointments
 * */
public class Users {

    private int userID;

    private String userName;

    private String password;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdated;

    /**
     * constructor for users objects
     * @param userID
     * @param userName
     * @param password
     * @param createDate
     * @param lastUpdated
     * */
    public Users(int userID, String userName, String password, LocalDateTime createDate, LocalDateTime lastUpdated) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.lastUpdated = lastUpdated;
    }

    /**
     * get user id
     * @return  userID
     * */
    public int getUserID() {
        return userID;
    }

    /**
     * set user id
     * @param userID
     * */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * get username
     * @return userName
     * */
    public String getUserName() {
        return userName;
    }

    /**
     * set username
     * @param userName
     * */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * gets password
     * @return password
     * */
    public String getPassword() {
        return password;
    }

    /**
     * sets password
     * @param password
     * */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets create date of user
     * @return createDate
     * */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * sets create date of user
     * @param createDate
     * */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * gets last updated date and time of user
     * @return lastUpdated
     * */
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     * sets last updated date and time of user
     * @param lastUpdated
     * */
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * when viewing a user will only display a username
     * @return userName
     * */
    @Override
    public String toString() {
    /*    return "Users{" +
                "userID=" + userID +
                ", createDate=" + createDate +
                ", lastUpdated=" + lastUpdated +
                '}';*/
    return userName;
    }
}
