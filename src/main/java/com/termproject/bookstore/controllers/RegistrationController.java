package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.Role;
import com.termproject.bookstore.models.User;
import com.termproject.bookstore.service.UserService;
import com.termproject.bookstore.utility.Hasher;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    private JavaMailSender emailSender;

    public RegistrationController(JavaMailSender emailSender) {
        this.emailSender=emailSender;
    }

    private Hasher hasher(){
        return new Hasher();
    }

    /**
     * <p>Shows the registration page.</p>
     * @param model the model to bind a new User
     * @return the registration view name
     */
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    /**
     * <p>Processes the user registration form</p>
     * @param userForm the User information
     * @param model the model to bind responses
     * @return the verification view on success, registration on failure
     */
    @RequestMapping(value = "/registerNewUser", method = RequestMethod.POST)
    public String registerNewUser(@ModelAttribute("userForm") User userForm, Model model) {

        User user = userForm;
        if (userService.getUserByEmail(user.getEmail()) != null) {
            model.addAttribute("userExists", "That email is already linked to an account");
        }
        if (!(userForm.getConfirmPassword().equals(user.getPassword()))) {
            model.addAttribute("passwordMismatch", "The passwords do not match");
        }
        if ((userService.getUserByEmail(user.getEmail()) == null)
                && (user.getConfirmPassword().equals(user.getPassword()))){

            user.setPassword(hasher().hashPassword(userForm.getPassword()));
            user.setRole(Role.valueOf("USER"));
            userService.save(user);
            sendVerification(user);
            return "verify-user";
        }
        return "registration";
    }

    /**
     * <p>Sends the verification code to the user</p>
     * @param userForm the user to email
     */
    private void sendVerification(User userForm) {

        String newVerificationCode = RandomStringUtils.random(20);
        userForm.setVerificationCode(newVerificationCode);
        userService.save(userForm);
        SimpleMailMessage message = new SimpleMailMessage();

        String text = "Hello " + userForm.getFirstName() + "!\n\n" +
                "Please use the following code to verify your account: " + newVerificationCode;

        message.setFrom("bigbrainbookstore.com");
        message.setTo(userForm.getEmail());
        message.setSubject("Confirm Your Account");
        message.setText(text);
        emailSender.send(message);
    }

}
