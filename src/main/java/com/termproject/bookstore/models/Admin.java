package com.termproject.bookstore.models;

import javax.persistence.*;

import com.termproject.bookstore.validation.Hasher;

/**
 * Admin table
 */
@Entity(name = "admin")
public class Admin {

    /**
     * Columns for Admin table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private int adminID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "admin_pw")
    private String adminPW;

    /**
    * Default Constructor for Admin
    */
    public Admin() {

    }

    /**
    * Getter and Setter for adminID
    */
    public int getId() {
        return adminID;
    }

    public void setId(int id) {
        this.adminID = id;
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
     * Getter and Setter for adminPW
     */
    public String getAdminPW() {
        return adminPW;
    }

    public void setAdminPW(String adminPW) {
        Hasher hasher = new Hasher();
        this.adminPW = hasher.hashPassword(adminPW);
    }

    /**
     * equals() method for Admin
     */


    /**
     * toString() method for Admin
     */
    @Override
    public String toString() {
        return "Admin{" +
                "adminID=" + adminID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", adminPW='" + adminPW + '\'' +
                '}';
    }
}
