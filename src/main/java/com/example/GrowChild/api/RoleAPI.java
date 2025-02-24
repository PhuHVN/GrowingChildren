package com.example.GrowChild.api;

import com.example.GrowChild.dto.RoleDTO;
import com.example.GrowChild.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("role")
public class RoleAPI {
    @Autowired
    RoleService roleService;

    @PostMapping("createRole")
    public ResponseEntity createRole(@RequestBody RoleDTO role){
        RoleDTO newRole = roleService.createRole(role);
        return new ResponseEntity<>(newRole,HttpStatus.CREATED);
    }

    @GetMapping("getAll")
    public List<RoleDTO> getRole(){
        return roleService.getAll();
    }

    @GetMapping("getRole/{roleId}")
    public RoleDTO getRoleById(@PathVariable("roleId") long id){
        return roleService.getRoleById(id);
    }

    @PutMapping("update/{roleId}")
    public RoleDTO updateRole(@PathVariable("roleId") long id,@RequestBody RoleDTO roleDTO){
        return roleService.updateRole(id,roleDTO);
    }

    @DeleteMapping("delete/{roleId}")
    public String deleteRole(@PathVariable("roleId") long id){
        return roleService.deleteRole(id);
    }



}
