package com.example.GrowChild.service;

import com.example.GrowChild.entity.Role;
import com.example.GrowChild.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;


    public Role createRole(Role role){
        return roleRepository.save(role);
    }

    public List<Role> getAll(){
        return roleRepository.findAll();
    }

    @Transactional
    public Role getRoleById(long id){
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public Role updateRole(long id,Role role){
        Role roleExisted = getRoleById(id);
        roleExisted.setRoleName(role.roleName);
        return roleRepository.save(roleExisted);
    }

    public String deleteRole(long id){
        Role roleExisted = getRoleById(id);
        if(roleExisted == null){
            return "Error Deleted";
        }
        roleRepository.delete(roleExisted);
        return "Delete Successful";
    }



}
