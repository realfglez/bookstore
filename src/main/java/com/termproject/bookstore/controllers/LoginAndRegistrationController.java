package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.User;
import com.termproject.bookstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginAndRegistrationController {

    @Autowired
    private UserRepository userRepository;
    BCryptPasswordEncoder getPasswordEncoder;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registerNewUser", method = RequestMethod.POST)
    public String registerNewUser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Error in registration");
            return "registration";
        }
        userForm.setPassword(getPasswordEncoder.encode(userForm.getPassword()));
        userForm.setRole("USER");
        userRepository.save(userForm);
        System.out.println("in registerNewUser (LoginAndRegistrationController)");
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username or password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
}
