package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.Book;
import com.termproject.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(value = "/manage-books", method = RequestMethod.GET)
    public String listBooks(Model model) {

        model.addAttribute("books", bookRepository.findAll());
        return "manage-books";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long isbn, Model model) {
        Book book = bookRepository.findByIsbn(isbn);

        model.addAttribute("book", book);
        return "update-book";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable("id") String isbn, Book book,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setIsbn(isbn);
            return "update-book";
        }

        bookRepository.save(book);
        model.addAttribute("books", bookRepository.findAll());
        return "redirect:/manage-books";
    }

    @GetMapping("/archive/{id}")
    public String archiveBook(@PathVariable("id") String isbn, Model model) {

        Book book = bookRepository.findByIsbn(isbn);

        book.setIsArchived(true);
        bookRepository.save(book);

        model.addAttribute("books", bookRepository.findAll());
        return "manage-books";
    }

    @PostMapping("/addbook")
    public String addBook(Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-book";
        }

        bookRepository.save(book);
        model.addAttribute("books", bookRepository.findAll());
        return "redirect:/manage-books";
    }

}