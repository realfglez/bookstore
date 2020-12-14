package com.termproject.bookstore.service;

import com.termproject.bookstore.models.Book;
import com.termproject.bookstore.models.BookCopy;
import com.termproject.bookstore.repositories.BookCopyRepository;
import com.termproject.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookCopyRepository bookCopyRepository;

    public List<Book> saveBooks(List<Book> books){
        return bookRepository.saveAll(books);
    }

    public Book getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);

        if(book == null){
            System.out.println("Could not find book");
        }

        return book;
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public void addBook(Book book) {
        setBookCopies(book);
        bookRepository.save(book);
    }

    public void archiveBook(Book book) {
        Book archivedBook = bookRepository.findByIsbn(book.getIsbn());
        archivedBook.setArchived(true);
        bookRepository.save(archivedBook);
    }

    public void unArchiveBook(Book book){
        Book unArchivedBook = bookRepository.findByIsbn(book.getIsbn());
        unArchivedBook.setArchived(false);
        bookRepository.save(unArchivedBook);
    }

    private void setBookCopies(Book book) {
        List<BookCopy> bookCopies = new ArrayList<>(book.getQuantityInStock());
        for ( BookCopy bookCopy: bookCopies) {
            bookCopy.setTitle(book.getTitle());
            bookCopy.setIsbn(book.getIsbn());
            bookCopy.setAuthor(book.getAuthor());
            bookCopy.setCategory(book.getCategory());
            bookCopy.setCoverPicUrl(book.getCoverPicUrl());
            bookCopy.setSellPrice(book.getSellPrice());
            bookCopy.setEdition(book.getEdition());
            bookCopy.setPublicationYear(book.getPublicationYear());
            bookCopy.setPublisher(book.getPublisher());
            bookCopyRepository.save(bookCopy);
        }
        book.setBookCopies(bookCopies);
    }

}
