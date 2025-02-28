package com.example.GrowChild.api;

import com.example.GrowChild.entity.enumStatus.MembershipType;
import com.example.GrowChild.entity.respone.Membership;
import com.example.GrowChild.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class MembershipAPI {

    @Autowired
    MembershipService membershipService;

    @PostMapping("createMembership")
    public Membership createMembership (@RequestParam MembershipType type,@RequestParam double price){
        return  membershipService.createPackage(type,price);
    }
    @GetMapping("getAllMembership")
    public List<Membership> getAll(){
        return membershipService.getAll();
    }

    @GetMapping("getMembership")
    public Membership getByType(@RequestParam MembershipType membershipType){
        return membershipService.getMembershipByType(membershipType);
    }

    @DeleteMapping("deleteMembership")
    public String delete(@RequestParam MembershipType type){
        return membershipService.deletePackage(type);
    }
}
