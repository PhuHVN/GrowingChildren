package com.example.GrowChild.api;

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
public class ChildrenAPI {

    @Autowired
    ChildrenService childrenService;

    @PostMapping("createChild")
    public ResponseEntity createChild(@Valid  @RequestBody Children children){
        if(!childrenService.createChild(children)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error create children!");
        }
        return new  ResponseEntity<>(children,HttpStatus.CREATED);
    }

    @GetMapping("getAll")
    public List<Children> getAllChildren(){
        return childrenService.getAll();
    }

    @GetMapping("getChildrenById/{child_id}")
    public Children getChildrenById(@RequestParam long child_id){
        return childrenService.getChildById(child_id);
    }

    @PutMapping("updateChild/{child_id}")
    public Children updateChildById(@RequestParam long child_id, @RequestBody Children children){
        return childrenService.updateChild(child_id,children);
    }

    @DeleteMapping("deleteChild/{child_id}")
    public String deleteChild(@RequestParam long child_id){
        return childrenService.deleteChild(child_id);
    }

}
