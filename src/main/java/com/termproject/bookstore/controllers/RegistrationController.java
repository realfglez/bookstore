package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.Role;
import com.termproject.bookstore.models.User;
import com.termproject.bookstore.service.UserService;
import com.termproject.bookstore.utility.Hasher;
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
            model.addAttribute("emailError", "That email is already linked to an account");
            formErrors = true;
            view = "registration";
        }
        else {
            user.setEmail(userForm.getEmail());
        }

        if (userService.getUserByUsername(userForm.getUsername()) != null) {
            model.addAttribute("usernameError", "That username is taken");
            formErrors = true;
            view = "registration";
        }
        else {
            user.setUsername(userForm.getUsername());
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
                view = "registration";
            }
        }

        if (!userForm.getSecurityCode().isBlank()) {
            if ((userForm.getSecurityCode().length() == 3) ||
                    (userForm.getSecurityCode().length() == 4)) {
                user.setSecurityCode(userForm.getSecurityCode());
            }
            else {
                model.addAttribute("codeError", "Invalid security code format");
                formErrors = true;
                view = "registration";
            }
        }
        if (!formErrors){
            user.setPassword(hasher().hashPassword(userForm.getPassword()));
            user.setFirstName(userForm.getFirstName());
            user.setLastName(userForm.getLastName());
            user.setPhoneNumber(userForm.getPhoneNumber());
            user.setRole(Role.valueOf("ADMIN"));
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
