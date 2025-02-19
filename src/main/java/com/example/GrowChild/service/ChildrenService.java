package com.example.GrowChild.service;

import com.example.GrowChild.dto.ChildDTO;
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
        User parent = userService.getUser(userId); //get parent by id
        if (parent == null || !parent.role.getRoleName().equals("Parent")) { // not found or user not parent
            return false;
        }
        children.setParentId(parent);
        children.setDelete(false);
        childrenRepository.save(children);
        return true;
    }

    public List<ChildDTO> getAll() {
        List<Children> list = childrenRepository.findChildrenByIsDeleteFalse(); // getAllChildren with isDelete false
        return childMapper.toDTOList(list);
    }


    public ChildDTO getChildById(long child_id) {
        Children existChild = getChildrenByIsDeleteFalseAndChildrenId(child_id); // find child with id & isDelete false!
        if (existChild == null) {
            throw new RuntimeException("Children not found!");
        }
        return childMapper.toDTO(existChild);
    }

    protected Children getChildrenByIsDeleteFalseAndChildrenId(long child_id) {
        return childrenRepository.findChildrenByIsDeleteFalseAndChildrenId(child_id);
    }

    public ChildDTO updateChild(long child_id, Children children) {
        Children existChildren = getChildrenByIsDeleteFalseAndChildrenId(child_id);
        existChildren = Children.builder()
                .childrenName(children.getChildrenName())
                .age(children.getAge())
                .gender(children.getGender())
                .build();
        Children updateChild = childrenRepository.save(existChildren);
        return childMapper.toDTO(updateChild);
    }

    public String deleteChild(long child_id) {
        Children existChild = getChildrenByIsDeleteFalseAndChildrenId(child_id);
        existChild.setDelete(true);
        childrenRepository.save(existChild);
        return "Delete Successful!";

    }

}
