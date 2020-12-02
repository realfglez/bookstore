package com.termproject.bookstore.repositories;

import com.termproject.bookstore.models.Book;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, String> {

    Book findByIsbn(Long isbn) throws IllegalArgumentException;
}
