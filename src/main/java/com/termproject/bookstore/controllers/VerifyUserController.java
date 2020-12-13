package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.User;
import com.termproject.bookstore.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VerifyUserController {

    @Autowired
    UserService userService;

    private JavaMailSender emailSender;

    public VerifyUserController(JavaMailSender emailSender) {
        this.emailSender=emailSender;
    }

    /**
     * <p>Shows the verification page</p>
     * @return the verify-user view name
     */
    @RequestMapping(value = "/verify-user", method = RequestMethod.GET)
    public String showVerification(){
        return "verify-user";
    }

    /**
     * <p>Processes the user's verification form</p>
     * @param verificationCode the user's verification code
     * @param model the model to bind responses
     * @return the verify-success view on success, or the verify-user view on failure
     */
    @RequestMapping(value = "/verify-user", method = RequestMethod.POST)
    public String verifyUser(@RequestParam("verificationCode") String verificationCode, Model model){

        User user = userService.getUserByVerificationCode(verificationCode);
        String view = "verify-user";

        if (user == null) {
            model.addAttribute("verifyError", "Code is incorrect");
        }
        else {
            user.setActive(true);
            userService.save(user);
            view = "verify-success";
        }
        return view;
    }
}
