package com.example.GrowChild.mapstruct;


import com.example.GrowChild.dto.ChildDTO;
import com.example.GrowChild.dto.UserDTO;
import com.example.GrowChild.entity.Children;
import com.example.GrowChild.entity.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public interface ChildMapper {

    default ChildDTO toDTO(Children children) {
        return ChildDTO.builder()
                .childrenId(children.getChildrenId())
                .childrenName(children.getChildrenName())
                .age(children.getAge())
                .gender(children.getGender())
                .parentId(children.getParentId().getUser_id()) // Chuyển UUID thành String
                .build();
    }

    default List<ChildDTO> toDTOList(List<Children> childrenList) {
        return childrenList.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
