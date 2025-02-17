package com.example.GrowChild.service;

import com.example.GrowChild.dto.ChildDTO;
import com.example.GrowChild.dto.UserDTO;
import com.example.GrowChild.entity.Children;
import com.example.GrowChild.entity.User;
import com.example.GrowChild.mapstruct.ChildMapper;
import com.example.GrowChild.repository.ChildrenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildrenService {
    @Autowired
    ChildrenRepository childrenRepository;
    @Autowired
    UserService userService;
    @Autowired
    ChildMapper childMapper;


    public boolean createChild(Children children, String userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            return false;
        }
        children.setParentId(user);
        children.setDelete(false);
        childrenRepository.save(children);
        return true;
    }

    public List<ChildDTO> getAll() {
        List<Children> list = childrenRepository.findChildrenByIsDeleteFalse();
        return childMapper.toDTOList(list);
    }


    public ChildDTO getChildById(long child_id) {
        Children existChild = getChildrenByIsDeleteFalseAndChildrenId(child_id); // find child with isDelete false!
        if (existChild == null) {
            throw new RuntimeException("Children not found!");
        }
        return childMapper.toDTO(existChild);
    }

    private Children getChildrenByIsDeleteFalseAndChildrenId(long child_id) {
        return childrenRepository.findChildrenByIsDeleteFalseAndChildrenId(child_id);
    }

    public Children updateChild(long child_id, Children children) {
        Children existChild = getChildrenByIsDeleteFalseAndChildrenId(child_id);
        existChild = Children.builder()
                .childrenName(children.getChildrenName())
                .age(children.getAge())
                .gender(children.getGender())
                .build();
        return childrenRepository.save(existChild);
    }

    public String deleteChild(long child_id) {
        Children existChild = getChildrenByIsDeleteFalseAndChildrenId(child_id);
        existChild.setDelete(true);
        childrenRepository.save(existChild);
        return "Delete Successful!";

    }

}
