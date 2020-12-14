package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.Order;
import com.termproject.bookstore.models.User;
import com.termproject.bookstore.service.CartService;
import com.termproject.bookstore.service.OrderService;
import com.termproject.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @RequestMapping(value = "/order-history", method = RequestMethod.GET)
    public String getOrderHistory(HttpSession session, Model model) {

        String view = "access-denied";

        User user = (User) session.getAttribute("loggedInUser");
        if (userService.loggedIn(user, session)) {
            model.addAttribute("orders", orderService.getOrders(user));
            view = "order-history";
        }

        return view;
    }

    @RequestMapping(value = "/reorder{confirmationNumber}", method = RequestMethod.GET)
    public String reorder(@PathVariable("confirmationNumber") String confirmationNumber,
                          HttpSession session, Model model) {

        Order order = orderService.getOrderByConfirmationCode(confirmationNumber);
        cartService.orderToCart(order);

        return "cart";
    }
}
