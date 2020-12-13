package com.termproject.bookstore.controllers;

import com.termproject.bookstore.models.Promotion;
import com.termproject.bookstore.models.Promotion;
import com.termproject.bookstore.models.User;
import com.termproject.bookstore.service.PromotionService;
import com.termproject.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;

@Controller
public class PromotionController {

    @Autowired
    PromotionService promotionService;
    UserService userService;

    private JavaMailSender emailSender;

    public PromotionController(JavaMailSender emailSender) {
        this.emailSender=emailSender;
    }

    @RequestMapping(value = "/manage-promotions", method = RequestMethod.GET)
    public String showPromotions(Model model, HttpSession session) {

        String view = "redirect:/access-denied";
        User user = (User)session.getAttribute("loggedInUser");
        if (userService.checkAdmin(user) && user != null) {
            model.addAttribute("promotions", promotionService.getPromotions());
            view = "manage-promotions";
        }
        return view;
    }

    @RequestMapping(value = {"/showAddPromotionForm", "/add-promotion"}, method = RequestMethod.GET)
    public String showAddPromotionForm(Model model, HttpSession session){

        String view = "redirect:/access-denied";
        User user = (User)session.getAttribute("loggedInUser");
        if (userService.checkAdmin(user) && user != null) {
            Promotion promotion = new Promotion();
            model.addAttribute("promotion", promotion);
            view = "add-promotion";
        }
        return view;
    }

    @RequestMapping(value = "/addPromotion", method = RequestMethod.POST)
    public String addPromotion(@RequestParam("promoCode") String promoCode,
                               @RequestParam("percentage") String percentage,
                               @RequestParam("expirationDate") String expirationDate,
                               Model model) {
        String view = "manage-promotions";

        if(promotionService.getPromotionByPromoCode(promoCode) != null) {
            model.addAttribute("promotionError", "That promotion already exists");
        }


        Promotion promotion = new Promotion();
        promotion.setPromoCode(promoCode);
        promotion.setPercentage(Integer.parseInt(percentage));
        promotion.setStartDate(LocalDate.now()));
        promotion.setExpirationDate(Date.valueOf(expirationDate));
        promotionService.add(promotion);
        sendPromotion(promotion);
        return view;
    }

    /**
     *  <p>Sends the promotion to all subscribed users</p>
     * @param promotion the promotion to send
     */
    private void sendPromotion(Promotion promotion){

        SimpleMailMessage message = new SimpleMailMessage();

        for (User user: userService.getUsers()) {
            if(user.isSubscribed()){
                String text = "Hello " + user.getFirstName() + "!\n\n" +
                        "Use promo-code " + promotion.getPromoCode() +
                        " to get "+ promotion.getPercentage() + "% off your next purchase!\n\n" +
                        "(Promotion valid through " + promotion.getExpirationDate().toString() + ")";
                message.setFrom("bigbrainbookstore@gmail.com");
                message.setTo(user.getEmail());
                message.setSubject("New Promotion");
                message.setText(text);
                emailSender.send(message);
            }
        }
    }

}