package com.termproject.bookstore.repositories;


import com.termproject.bookstore.models.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookCopyRepository extends JpaRepository<BookCopy, Integer> {
    BookCopy findByIsbn(String isbn);
}
