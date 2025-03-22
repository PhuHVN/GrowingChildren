package com.example.GrowChild.service;

import com.example.GrowChild.entity.response.Membership;
import com.example.GrowChild.repository.MembershipRepository;
import com.example.GrowChild.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipService {
    @Autowired
    MembershipRepository membershipRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    public Membership getMembershipByType(String type) {
        Membership membership = membershipRepository.findByType(type);
        if (membership == null) {
            throw new RuntimeException("Membership not found!");
        }
        return membership;
    }
    public Membership createMembershipDefault() {
        if(membershipRepository.findByType("Default") != null){
            return membershipRepository.findByType("Default");
        }
        Membership membership = Membership.builder()
                .type("Default")
                .price(0)
                .description("Basic membership")
                .build();
        membershipRepository.save(membership);
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



    public String deletePackage(long id) {
        Membership membership = getMembershipById(id);
        Membership membershipDefault = membershipRepository.findByType("Default");

        userService.getUserByMembershipType(membership.getType()).forEach(user -> {
            user.setMembership(membershipDefault);
            userRepository.save(user);
        });

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
        if(membership.getDescription() == null){
            membership1.setDescription(membership1.getDescription());
        }
        membership1.setType(membership.getType());
        membership1.setPrice(membership.getPrice());
        membership1.setDescription(membership.getDescription());
        return membershipRepository.save(membership1);
    }




}
