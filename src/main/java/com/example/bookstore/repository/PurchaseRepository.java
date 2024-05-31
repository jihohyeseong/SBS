package com.example.bookstore.repository;

import com.example.bookstore.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByUserId(Long userId);

    @Query("SELECT SUM(p.book.price * p.quantity) FROM Purchase p")
    Long calculateTotalPurchaseAmount();
}
