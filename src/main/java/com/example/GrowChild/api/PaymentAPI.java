package com.example.GrowChild.api;

import com.example.GrowChild.entity.response.Payment;
import com.example.GrowChild.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("payment")
public class PaymentAPI {

    @Autowired
    PaymentService paymentService;

    @GetMapping("getAllPayment")
    public List<Payment> payment(){
        return paymentService.getPayments();
    }
}
