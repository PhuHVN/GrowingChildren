package com.example.GrowChild.entity.respone;

import com.example.GrowChild.entity.enumStatus.MembershipType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long membershipId;

    @Enumerated(EnumType.STRING)
    private MembershipType type; // DEFAULT, LIFETIME

    double price;







}
