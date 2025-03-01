package com.example.GrowChild.entity.request;

import com.example.GrowChild.entity.enumStatus.MembershipType;
import lombok.Data;

@Data
public class PaymentRequest {
    private String userId;
    private MembershipType membershipType;
    private double price;
}
