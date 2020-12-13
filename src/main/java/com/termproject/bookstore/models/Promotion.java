package com.termproject.bookstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String promoCode;

    private int percentage;

    @Basic
    private LocalDate startDate;

    @Basic
    private LocalDate expirationDate;

}
