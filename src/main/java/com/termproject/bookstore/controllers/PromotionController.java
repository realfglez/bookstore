package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.User;
import com.termproject.bookstore.models.Promotion;
import com.termproject.bookstore.repositories.PromotionRepository;
import com.termproject.bookstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PromotionController {

    @Autowired
    PromotionRepository promotionRepository;
    UserRepository userRepository;
    JavaMailSender mailSender;

    @RequestMapping(value = "/manage-promotions", method = RequestMethod.GET)
    public String listPromotions(Model model) {

        model.addAttribute("promotions", promotionRepository.findAll());
        return "manage-promotions";
    }

    @PostMapping("/addpromotion")
    public String addPromotion(Promotion promotion, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-promotion";
        }

        promotionRepository.save(promotion);
        model.addAttribute("promotions", promotionRepository.findAll());
        model.addAttribute("newPromotion", promotion);
        return "redirect:/send-promotion";
    }


    @PostMapping("send-promotion")
    String sendPromotion(@ModelAttribute("newPromotion") Promotion promotion, Model model, HttpServletRequest request) {
        try {
            model.addAttribute("newPromotion", promotion);

            Promotion promotionInstance = promotionRepository.findByPromoCode(promotion.getPromoCode());
            String sender = "Big Brain Books";
            String subject = "Promotion";
            String content = "<p>Hi!</p>"
                    + "<p>Since you subscribed to receive promotions, we're here to tell you about a new chance" +
                    " to save big on your next purchase.</p>"
                    + "<p>Promo-Code:" + promotionInstance.getPromoCode() + "</p>"
                    + "<p>Percentage:" + promotionInstance.getPercentage() + "%</p>"
                    + "<p>Best wishes, </p>"
                    + "</br><p>Big Brain Books</p>";


            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("info.bigbrainbooks@gmail.com", sender);

            helper.setSubject(subject);
            helper.setText(content, true);

            Iterable<User> subUsers = userRepository.findAll();

            for (User user: subUsers) {
                if (user.getIsSubscribed()) {
                    helper.setTo(user.getEmail());
                    mailSender.send(message);
                }
            }
        }catch(Exception ex) {
            return "Error in sending promotion: "+ex;
        }
        return "manage-promotions";
    }
}