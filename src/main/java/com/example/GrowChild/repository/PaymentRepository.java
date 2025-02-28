package com.example.GrowChild.repository;

import com.example.GrowChild.entity.respone.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Payment findByTransactionId(String transactionId);
}
