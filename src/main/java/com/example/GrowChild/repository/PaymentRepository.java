package com.example.GrowChild.repository;

import com.example.GrowChild.entity.response.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
