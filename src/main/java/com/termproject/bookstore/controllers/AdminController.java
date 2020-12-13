package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.Book;
import com.termproject.bookstore.models.Promotion;
import com.termproject.bookstore.models.User;
import com.termproject.bookstore.service.BookService;
import com.termproject.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    BookService bookService;
    UserService userService;

    private JavaMailSender emailSender;

    public AdminController(JavaMailSender emailSender) {
        this.emailSender=emailSender;
    }

    @RequestMapping(value = "/manage-books", method = RequestMethod.GET)
    public String listBooks(Model model, HttpSession session) {

        User admin = (User)session.getAttribute("loggedInUser");

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

    /**
     *  <p>Sends the promotion to all subscribed users</p>
     * @param promotion the promotion to send
     */
    private void sendPromotion(Promotion promotion){

        SimpleMailMessage message = new SimpleMailMessage();

        for (User user: userService.getUsers()) {
            if(user.isSubscribed()){

                String text = "Hello " + user.getFirstName() + "!\n\n" +
                        "Use promo-code " + promotion.getPromoCode() +
                        " to get "+ promotion.getPercentage() + "% off your next purchase!\n\n" +
                        "(Promotion valid through " + promotion.getExpirationDate() + ")";

                message.setFrom("bigbrainbookstore@gmail.com");
                message.setTo(user.getEmail());
                message.setSubject("New Promotion");
                message.setText(text);
                emailSender.send(message);
            }
        }
    }

}