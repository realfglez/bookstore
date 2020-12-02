package com.termproject.bookstore.models;

import javax.persistence.*;

/**
 * Promotion table
 */
@Entity(name = "promotion")
public class Promotion {

    /**
     * Columns for Promotion table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "percentage")
    private Integer percentage;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    /**
     * Default Constructor for Promotion
     */
    public Promotion() {
    }

    /**
     * Getters and Setters for promoCode
     */
    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
/**
     * equals() method for Promotion
     */


    /**
     * hashCode() method for Promotion
     */
}