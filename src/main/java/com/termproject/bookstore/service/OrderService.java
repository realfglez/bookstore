package com.termproject.bookstore.service;

import com.termproject.bookstore.models.BookCopy;
import com.termproject.bookstore.models.Cart;
import com.termproject.bookstore.models.Order;
import com.termproject.bookstore.models.OrderItem;
import com.termproject.bookstore.repositories.BookRepository;
import com.termproject.bookstore.repositories.OrderItemRepository;
import com.termproject.bookstore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    public Order placeOrder(Cart cart) {

        Random random = new Random();
        int randomCode = random.nextInt(9999) + random.nextInt(9999);

        int change = 0;

        Order order = new Order();
        order.setPrice(calculateTotal(cart));
        order.setUser(cart.getUser());
        order.setConfirmationCode(Integer.toString(randomCode));
        order.setDateTime(LocalDateTime.now());
        order.setItems(convertItems(cart.getBookCopies()));

        change = order.getItems().size();

        order.setCompleted(true);
        orderRepository.save(order);

        return order;
    }

    private double calculateTotal(Cart cart){
        double total = 0;

        for (BookCopy bookCopy:cart.getBookCopies()) {
            total += bookCopy.getSellPrice();
        }
        return total;
    }

    private List<OrderItem> convertItems(List<BookCopy> bookCopies){


        List<OrderItem> orderItems = new ArrayList<>();
        for (BookCopy bookCopy:bookCopies) {
            OrderItem orderItem = new OrderItem();
            orderItem.setAuthor(bookCopy.getAuthor());
            orderItem.setBook(bookCopy.getBook());
            orderItem.setCategory(bookCopy.getCategory());
            orderItem.setCoverPicUrl(bookCopy.getCoverPicUrl());
            orderItem.setEdition(bookCopy.getEdition());
            orderItem.setIsbn(bookCopy.getIsbn());
            orderItem.setPublicationYear(bookCopy.getPublicationYear());
            orderItem.setPublisher(bookCopy.getPublisher());
            orderItem.setSellPrice(bookCopy.getSellPrice());
            orderItems.add(orderItem);

            orderItem.getBook().setQuantityInStock(orderItem.getBook().getQuantityInStock() - 1);
            bookRepository.save(orderItem.getBook());
            orderItemRepository.save(orderItem);
        }
        return orderItems;
    }
}
