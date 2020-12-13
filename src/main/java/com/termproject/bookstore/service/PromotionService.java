package com.termproject.bookstore.service;

import com.termproject.bookstore.models.Promotion;
import com.termproject.bookstore.repositories.PromotionRepository;
import com.termproject.bookstore.repositories.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    public Promotion add(Promotion promotion){
        return promotionRepository.save(promotion);
    }

    public List<Promotion> savePromotions(List<Promotion> promotions){
        return promotionRepository.saveAll(promotions);
    }

    public Promotion getPromotionByPromoCode(String promoCode) {
        Promotion promotion = promotionRepository.findByPromoCode(promoCode);
        if(promotion == null){
            System.out.println("Could not find promotion");
        }
        return promotion;
    }

    public List<Promotion> getPromotions(){
        return promotionRepository.findAll();
    }

}
