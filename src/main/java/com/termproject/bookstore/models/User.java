package com.termproject.bookstore.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * User table
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    @Column(unique = true)
    private String username;

    private String password;

    @Transient
    private String confirmPassword;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private boolean active;

    private boolean subscribed;

    private boolean suspended;

    private String verificationCode;

    private String resetPasswordCode;

    @Embedded
    private Address address;

    @Embedded
    private Card card;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Order> order;
}
