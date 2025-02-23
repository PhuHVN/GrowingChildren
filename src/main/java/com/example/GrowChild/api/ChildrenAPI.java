package com.example.GrowChild.api;

import com.example.GrowChild.dto.ChildDTO;
import com.example.GrowChild.entity.Children;
import com.example.GrowChild.service.ChildrenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("children")
public class  ChildrenAPI {

    @Autowired
    ChildrenService childrenService;

    @PostMapping("createChild/{parent_id}")
    public ResponseEntity createChild(@Valid  @RequestBody Children children, @RequestParam String userId){
        if(!childrenService.createChild(children,userId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error create children!");
        }
        return new  ResponseEntity<>(children,HttpStatus.CREATED);
    }

    @GetMapping("getAll_User")
    public List<ChildDTO> getAllChildren(){

        return childrenService.getAll();
    }


    @GetMapping("getAll_Admin")
    public List<ChildDTO> getAllChildren_Admin(){
        return childrenService.getAll_Admin();
    }


    @GetMapping("getChildrenById/{childId}")
    public ChildDTO getChildrenById(@RequestParam long child_id){

        return childrenService.getChildById(child_id);
    }

    @PutMapping("updateChild/{childId}")
    public ChildDTO updateChildById(@RequestParam long child_id, @RequestBody Children children){

        return childrenService.updateChild(child_id,children);
    }



    @DeleteMapping("deleteChild_User/{childId}")
    public String deleteChild_User(@RequestParam long child_id){
        return childrenService.deleteChild_User(child_id);
    }
    @DeleteMapping("deleteChild_Admin/{childId}")
    public String deleteChild_Admin(@RequestParam long child_id){
        return childrenService.deleteChild_Admin(child_id);
    }

}
