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

    public void removeItem(Cart cart, String  isbn){
        for (BookCopy bookCopy:cart.getBookCopies()) {
            if(bookCopy.getIsbn().equals(isbn)) {
                cart.getBookCopies().remove(bookCopy);
            }
            break;
        }
    }

    public void addItem(Cart cart, BookCopy bookCopy){
        cart.getBookCopies().add(bookCopy);
    }
}
