package com.example.c868probi.Model;

/**
 * model used for the creation and storing of contacts within scheduling application
 * */
public class Contacts {

    private int contactID;

    private String contactName;

    private String contactEmail;

    /**
     * constructor for contact objects
     * @param contactID
     * @param contactName
     * @param contactEmail
     * */
    public Contacts(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * gets contact id
     * @return contactID
     * */
    public int getContactID() {
        return contactID;
    }

    /**
     * sets contact id
     * @param contactID
     * */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * gets contact name
     * @return contactName
     * */
    public String getContactName() {
        return contactName;
    }

    /**
     * sets contact name
     * @param contactName
     * */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * gets contact email
     * @return contactEmail
     * */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * sets contact Email
     * @param contactEmail
     * */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * when viewing contacts will display only the contact name
     * @return contactName
     * */
    @Override
    public String toString() {
        /*return "Contacts{" +
                "contactID=" + contactID +
                ", contactName='" + contactName + '\'' +
                '}';*/
        return contactName;
    }
}
