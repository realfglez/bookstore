package com.termproject.bookstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Card {

    private String cardNumber;

    private String expirationDate;

    private String securityCode;

}
