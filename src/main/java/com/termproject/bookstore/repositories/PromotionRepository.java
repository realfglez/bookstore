package com.termproject.bookstore.repositories;

import com.termproject.bookstore.models.Promotion;

import org.springframework.data.repository.CrudRepository;

public interface PromotionRepository extends CrudRepository<Promotion, String>{

    Promotion findByPromoCode(String promoCode);
}
