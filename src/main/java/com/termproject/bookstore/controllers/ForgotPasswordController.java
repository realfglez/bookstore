package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.User;
import com.termproject.bookstore.service.UserService;
import com.termproject.bookstore.utility.Hasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Random;

@SessionAttributes("userForm")
@Controller
public class ForgotPasswordController {

    @Autowired
    UserService userService;

    private JavaMailSender emailSender;

    public ForgotPasswordController(JavaMailSender emailSender) {
        this.emailSender=emailSender;
    }

    private Hasher hasher(){
        return new Hasher();
    }

    @ModelAttribute("userForm")
    private User getUser() {
        return new User();
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public String showForgotPassword() {
        return "forgot-password";
    }

    @RequestMapping(value = "/forgotPasswordForm", method = RequestMethod.POST)
    public String forgotPasswordForm(@RequestParam("email") String email, Model model) {

        String view = "forgot-password";

        if (userService.getUserByEmail(email) == null) {
            model.addAttribute("emailError", "Invalid email");
        }
        else{
            User userForm = userService.getUserByEmail(email);
            sendPasswordReset(userForm);
            model.addAttribute("userForm", userForm);
            view = "verify-password-reset";
        }
        return view;
    }

    @RequestMapping(value = "/verify-password-reset", method = RequestMethod.GET)
    public String showVerifyReset() {
        return "verify-password-reset";
    }

    @RequestMapping(value = "/verifyResetPasswordCode", method = RequestMethod.POST)
    public String verifyResetPasswordCode(@RequestParam("code") String code,
                                          @ModelAttribute("userForm") User userForm,
                                          Model model) {

        String view = "verify-password-reset";

        if (!(userForm.getResetPasswordCode().compareTo(code) == 0)) {
            model.addAttribute("codeError", "Code doesn't match");
        }
        else{
            view = "reset-password";
        }
        return view;
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.GET)
    public String showResetPassword() {
        return "reset-password";
    }

    @RequestMapping(value = "/resetPasswordForm", method = RequestMethod.POST)
    public String resetPasswordForm(@ModelAttribute("userForm") User userForm,
                                    @RequestParam("password") String password,
                                    @RequestParam("confirmPassword") String confirmPassword,
                                    HttpSession session,
                                    Model model) {

        String view = "reset-password";

        if (!password.matches(confirmPassword)) {
            model.addAttribute("passwordError", "Passwords don't match");
        }
        else{
            userForm.setPassword(hasher().hashPassword(password));
            userService.save(userForm);
            view = "login";
            session.removeAttribute("userForm");
        }
        return view;
    }

    /**
     <p>Sends the reset password code to the user</p>
     * @param userForm the user to email
     */
    public void sendPasswordReset(User userForm) {

        Random random = new Random();
        int randomCode = random.nextInt(9999) + random.nextInt(9999);
        userForm.setResetPasswordCode(Integer.toString(randomCode));
        userService.save(userForm);
        SimpleMailMessage message = new SimpleMailMessage();

        String text = "Hello " + userForm.getFirstName() + "!\n\n" +
                "Please use the following code to reset your password: " + userForm.getResetPasswordCode();

        message.setFrom("bigbrainbookstore@gmail.com");
        message.setTo(userForm.getEmail());
        message.setSubject("Reset Password");
        message.setText(text);
        emailSender.send(message);
    }

}
