package com.termproject.bookstore.service;

import com.termproject.bookstore.models.Book;
import com.termproject.bookstore.models.BookCopy;
import com.termproject.bookstore.repositories.BookCopyRepository;
import com.termproject.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ListIterator;

@Service
public class BookCopyService {

    @Autowired
    BookCopyRepository bookCopyRepository;

    @Autowired
    BookRepository bookRepository;

    public void save(BookCopy bookCopy) {
        bookCopyRepository.save(bookCopy);
    }

    public BookCopy getBookCopyById(int id) {
        return bookCopyRepository.findById(id);
    }

    public BookCopy getSingleBookCopy(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);

        BookCopy bookCopy = null;
        ListIterator<BookCopy> iterator = book.getBookCopies().listIterator();
        while (iterator.hasNext()) {
            bookCopy = iterator.next();
            if (!bookCopy.isReserved()){
                bookCopy.setReserved(true);
                bookCopyRepository.save(bookCopy);
                break;
            }
        }
        return bookCopy;
    }
}
