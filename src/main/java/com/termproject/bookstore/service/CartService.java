package com.termproject.bookstore.service;

import com.termproject.bookstore.models.*;
import com.termproject.bookstore.repositories.BookCopyRepository;
import com.termproject.bookstore.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    BookService bookService;

    @Autowired
    BookCopyService bookCopyService;

    @Autowired
    CartRepository cartRepository;

    public void removeItem(Cart cart, String  isbn){
        for (BookCopy bookCopy:cart.getBookCopies()) {
            if(bookCopy.getIsbn().equals(isbn)) {
                cart.getBookCopies().remove(bookCopy);
                bookCopy.setReserved(false);
                bookCopyService.save(bookCopy);
            }
            break;
        }
        cartRepository.save(cart);
    }

    public void addItem(Cart cart, Book book){
        BookCopy bookCopy = bookCopyService.getSingleBookCopy(book.getIsbn());
        if (!(bookCopy == null)) {
            cart.getBookCopies().add(bookCopy);
            cartRepository.save(cart);
        }
    }

    public Cart orderToCart(Order order) {

        Cart cart = order.getUser().getCart();

        for (BookCopy bookCopy: convertItems(order.getItems())) {
            cart.getBookCopies().add(bookCopy);
        }
        cartRepository.save(cart);
        return cart;
    }

    private List<BookCopy> convertItems(List<OrderItem> orderItems) {
        List<BookCopy> bookCopies = new ArrayList<>();
        for (OrderItem item:orderItems) {
            if(!item.getBook().isArchived() && !item.getBook().getBookCopies().isEmpty()) {

                bookCopies.add(bookCopyService.getSingleBookCopy(item.getIsbn()));
            }
        }
        return bookCopies;
    }
}
