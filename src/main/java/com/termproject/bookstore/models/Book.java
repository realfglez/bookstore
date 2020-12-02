package com.termproject.bookstore.models;

import javax.persistence.*;

/**
 * Book table
 */
@SuppressWarnings("ALL")
@Entity(name = "book")
public class Book {

    /**
     * Columns for Book table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "category")
    private String category;

    @Column(name = "author")
    private String author;

    @Column(name = "coverPicURL")
    private String coverPicURL;

    @Column(name = "edition")
    private Integer edition;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publicationYear")
    private Integer publicationYear;

    @Column(name = "quantityInStock")
    private Integer quantityInStock;

    @Column(name = "minimumThreshold")
    private Integer minimumThreshold;

    @Column(name = "buyPrice")
    private Double buyPrice;

    @Column(name = "sellPrice")
    private Double sellPrice;

    @Column(name = "isArchived")
    private Boolean isArchived;

    /**
     * Default Constructor for Admin
     */
    public Book() {
    }

    /**
     * Getter and Setter for isbn
     */
    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    /**
     * Getter and Setter for title
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter and Setter for category
     */
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Getter and Setter for author
     */
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Getter and Setter for coverPicURL
     */
    public String getCoverPicURL() {
        return coverPicURL;
    }

    public void setCoverPicURL(String coverPicURL) {
        this.coverPicURL = coverPicURL;
    }

    /**
     * Getter and Setter for edition
     */
    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    /**
     * Getter and Setter for publisher
     */
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Getter and Setter for publicationYear
     */
    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    /**
     * Getter and Setter for quantityInStock
     */
    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    /**
     * Getter and Setter for minimumThreshold
     */
    public Integer getMinimumThreshold() {
        return minimumThreshold;
    }

    public void setMinimumThreshold(Integer minimumThreshold) {
        this.minimumThreshold = minimumThreshold;
    }

    /**
     * Getter and Setter for buyPrice
     */
    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    /**
     * Getter and Setter for sellPrice
     */
    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * Getter and Setter for isArchived
     */
    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    /**
     * toString() method for Book
     */
    @Override
    public String toString() {
        return "Book{" +
                "isbn=" + isbn +
                ", category='" + category + '\'' +
                ", author='" + author + '\'' +
                ", coverPicURL='" + coverPicURL + '\'' +
                ", edition=" + edition +
                ", publisher='" + publisher + '\'' +
                ", publicationYear=" + publicationYear +
                ", quantityInStock=" + quantityInStock +
                ", minimumThreshold=" + minimumThreshold +
                ", buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                ", archived=" + isArchived +
                '}';
    }

}

