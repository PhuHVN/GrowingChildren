package com.example.GrowChild.repository;

import com.example.GrowChild.entity.enumStatus.MembershipType;
import com.example.GrowChild.entity.response.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Membership findByType(String type);
}
