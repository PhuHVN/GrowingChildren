package com.example.GrowChild.mapstruct.toDTO;


import com.example.GrowChild.dto.RoleDTO;
import com.example.GrowChild.entity.response.Role;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleToDTO {

    default RoleDTO toDTO(Role role) {
        return RoleDTO.builder()
                .roleId(role.getRoleId())
                .roleName(role.getRoleName())
                .build();

    }

    default Role toEntity(RoleDTO roleDTO) {
        return Role.builder()
                .roleId(roleDTO.getRoleId())
                .roleName(roleDTO.getRoleName())
                .build();
    }

    default List<RoleDTO> toDTOList(List<Role> roles) {
        return roles.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
