package com.termproject.bookstore.repositories;


import com.termproject.bookstore.models.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCopyRepository extends JpaRepository<BookCopy, Integer> {
    BookCopy findById(int id);
    BookCopy findByIsbn(String isbn);
}
