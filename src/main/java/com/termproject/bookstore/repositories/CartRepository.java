package com.termproject.bookstore.repositories;


import com.termproject.bookstore.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
