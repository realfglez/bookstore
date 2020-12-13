package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.User;
import com.termproject.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showIndex(final HttpSession session, Model model){

        String view = "index";
        User loggedInUser = (User)session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            view = "login";
        }
        return view;
    }

}
