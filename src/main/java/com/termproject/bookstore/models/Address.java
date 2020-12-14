package com.termproject.bookstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Address {

    private String street;
    private String city;
    private String state;
    private String zip;

}
