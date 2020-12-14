package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.Role;
import com.termproject.bookstore.models.User;
import com.termproject.bookstore.service.UserService;
import com.termproject.bookstore.utility.Hasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    private JavaMailSender emailSender;

    public UserController(JavaMailSender emailSender) {
        this.emailSender=emailSender;
    }

    private Hasher hasher(){
        return new Hasher();
    }

    @RequestMapping(value = "/manage-users", method = RequestMethod.GET)
    public String showUsers(Model model, HttpSession session) {

        String view = "/access-denied";
        User user = (User)session.getAttribute("loggedInUser");
        if (userService.checkAdmin(user) && user != null) {
            model.addAttribute("users", userService.getUsers());
            view = "manage-users";
        }
        return view;
    }

    @RequestMapping(value = "/add-employee", method = RequestMethod.GET)
    public String showAddEmployeeForm(Model model, HttpSession session){

        String view = "/access-denied";
        User user = (User)session.getAttribute("loggedInUser");
        if (userService.checkAdmin(user) && user != null) {
            User newUser = new User();
            model.addAttribute("newUser", newUser);
            view = "add-employee";
        }
        return view;
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public String addEmployee(@ModelAttribute("newUser") User newUser, Model model) {

        String view = "manage-users";
        boolean formErrors = false;

        User userForm = newUser;

        if((userService.getUserByEmail(newUser.getEmail()) != null) ||
                (userService.getUserByEmail(newUser.getUsername()) != null)) {
            model.addAttribute("userError", "That user already exists");
            formErrors = true;
            view = "add-employee";
        }

        if (!(newUser.getConfirmPassword().equals(newUser.getPassword()))) {
            model.addAttribute("passwordMismatch", "The passwords do not match");
            formErrors = true;
            view = "add-employee";
        }

        if (!formErrors){
            userForm.setPassword(hasher().hashPassword(newUser.getPassword()));
            userForm.setRole(Role.valueOf("EMPLOYEE"));
            userForm.setActive(true);
            userService.save(userForm);
            model.addAttribute("verifyUser", userForm);
        }
        return view;
    }
}