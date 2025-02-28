package com.example.GrowChild.service;

import com.example.GrowChild.dto.ChildDTO;
import com.example.GrowChild.entity.respone.Children;
import com.example.GrowChild.entity.respone.User;
import com.example.GrowChild.entity.request.ChildrenRequest;
import com.example.GrowChild.mapstruct.toDTO.ChildToDTO;
import com.example.GrowChild.repository.ChildrenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChildrenService {
    @Autowired
    ChildrenRepository childrenRepository;
    @Autowired
    UserService userService;
    @Autowired
    ChildToDTO childTODTO;




    public boolean createChild(ChildrenRequest childrenRequest, String userId) {
        User parent = userService.getUser(userId); //get parent by id
        if (parent == null || !parent.getRole().getRoleName().equals("Parent")) { // not found or user not parent
            return false;
        }

        Children children = Children.builder()
                .childrenName(childrenRequest.getChildrenName())
                .age(childrenRequest.getAge())
                .gender(childrenRequest.getGender())
                .parentId(parent)
                .isDelete(false)
                .build();
        childrenRepository.save(children);
        return true;
    }

    public List<Children> getAll_Admin() {
       return childrenRepository.findAll();

    }

    public List<ChildDTO> getAll() {
        List<Children> list = childrenRepository.findChildrenByIsDeleteFalse(); // getAllChildren with isDelete false
        return childTODTO.toDTOList(list);
    }


    public ChildDTO getChildById(long child_id) {
        Children existChild = getChildrenByIsDeleteFalseAndChildrenId(child_id); // find child with id & isDelete false!
        if (existChild == null) {
            throw new RuntimeException("Children not found!");
        }
        return childTODTO.toDTO(existChild);
    }

    protected Children getChildrenByIsDeleteFalseAndChildrenId(long child_id) {
        return childrenRepository.findChildrenByIsDeleteFalseAndChildrenId(child_id);
    }

    public ChildDTO updateChild(long child_id, ChildrenRequest children) {
        Children existChildren = getChildrenByIsDeleteFalseAndChildrenId(child_id); //get child
        existChildren = Children.builder()
                .childrenId(existChildren.getChildrenId())
                .parentId(existChildren.getParentId())
                .childrenName(children.getChildrenName())
                .age(children.getAge())
                .gender(children.getGender())
                .build(); //set attribute child
        Children updateChild = childrenRepository.save(existChildren);
        return childTODTO.toDTO(updateChild);
    }

    public String deleteChild_User(long child_id) {
        Children existChild = getChildrenByIsDeleteFalseAndChildrenId(child_id);
        existChild.setDelete(true);
        childrenRepository.save(existChild);
        return "Delete Successful!";

    }

    public String deleteChild_Admin(long child_id) {
        Children existChild = getChildrenByIsDeleteFalseAndChildrenId(child_id);
        childrenRepository.delete(existChild);
        return "Delete Successful!";
    }

    public List<ChildDTO> getChildByParentId(String parentId){
        List<ChildDTO> childrenList = getAll();
        List<ChildDTO> list = new ArrayList<>();
        for(ChildDTO childDTO : childrenList){
            if(childDTO.getParentId().equals(parentId)){
                list.add(childDTO);
            }
        }
        return list;
    }

}
