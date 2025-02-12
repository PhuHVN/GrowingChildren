package com.example.GrowChild.service;

import com.example.GrowChild.dto.RoleDTO;
import com.example.GrowChild.entity.Role;
import com.example.GrowChild.mapstruct.RoleMapper;
import com.example.GrowChild.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;

    public RoleDTO createRole(RoleDTO roleDTO){
        Role role = roleMapper.toEntity(roleDTO); // map fill DTO -> entity
        Role saveRole = roleRepository.save(role); // save db
        return roleMapper.toDTO(saveRole);  // return attribute of DTO
    }

    public List<RoleDTO> getAll(){
        List<Role> roles = roleRepository.findAll(); // find list role full info
        return roleMapper.toDTOList(roles); //return some attribute this DTO
    }

    //
    public RoleDTO getRoleById(long id){
        Role role = getRoleExisted(id);
        return roleMapper.toDTO(role);
    }

    public RoleDTO updateRole(long id,RoleDTO roleDTO){
        Role roleExisted = getRoleExisted(id);
        roleExisted = Role.builder()
                .roleId(roleExisted.getRoleId()) //not change id // can del this lined
                .roleName(roleDTO.getRoleName())// change name
                .build();
        Role updateRole = roleRepository.save(roleExisted); // save db
        return roleMapper.toDTO(updateRole);// return DTO
    }


    public String deleteRole(long id){
        Role roleExisted = getRoleExisted(id); // find role
        if(roleExisted == null){
            return "Error Deleted";
        }
        roleRepository.delete(roleExisted);
        return "Delete Successful";
    }

    public Role getRoleExisted(long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found!"));
    }


}
