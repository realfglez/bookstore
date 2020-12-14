package com.termproject.bookstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String isbn;

    private String title;

    private String category;

    private String author;

    @Lob
    private String coverPicUrl;

    private int edition;

    private String publisher;

    private int publicationYear;

    private double sellPrice;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Book book;
}
