package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.Role;
import com.termproject.bookstore.models.User;
import com.termproject.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class UserProfileController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/edit-profile", method = RequestMethod.GET)
    public String showEditProfile(Model model, HttpSession session) {

        String view = "/access-denied";
        User user = (User)session.getAttribute("loggedInUser");
        if (userService.loggedIn(user, session)) {
            User useForm = new User();
            model.addAttribute("useForm", useForm);
            view = "edit-profile";
        }
        return view;
    }

    @RequestMapping(value = "/editProfileForm", method = RequestMethod.POST)
    public String editProfilForm(@ModelAttribute("userForm") User userForm, Model model, HttpSession session) {

        String view = "index";
        User user = new User();
        boolean formErrors = false;
        User loggedInUser = (User)session.getAttribute("loggedInUser");

        if (!userForm.getEmail().isBlank()) {
            if (userService.getUserByEmail(userForm.getEmail()) == null) {
                user.setEmail(userForm.getEmail());
            } else {
                model.addAttribute("emailError", "That email address is already associated with an account");
                formErrors = true;
                view = "edit-profile";
            }
        }

        if (!userForm.getUsername().isBlank()) {
            if (userService.getUserByUsername(userForm.getUsername()) == null) {
                user.setUsername(userForm.getUsername());
            } else {
                model.addAttribute("usernameError", "That username is taken");
                formErrors = true;
                view = "edit-profile";
            }
        }

        if (!userForm.getFirstName().isBlank()) {
            user.setFirstName(userForm.getFirstName());
        }

        if (!userForm.getLastName().isBlank()) {
            user.setLastName(userForm.getLastName());
        }

        if (!userForm.getPhoneNumber().isBlank()) {
            user.setPhoneNumber(userForm.getPhoneNumber());
        }

        if (userForm.isSubscribed()) {
            user.setSubscribed(true);
        } else {
            user.setSubscribed(false);
        }

        if (!userForm.getAddress().getStreet().isBlank()) {
            user.getAddress().setStreet(userForm.getAddress().getStreet());
        }

        if (!userForm.getAddress().getCity().isBlank()) {
            user.getAddress().setCity(userForm.getAddress().getCity());
        }

        if (!userForm.getAddress().getState().isBlank()) {
            user.getAddress().setState(userForm.getAddress().getState());
        }

        if (!userForm.getAddress().getZip().isBlank()) {
            user.getAddress().setZip(userForm.getAddress().getZip());
        }

        if (!userForm.getCard().getCardNumber().isBlank()) {
            user.getCard().setCardNumber(userForm.getCard().getCardNumber());
        }

        if (!userForm.getCard().getExpirationDate().isBlank()) {
            String month = userForm.getCard().getExpirationDate().substring(0, 2);
            String year = userForm.getCard().getExpirationDate().substring(3);
            if (month.matches("12")) {
                if (year.matches("20[2-9][0-9]")) {
                    user.getCard().setExpirationDate(userForm.getCard().getExpirationDate());
                }
            } else if (month.matches("(0[1-9])|(1[1-2])")) {
                if (year.matches("20[2-9][1-9]")) {
                    user.getCard().setExpirationDate(userForm.getCard().getExpirationDate());
                }
            } else {
                model.addAttribute("dateError", "Invalid expiration date");
                formErrors = true;
                view = "edit-profile";
            }
        }

        if (!userForm.getCard().getSecurityCode().isBlank()) {
            if ((userForm.getCard().getSecurityCode().length() == 3) ||
                    (userForm.getCard().getSecurityCode().length() == 4)) {
                user.getCard().setSecurityCode(userForm.getCard().getSecurityCode());
            }
            model.addAttribute("codeError", "Invalid security code format");
            formErrors = true;
            view = "edit-profile";
        }

        if (!formErrors) {
            user.setRole(Role.valueOf("USER"));
            userService.save(user);
        }


        return view;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String showProfile(HttpSession session, Model model) {
        String view = "access-denied";

        User user = (User)session.getAttribute("loggedInUser");
        if (userService.loggedIn(user, session)){
            model.addAttribute("user", user);
            view = "profile";
        }
        return view;
    }




}
