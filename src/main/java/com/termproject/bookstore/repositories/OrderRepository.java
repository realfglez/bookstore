package com.termproject.bookstore.repositories;

import com.termproject.bookstore.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByConfirmationCode(String confirmationCode);
}
