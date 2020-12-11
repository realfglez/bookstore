package com.termproject.bookstore.service;

import com.termproject.bookstore.models.Book;
import com.termproject.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book save(Book book){
        return bookRepository.save(book);
    }

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

}
