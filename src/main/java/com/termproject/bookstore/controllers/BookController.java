package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.Book;
import com.termproject.bookstore.service.BookService;
import com.termproject.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    @Autowired
    BookService bookService;
    UserService userService;

    @RequestMapping(value = "/manage-books", method = RequestMethod.GET)
    public String listBooks(Model model) {

        model.addAttribute("books", bookService.getBooks());
        return "manage-books";
    }

    @RequestMapping(value = "archive/{isbn}", method = RequestMethod.GET)
    public String archiveBook(@PathVariable("isbn") String isbn, Model model) {

        Book book = bookService.getBookByIsbn(isbn);
        bookService.archiveBook(book);
        model.addAttribute("books", bookService.getBooks());
        return "manage-books";
    }

    @RequestMapping(value = "unarchive/{isbn}", method = RequestMethod.GET)
    public String unArchiveBook(@PathVariable("isbn") String isbn, Model model) {

        Book book = bookService.getBookByIsbn(isbn);
        bookService.unArchiveBook(book);
        model.addAttribute("books", bookService.getBooks());
        return "manage-books";
    }

    @RequestMapping(value = "/showAddBookForm", method = RequestMethod.GET)
    public String showAddBookForm(Model model){
        Book book = new Book();
        model.addAttribute("book", book);
        return "add-book";
    }

    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    public String addBook(@ModelAttribute("book") Book book, BindingResult result) {
        if(result.hasErrors()) {
            return "add-book";
        }

        bookService.addBook(book);
        return "redirect:/manage-books";
    }

}