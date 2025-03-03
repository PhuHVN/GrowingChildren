package com.example.GrowChild.entity.response;


import com.example.GrowChild.entity.enumStatus.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long paymentId;

    @Enumerated(EnumType.STRING)
    PaymentStatus status; //success or fail

    String transactionId;

    double price;

    String paymentDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
     User user; // Ai thực hiện thanh toán

    @ManyToOne
    @JoinColumn(name = "membership_id", nullable = false)
     Membership membership; // Membership được mua

}
