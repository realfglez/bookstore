package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.User;
import com.termproject.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
    @RequestMapping(value = "/verifyUser", method = RequestMethod.POST)
    public String verifyUser(HttpSession session,
                             @RequestParam("verificationCode") String verificationCode, Model model){

        String view = "verify-success";
        User verifyUser = (User)session.getAttribute("loggedInUser");

        if (!verifyUser.getVerificationCode().equals(verificationCode)) {
            model.addAttribute("verifyError", "Code doesn't match");
            view = "verify-user";
        }
        
        else {
            verifyUser.setActive(true);
            userService.save(verifyUser);
        }
        return view;
    }
}
