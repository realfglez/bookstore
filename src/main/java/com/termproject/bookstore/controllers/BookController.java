package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.Book;
import com.termproject.bookstore.models.User;
import com.termproject.bookstore.service.BookService;
import com.termproject.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class BookController {

    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;
    @Autowired
    JavaMailSender emailSender;

    @RequestMapping(value = "/manage-books", method = RequestMethod.GET)
    public String showBooks(Model model, HttpSession session) {

        String view = "access-denied";
        User user = (User)session.getAttribute("loggedInUser");
        if (userService.checkAdmin(user) && userService.loggedIn(user, session)) {
            model.addAttribute("books", bookService.getBooks());
            view = "manage-books";
        }
        return view;
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

    @RequestMapping(value = {"/showAddBookForm", "/add-book"}, method = RequestMethod.GET)
    public String showAddBookForm(Model model, HttpSession session){

        String view = "redirect:/access-denied";
        User user = (User)session.getAttribute("loggedInUser");
        if (userService.checkAdmin(user) && user != null) {
            Book book = new Book();
            model.addAttribute("book", book);
            view = "add-book";
        }
        return view;
    }

    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    public String addBook(@ModelAttribute("book") Book book, Model model) {

        String view = "manage-books";
        if(bookService.getBookByIsbn(book.getIsbn()) != null) {
            model.addAttribute("bookError", "That book already exists");
            view = "add-book";
        }
        bookService.addBook(book);
        return view;
    }
}