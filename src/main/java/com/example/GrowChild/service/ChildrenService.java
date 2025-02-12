package com.example.GrowChild.service;

import com.example.GrowChild.entity.Children;
import com.example.GrowChild.entity.User;
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


    public boolean createChild(Children children) {
        if (userService.getUserById(children.getParentId()) == null) {
            return false;
        }
        childrenRepository.save(children);
        return true;
    }

    public List<Children> getAll() {
        return childrenRepository.findChildrenByIsDeleteFalse();
    }



    public Children getChildById(long child_id) {
        Children existChild = childrenRepository.findChildrenByIsDeleteFalseAndChildrenId(child_id);
        if (existChild == null) {
            throw new RuntimeException("Children not found!");
        }
        return existChild;
    }

    public Children updateChild(long child_id, Children children) {
        Children existChild = getChildById(child_id);
        existChild = Children.builder()
                .username(children.getUsername())
                .age(children.getAge())
                .gender(children.getGender())
                .build();
        return childrenRepository.save(existChild);
    }

    public String deleteChild(long child_id) {
        Children existChild = getChildById(child_id) ;
        existChild.setDelete(true);
        childrenRepository.save(existChild);
        return "Delete Successful!";

    }
}
