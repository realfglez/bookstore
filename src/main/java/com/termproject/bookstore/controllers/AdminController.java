package com.termproject.bookstore.controllers;

import com.termproject.bookstore.repositories.AdminRepository;
import com.termproject.bookstore.models.Admin;

import com.termproject.bookstore.validation.Hasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @RequestMapping(value = "/admin-login", method = RequestMethod.GET)
    public String showAdminLogin(Model model) {
        model.addAttribute("adminLogin", new Admin());
        return "admin-login";
    }

    @RequestMapping(value = "adminLogin", method = RequestMethod.POST)
    public String submitLogin(@ModelAttribute("adminLogin") Admin adminLogin, Model model) {

        Admin adminInstance = adminRepository.findByAdminID(adminLogin.getId());

        Hasher hasher = new Hasher();
        String hashedPW = hasher.hashPassword(adminInstance.getAdminPW());

        if (!(hashedPW.matches(adminLogin.getAdminPW()))) {
            System.out.println("Email / Password does not exist");
            System.out.println(adminInstance);
            return "admin-login";
        }

        model.addAttribute("admin", adminInstance);
        return "admin-home";
    }
}
