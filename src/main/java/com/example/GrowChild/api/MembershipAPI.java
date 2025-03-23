package com.example.GrowChild.api;

import com.example.GrowChild.entity.response.Membership;
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
    public Membership createMembership(@RequestBody Membership membership) {
        return membershipService.createPackage(membership);
    }

    @GetMapping("getAllMembership")
    public List<Membership> getAll() {
        return membershipService.getAll();
    }

    @GetMapping("getMembership")
    public Membership getByType(@RequestParam String membershipType) {
        return membershipService.getMembershipByType(membershipType);
    }
    @PutMapping("updateMembership")
    public Membership updatePackage(@RequestParam long id, @RequestBody Membership membership) {
        return membershipService.updatePackage(id, membership);
    }

    @DeleteMapping("deleteMembership")
    public String delete(@RequestParam long id) {
        return membershipService.deletePackage(id);
    }
}
