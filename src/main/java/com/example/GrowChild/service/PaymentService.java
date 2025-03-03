package com.example.GrowChild.service;

import com.example.GrowChild.entity.enumStatus.PaymentStatus;
import com.example.GrowChild.entity.response.Membership;
import com.example.GrowChild.entity.response.Payment;
import com.example.GrowChild.entity.response.User;
import com.example.GrowChild.repository.PaymentRepository;
import com.example.GrowChild.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    UserService userService;
    @Autowired
    MembershipService membershipService;
    @Autowired
    UserRepository userRepository;

    public void updatePaymentStatus(String transactionId,String userId,String date,long membershipId,double price,PaymentStatus status) {
        User user = userService.getUser(userId);
        Membership membership = membershipService.getMembershipById(membershipId);
        Payment payment = Payment.builder()
                .paymentDate(date)
                .user(user)
                .status(status)
                .transactionId(transactionId)
                .price(price/100)
                .membership(membership)
                .build();

        paymentRepository.save(payment);
        if(status.equals(PaymentStatus.SUCCESS)){
            user.setMembership(membership);
            userRepository.save(user);
        }

    }
}
