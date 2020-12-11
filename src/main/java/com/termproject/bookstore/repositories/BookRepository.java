package com.termproject.bookstore.repositories;

import com.termproject.bookstore.models.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByIsbn(String isbn);
}
