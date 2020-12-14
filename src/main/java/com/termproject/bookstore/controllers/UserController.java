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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Random;

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

    @RequestMapping(value = {"/showAddEmployeeForm", "/add-employee"}, method = RequestMethod.GET)
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

        User userForm = newUser;
        if((userService.getUserByEmail(newUser.getEmail()) != null) ||
                (userService.getUserByEmail(newUser.getUsername()) != null)) {
            model.addAttribute("userError", "That user already exists");
        }

        if (!(newUser.getConfirmPassword().equals(newUser.getPassword()))) {
            model.addAttribute("passwordMismatch", "The passwords do not match");
        }
        if ((userService.getUserByEmail(newUser.getEmail()) == null) &&
                (userService.getUserByUsername(newUser.getUsername()) == null) &&
                (newUser.getConfirmPassword().equals(newUser.getPassword()))){
            userForm.setPassword(hasher().hashPassword(newUser.getPassword()));
            userForm.setRole(Role.valueOf("EMPLOYEE"));
            userService.save(userForm);
            sendEmployeeVerification(userForm);
            model.addAttribute("verifyUser", userForm);
            return "verify-user";
        }
        return view;
    }

    /**
     * <p>Sends the verification code to the employee</p>
     * @param employee the employee to email
     */
    private void sendEmployeeVerification(User employee) {

        Random random = new Random();
        int randomCode = random.nextInt(9999) + random.nextInt(9999);
        employee.setVerificationCode(Integer.toString(randomCode));
        userService.save(employee);
        SimpleMailMessage message = new SimpleMailMessage();

        String text = "Hello " + employee.getFirstName() + "!\n\n" +
                "Please use the following code to verify your account: " + employee.getVerificationCode();

        message.setFrom("bigbrainbookstore.com");
        message.setTo(employee.getEmail());
        message.setSubject("Verify Your Account");
        message.setText(text);
        emailSender.send(message);
    }
}