package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.Book;
import com.termproject.bookstore.models.Status;
import com.termproject.bookstore.repositories.UserRepository;
import com.termproject.bookstore.models.User;
import com.termproject.bookstore.validation.Hasher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String goHome()
    {
        return "index";
    }

    @RequestMapping(value="/user-admin", method = RequestMethod.GET)
    public String choseUserOrAdmin() {return "user-admin";}

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(Model model) {
        model.addAttribute("loginForm", new User());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submitLogin(@ModelAttribute("loginForm") User userForm, Model model) {

        User userInstance = userRepository.findByUserName(userForm.getUserName());

        Hasher hasher = new Hasher();
        String hashedPW = hasher.hashPassword(userInstance.getUserPW());

        if (userInstance == null || !(hashedPW.matches(userForm.getUserPW()))) {
            System.out.println("Email / Password does not exist");
            System.out.println(userInstance);
            return "login";
        }

        return "index";
    }

    @GetMapping("manage-users")
    public String listUsers(Model model) {

        model.addAttribute("books", userRepository.findAll());
        return "manage-users";
    }


    @GetMapping("/suspend/{id}")
    public String suspendUser(@PathVariable("id") String userName, Model model) {

        User user = userRepository.findByUserName(userName);

        user.setStatus(Status.SUSPENDED);
        userRepository.save(user);

        model.addAttribute("users", userRepository.findAll());
        return "manage-users";
    }

    @GetMapping("/activate/{id}")
    public String activateUser(@PathVariable("id") String userName, Model model) {

        User user = userRepository.findByUserName(userName);

        user.setStatus(Status.ACTIVE);
        userRepository.save(user);

        model.addAttribute("users", userRepository.findAll());
        return "manage-users";
    }

    @GetMapping("/promote/{id}")
    public String promoteUser(@PathVariable("id") String userName, Model model) {

        User user = userRepository.findByUserName(userName);

        user.setIsEmployee(true);
        userRepository.save(user);

        model.addAttribute("users", userRepository.findAll());
        return "manage-users";
    }

    @GetMapping("/demote/{id}")
    public String demoteUser(@PathVariable("id") String userName, Model model) {

        User user = userRepository.findByUserName(userName);

        user.setIsEmployee(false);
        userRepository.save(user);

        model.addAttribute("users", userRepository.findAll());
        return "manage-users";
    }

}
