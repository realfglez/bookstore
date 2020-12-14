package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.BookCopy;
import com.termproject.bookstore.models.Cart;
import com.termproject.bookstore.models.User;
import com.termproject.bookstore.service.BookCopyService;
import com.termproject.bookstore.service.CartService;
import com.termproject.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class CartController {

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    BookCopyService bookCopyService;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String showCart(HttpSession session, Model model) {

        String view = "/access-denied";
        User user = (User) session.getAttribute("loggedInUser");
        if (userService.loggedIn(user, session)) {
            if (user.getCart() == null) {
                Cart cart = new Cart();
                user.setCart(cart);
            }
            else {
                model.addAttribute("cart", user.getCart());
                view = "cart";
            }
        }
        return view;
    }

    @RequestMapping(value = "/remove/{isbn}", method = RequestMethod.GET)
    public String removeItem(@PathVariable("isbn") String isbn,
                             HttpSession session,
                             Model model) {

        Cart cart = ((User)session.getAttribute("loggedInUser")).getCart();
        cartService.removeItem(cart, isbn);
        return "cart";
    }

    @RequestMapping(value = "/add/{isbn}", method = RequestMethod.GET)
    public String addItem(@PathVariable("isbn") String isbn,
                             HttpSession session,
                             Model model) {

        Cart cart = ((User)session.getAttribute("loggedInUser")).getCart();
        BookCopy bookCopy = bookCopyService.getSingleBookCopy(isbn);
        if (!bookCopy.getBook().isArchived()){
            cartService.addItem(cart, bookCopy);
        }
        return "cart";
    }

}
