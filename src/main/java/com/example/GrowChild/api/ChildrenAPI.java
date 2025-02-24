package com.example.GrowChild.api;

import com.example.GrowChild.dto.ChildDTO;
import com.example.GrowChild.entity.respone.Children;
import com.example.GrowChild.entity.request.ChildrenRequest;
import com.example.GrowChild.service.ChildrenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("childrenAPI")
public class ChildrenAPI {

    @Autowired
    ChildrenService childrenService;

    @PostMapping("createChild/{parent_id}")
    public ResponseEntity createChild(@Valid @RequestBody ChildrenRequest children, @PathVariable String userId) {
        if (!childrenService.createChild(children, userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error create children!");
        }
        return new ResponseEntity<>(children, HttpStatus.CREATED);
    }

    @GetMapping("getChildren")
    public List<ChildDTO> getAllChildren() {
        return childrenService.getAll();
    }


    @GetMapping("children-admin")
    public List<Children> getAllChildren_Admin() {
        return childrenService.getAll_Admin();
    }

    @GetMapping("children/{parentId}")
    public List<ChildDTO> getChildrenParentById(@PathVariable String parentId) {
        return childrenService.getChildByParentId(parentId);
    }

    @GetMapping("child/{childId}")
    public ChildDTO getChildrenById(@PathVariable long childId) {
        return childrenService.getChildById(childId);
    }

    @PutMapping("updateChild/{childId}")

    public ChildDTO updateChildById(@PathVariable long childId, @RequestBody ChildrenRequest children) {
        return childrenService.updateChild(childId, children);
    }



    @DeleteMapping("deleteChild/{childId}")
    public String deleteChild_User(@RequestParam long child_id) {
        return childrenService.deleteChild_User(child_id);
    }

    @DeleteMapping("deleteChild-admin/{childId}")
    public String deleteChild_Admin(@RequestParam long child_id) {
        return childrenService.deleteChild_Admin(child_id);
    }

}
