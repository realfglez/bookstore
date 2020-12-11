package com.termproject.bookstore.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class Card {

    private String cardNumber;
    @DateTimeFormat
    private Date expDate;
    private String securityCode;

}
