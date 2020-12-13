package com.termproject.bookstore.models;

import lombok.*;

import javax.persistence.*;


/**
 * Book table
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Book{

    /**
     * Columns for Book table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String isbn;

    private String title;

    private String category;

    private String author;

    @Lob
    private String coverPicUrl;

    private int edition;

    private String publisher;

    private int publicationYear;

    private int quantityInStock;

    private int minimumThreshold;

    private double buyPrice;

    private double sellPrice;

    private boolean archived;

}

