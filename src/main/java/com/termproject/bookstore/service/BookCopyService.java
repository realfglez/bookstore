package com.termproject.bookstore.service;

import com.termproject.bookstore.models.BookCopy;
import com.termproject.bookstore.repositories.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCopyService {

    @Autowired
    BookCopyRepository bookCopyRepository;

    public BookCopy getBookCopyByIsbn(String isbn) {
        return bookCopyRepository.findByIsbn(isbn);
    }


}
