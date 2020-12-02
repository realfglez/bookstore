package com.termproject.bookstore.models;

import com.termproject.bookstore.validation.Hasher;

import javax.persistence.*;

/**
 * User table
 */
@Entity(name = "user")
public class User {

    /**
     * Columns for User table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    private String userName;

    private String firstName;

    private String lastName;

    private String userPW;

    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Boolean isEmployee;

    private Boolean isSubscribed;
    /**
     * Default Constructor for User
     */
    public User() {
    }

    /**
     * Getter and Setter for userID
     */
    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    /**
     * Getter and Setter for userName
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter and Setter for firstName
     */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter and Setter for lastName
     */
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter and Setter for userPW
     */
    public String getUserPW() {
        return userPW;
    }

    public void setUserPW(String userPW) {
        Hasher hasher = new Hasher();
        this.userPW = hasher.hashPassword(userPW);
    }

    /**
     * Getter and Setter for email
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter and Setter for status
     */
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Getter and Setter for isEmployee
     */
    public Boolean getIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(Boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    /**
     * Getter and Setter for isSubscribed
     */
    public Boolean getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(Boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

}
