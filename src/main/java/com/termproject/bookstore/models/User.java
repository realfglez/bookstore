package com.termproject.bookstore.models;

import lombok.*;

import javax.persistence.*;

/**
 * User table
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    private String role;

    private boolean active;

    private boolean subscribed;

    private boolean suspended;

    private String confirmationCode;

    private String resetPasswordCode;

    @Embedded
    private Address address;

    @Embedded
    private Card card;

}
