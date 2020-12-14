package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.*;
import com.termproject.bookstore.service.CartService;
import com.termproject.bookstore.service.OrderService;
import com.termproject.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

@Controller
public class CheckoutController {

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    JavaMailSender mailSender;

    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String showCheckout(HttpSession session, Model model){
        String view = "/access-denied";
        User user = (User) session.getAttribute("loggedInUser");
        if (userService.loggedIn(user, session)) {
            if (!user.getCart().getBookCopies().isEmpty()) {

                model.addAttribute("checkoutForm", user);

                if (!userService.hasAddress(user)) {
                    model.addAttribute("noAddress", true);
                }

                if (!userService.hasCard(user)) {
                    model.addAttribute("noCard", true);
                }

                view = "checkout";
            }
            else {
                view = "cart";
            }
        }

        return view;
    }

    @RequestMapping(value = "/checkoutForm", method = RequestMethod.POST)
    public String checkoutForm(@ModelAttribute("checkoutForm") User checkoutForm,
                               HttpSession session,
                               Model model) {

        String view = "checkout";
        boolean itemChange = false;
        Cart cart = checkoutForm.getCart();

        for (BookCopy bookCopy:cart.getBookCopies()) {
            if(bookCopy.getBook().isArchived()) {
                model.addAttribute("itemGone", "The following item is no longer available: " +
                        bookCopy.getTitle());
                itemChange = true;
            }
        }

        if(!itemChange) {
            Order order = orderService.placeOrder(cart);
            sendConfirmation(order);
        }

        return view;
    }

    private void sendConfirmation(Order order) {

        SimpleMailMessage message = new SimpleMailMessage();
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.UP);

        User user = order.getUser();

        String itemList = "";
        for (OrderItem item:order.getItems()) {
            String title = item.getTitle();
            if (!itemList.contains(title)) {
                itemList += title + ", ";
            }
        }

        String text = "Hello " + user.getFirstName() + ",\n\n" +
                "The following serves as confirmation of your order: \n" +
                "\nCustomer: " + user.getFirstName() + user.getLastName() +
                "\nConfirmation code: " + order.getConfirmationCode() +
                "\nOrder ID: " + order.getId() +
                "\nShipping address: " + user.getStreet() + user.getCity() +
                user.getState() + user.getState() + user.getZip() +
                "\nItems: " + itemList +
                "\nTotal: $" + df.format(order.getPrice()) +
                "\nOrder completed at " + order.getDateTime().toString();

        message.setFrom("bigbrainbookstore.com");
        message.setTo(order.getUser().getEmail());
        message.setSubject("Order Confirmation");
        message.setText(text);
        mailSender.send(message);
    }
}
