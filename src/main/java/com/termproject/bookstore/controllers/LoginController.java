package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.User;
import com.termproject.bookstore.service.UserService;
import com.termproject.bookstore.utility.Hasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    private Hasher hasher(){
        return new Hasher();
    }

    /**
     * <p>Shows the login page</p>
     * @return the login view name
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin() {
        return "login";
    }

    /**
     * <p>Processes the user login form</p>
     * @param id the user's id
     * @param password the user's password
     * @param model the model to bind responses
     * @return the login
     */
    @RequestMapping(value = "/loginForm", method = RequestMethod.POST)
    public String loginUser(@RequestParam("id") String id,
                            @RequestParam("password") String password,
                            HttpServletRequest httpServletRequest,
                            Model model) {
        httpServletRequest.getSession().invalidate();
        User user;
        String loginError = "Invalid credentials";
        String view = "login";

        if (userService.getUserByUsername(id) != null) {
            user = userService.getUserByUsername(id);
            if (hasher().passwordMatches(password, user.getPassword())) {
                HttpSession session = httpServletRequest.getSession(true);
                session.setAttribute("loggedInUser", user);
                user.setSessionCode(session.getId());
                view = "redirect:/";
            }
            model.addAttribute("loginError", loginError);
        } else if (userService.getUserByEmail(id) != null) {
            user = userService.getUserByEmail(id);
            if (hasher().passwordMatches(password, user.getPassword())) {
                HttpSession session = httpServletRequest.getSession(true);
                session.setAttribute("loggedInUser", user);
                user.setSessionCode(session.getId());
                view = "redirect:/";
            }
            model.addAttribute("loginError", loginError);
        } else {
            model.addAttribute("loginError", loginError);
        }
        return view;
    }
}
