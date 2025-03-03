package com.example.GrowChild.service;

import com.example.GrowChild.entity.enumStatus.MembershipType;
import com.example.GrowChild.entity.response.Membership;
import com.example.GrowChild.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {
    @Autowired
    MembershipRepository membershipRepository;

    public Membership getMembershipByType(MembershipType type) {
        Membership membership = membershipRepository.findByType(type);
        if (membership == null) {
            throw new RuntimeException("Membership not found!");
        }
        return membership;
    }

    public List<Membership> getAll() {
        return membershipRepository.findAll();
    }

    public Membership getMembershipById(long id){
        return membershipRepository.findById(id).orElseThrow(() -> new RuntimeException("Membership not found!"));
    }
    public Membership createPackage(MembershipType membershipType,double price){
       Membership membership = Membership.builder()
                .type(membershipType)
                .price(price)
                .build();
        return membershipRepository.save(membership);
    }

    public String deletePackage(MembershipType type){
        Membership membership =getMembershipByType(type);
        membershipRepository.delete(membership);
        return "Delete Successful!";
    }

}
