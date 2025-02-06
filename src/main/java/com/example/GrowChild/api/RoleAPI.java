package com.example.GrowChild.api;

import com.example.GrowChild.entity.Role;
import com.example.GrowChild.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleAPI {
    @Autowired
    RoleService roleService;

    @PostMapping("create")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        Role newRole = roleService.createRole(role);
        return new ResponseEntity<>(newRole,HttpStatus.CREATED);
    }

    @GetMapping("getAll")
    public List<Role> getRole(){
        return roleService.getAll();
    }

    @GetMapping("getRole/{roleId}")
    public Role getRoleById(@PathVariable("roleId") long id){
        return roleService.getRoleById(id);
    }

    @PutMapping("update/{roleId}")
    public Role updateRole(@PathVariable("roleId") long id,@RequestBody Role role){
        return roleService.updateRole(id,role);
    }

    @DeleteMapping("delete/{roleId}")
    public String deleteRole(@PathVariable("roleId") long id){
        return roleService.deleteRole(id);
    }



}
