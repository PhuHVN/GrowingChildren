package com.example.GrowChild.service;

import com.example.GrowChild.entity.response.Membership;
import com.example.GrowChild.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {
    @Autowired
    MembershipRepository membershipRepository;

    public Membership getMembershipByType(String type) {
        Membership membership = membershipRepository.findByType(type);
        if (membership == null) {
            throw new RuntimeException("Membership not found!");
        }
        return membership;
    }

    public List<Membership> getAll() {
        return membershipRepository.findAll();
    }

    public Membership getMembershipById(long id) {
        return membershipRepository.findById(id).orElseThrow(() -> new RuntimeException("Membership not found!"));
    }

    public Membership createPackage(Membership membershipRequest) {
        Membership membership = Membership.builder()
                .type(membershipRequest.getType())
                .price(membershipRequest.getPrice())
                .description(membershipRequest.getDescription())
                .build();
        return membershipRepository.save(membership);
    }

    public String deletePackage(String type) {
        Membership membership = getMembershipByType(type);
        membershipRepository.delete(membership);
        return "Delete Successful!";
    }

    public Membership updatePackage(long id, Membership membership) {
        Membership membership1 = getMembershipById(id);
        if(membership.getType() == null){
            membership1.setType(membership1.getType());
        }
        if(membership.getPrice() == 0){
            membership1.setPrice(membership1.getPrice());
        }
        membership1.setType(membership.getType());
        membership1.setPrice(membership.getPrice());
        return membershipRepository.save(membership1);
    }




}
