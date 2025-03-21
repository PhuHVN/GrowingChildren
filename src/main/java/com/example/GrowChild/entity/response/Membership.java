package com.example.GrowChild.entity.response;

import com.example.GrowChild.entity.enumStatus.MembershipType;
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
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long membershipId;

    private String type; // DEFAULT, PREMIUM

    double price;

    String description;

}
