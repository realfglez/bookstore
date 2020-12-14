package com.termproject.bookstore.service;

import com.termproject.bookstore.models.Book;
import com.termproject.bookstore.models.BookCopy;
import com.termproject.bookstore.models.Cart;
import com.termproject.bookstore.repositories.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    BookService bookService;

    @Autowired
    BookCopyRepository bookCopyRepository;

    public void removeItem(Cart cart, String isbn){
        cart.getBookCopies().removeIf(bookCopy -> bookCopy.getIsbn().equals(isbn));
    }

    public void addItem(Cart cart, String isbn){
        cart.getBookCopies().add(bookCopyRepository.findByIsbn(isbn));
    }
}
