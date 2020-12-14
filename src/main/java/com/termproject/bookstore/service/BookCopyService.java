package com.termproject.bookstore.service;

import com.termproject.bookstore.models.Book;
import com.termproject.bookstore.models.BookCopy;
import com.termproject.bookstore.repositories.BookCopyRepository;
import com.termproject.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCopyService {

    @Autowired
    BookCopyRepository bookCopyRepository;

    @Autowired
    BookRepository bookRepository;

    public BookCopy getBookCopyById(int id) {
        return bookCopyRepository.findById(id);
    }

    public BookCopy getSingleBookCopy(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        return book.getBookCopies().get(0);
    }
}
