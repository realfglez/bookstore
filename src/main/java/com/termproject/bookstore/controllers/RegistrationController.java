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

import java.util.Random;

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

        String view = "verify-user";
        User user = new User();
        boolean formErrors = false;

        if (userService.getUserByEmail(userForm.getEmail()) != null) {
            model.addAttribute("userExists", "That email is already linked to an account");
            formErrors = true;
            view = "registration";
        }

        if (userService.getUserByUsername(userForm.getUsername()) != null) {
            model.addAttribute("userExists", "That username is taken");
            formErrors = true;
            view = "registration";
        }

        if (!(userForm.getConfirmPassword().equals(user.getPassword()))) {
            model.addAttribute("passwordMismatch", "The passwords do not match");
            formErrors = true;
            view = "registration";
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
            }
            else {
                model.addAttribute("dateError", "Invalid expiration date");
                formErrors = true;
                view = "registration";
            }
        }

        if (!userForm.getCard().getSecurityCode().isBlank()) {
            if ((userForm.getCard().getSecurityCode().length() == 3) ||
                    (userForm.getCard().getSecurityCode().length() == 4)) {
                user.getCard().setSecurityCode(userForm.getCard().getSecurityCode());
            }
            model.addAttribute("codeError", "Invalid security code format");
            formErrors = true;
            view = "registration";
        }
        if (!formErrors){
            user.setPassword(hasher().hashPassword(userForm.getPassword()));
            user.setRole(Role.valueOf("USER"));
            userService.save(user);
            model.addAttribute("verifyUser", user);
            sendVerification(user);
        }
        return view;
    }

    /**
     * <p>Sends the verification code to the user</p>
     * @param userForm the user to email
     */
    private void sendVerification(User userForm) {

        Random random = new Random();
        int randomCode = random.nextInt(9999) + random.nextInt(9999);
        userForm.setVerificationCode(Integer.toString(randomCode));
        userService.save(userForm);
        SimpleMailMessage message = new SimpleMailMessage();

        String text = "Hello " + userForm.getFirstName() + "!\n\n" +
                "Please use the following code to verify your account: " + userForm.getVerificationCode();

        message.setFrom("bigbrainbookstore.com");
        message.setTo(userForm.getEmail());
        message.setSubject("Verify Your Account");
        message.setText(text);
        emailSender.send(message);
    }

}
