package com.team46.stubank;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author Harry Alexander
 **/

public class User implements Serializable {
// User is Serializable, meaning that User objects can be passed between activities


    private String firstName, lastName, email, username, password;
    private String dob, phoneNumber, accountID;
    private int userID, userDetailsID;
    // Private variables identical to the columns in the user table


    // Empty constructor, used to create instances of the User class
    public User() {

    }

    // setter methods used to change attributes for a User object
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserDetailsID(int userDetailsID) {
        this.userDetailsID = userDetailsID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }


    // getter methods used to get the attribute for an object
    public int getUserDetailsID() {
        return userDetailsID;
    }

    public String getAccountID() {
        return accountID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDob() {
        return dob;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }

}
