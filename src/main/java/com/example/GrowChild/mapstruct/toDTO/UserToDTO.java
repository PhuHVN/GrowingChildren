package com.example.GrowChild.mapstruct.toDTO;

import com.example.GrowChild.dto.UserDTO;
import com.example.GrowChild.entity.response.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public interface UserToDTO {

    default UserDTO toDTO(User user) {
        return UserDTO.builder()
                .user_id(user.getUser_id())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .gender(user.getGender())
                .address(user.getAddress())
                .roleName(user.getRole() != null ? user.getRole().getRoleName() : null)
                .membership(user.getMembership()!= null ?user.getMembership().getType():null)
                .isDelete(user.isDelete())
                .build();
    }

    default User toEntity(UserDTO userDTO) {
        return User.builder()
                .user_id(userDTO.getUser_id())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .fullName(userDTO.getFullName())
                .phone(userDTO.getPhone())
                .address(userDTO.getAddress())
                .gender(userDTO.getGender())
                .isDelete(userDTO.isDelete())
                .build();
    }

    default List<UserDTO> toDTOList(List<User> users) {
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    default List<User> toEntityList(List<UserDTO> userDTOs) {
        return userDTOs.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
