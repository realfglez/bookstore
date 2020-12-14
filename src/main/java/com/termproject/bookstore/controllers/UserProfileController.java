package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.Role;
import com.termproject.bookstore.models.User;
import com.termproject.bookstore.service.UserService;
import com.termproject.bookstore.utility.Hasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UserProfileController {

    @Autowired
    UserService userService;

    private Hasher hasher() {
        return new Hasher();
    }

    @RequestMapping(value = "/edit-profile", method = RequestMethod.GET)
    public String showEditProfile(Model model, HttpSession session) {

        String view = "/access-denied";
        User user = (User) session.getAttribute("loggedInUser");
        if (userService.loggedIn(user, session)) {
            User useForm = new User();
            model.addAttribute("useForm", useForm);
            view = "edit-profile";
        }
        return view;
    }

    @RequestMapping(value = "/editProfileForm", method = RequestMethod.POST)
    public String editProfileForm(@ModelAttribute("userForm") User userForm, Model model, HttpSession session) {

        String view = "profile";
        User user = new User();
        boolean formErrors = false;

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

        if (!userForm.getStreet().isBlank()) {
            user.setStreet(userForm.getStreet());
        }

        if (!userForm.getCity().isBlank()) {
            user.setCity(userForm.getCity());
        }

        if (!userForm.getState().isBlank()) {
            user.setState(userForm.getState());
        }

        if (!userForm.getZip().isBlank()) {
            user.setZip(userForm.getZip());
        }

        if (!userForm.getCardNumber().isBlank()) {
            user.setCardNumber(hasher().hashCardNumber(userForm.getCardNumber()));
        }

        if (!userForm.getExpirationDate().isBlank()) {
            if (userForm.getExpirationDate().matches("\\d\\d/\\d\\d\\d\\d")) {
                user.setExpirationDate(userForm.getExpirationDate());
            }
            else {
                model.addAttribute("dateError", "Invalid expiration date");
                formErrors = true;
                view = "edit-profile";
            }
        }

        if (!userForm.getSecurityCode().isBlank()) {
            if ((userForm.getSecurityCode().length() == 3) ||
                    (userForm.getSecurityCode().length() == 4)) {
                user.setSecurityCode(userForm.getSecurityCode());
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

        User user = (User) session.getAttribute("loggedInUser");
        if (userService.loggedIn(user, session)) {
            model.addAttribute("user", user);
            view = "profile";
        }
        return view;
    }

    @RequestMapping(value = "/edit-password", method = RequestMethod.GET)
    public String showEditPassword(HttpSession session, Model model) {

        String view = "access-denied";

        User user = (User) session.getAttribute("loggedInUser");
        if (userService.loggedIn(user, session)) {
            model.addAttribute("user", user);
            view = "edit-password";
        }
        return view;
    }

    @RequestMapping(value = "/editPasswordForm", method = RequestMethod.POST)
    public String editPasswordForm(@ModelAttribute("user") User user,
                                   @RequestParam("oldPassword") String oldPassword,
                                   @RequestParam("newPassword") String newPassword,
                                   @RequestParam("confirmPassword") String confirmPassword,
                                   Model model) {

        String view = "profile";
        boolean formErrors = false;

        if (!hasher().passwordMatches(oldPassword, user.getPassword())){
            model.addAttribute("passwordError", "Password incorrect");
            view = "edit-password";
            formErrors = true;
        }

        if (!newPassword.equals(confirmPassword)){
            model.addAttribute("passwordMismatch", "Passwords don't match");
            view = "edit-password";
            formErrors = true;
        }

        if (!formErrors){
            user.setPassword(hasher().hashPassword(newPassword));
            userService.save(user);
        }
        return view;
    }

}
