package com.termproject.bookstore.models;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import java.sql.Date;

@Embeddable
public class Card {

    private String cardNumber;

    @Basic
    private Date expDate;

    private String securityCode;

}
