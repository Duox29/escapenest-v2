package com.escapenest.repository;

import com.escapenest.entity.PaymentReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentReceiptRepository extends JpaRepository<PaymentReceipt , Integer> {
    @Query("SELECT p FROM PaymentReceipt p ORDER BY p.id DESC")
    List<PaymentReceipt> findAllOrderByDescending();
}
