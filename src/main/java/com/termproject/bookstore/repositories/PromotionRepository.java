package com.termproject.bookstore.repositories;

import com.termproject.bookstore.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    Promotion findByPromoCode(String promoCode);
}
