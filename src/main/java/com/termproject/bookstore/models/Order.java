package com.termproject.bookstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customerOrder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    private Timestamp dateTime;

    private boolean completed;

    private double price;

    private Address shippingAddress;

    private String confirmationCode;

    private Card card;

    @ManyToOne
    private User user;

    @OneToMany
    private List<OrderItem> items;
}
