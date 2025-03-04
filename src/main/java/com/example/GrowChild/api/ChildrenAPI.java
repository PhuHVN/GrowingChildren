package com.example.GrowChild.api;

import com.example.GrowChild.dto.ChildDTO;
import com.example.GrowChild.entity.response.Children;
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

    @PostMapping("createChild")
    public ResponseEntity createChild(@Valid @RequestBody ChildrenRequest children, @RequestParam String parentId) {
        if (!childrenService.createChild(children, parentId)) {
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

    @GetMapping("childrenByParentId")
    public List<ChildDTO> getChildrenParentById(@RequestParam String parentId) {
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

    //abc
    @DeleteMapping("deleteChild/{childId}")
    public String deleteChild_User(@RequestParam long childId) {
        return childrenService.deleteChild_User(childId);
    }

    @DeleteMapping("deleteChild-admin/{childId}")
    public String deleteChild_Admin(@RequestParam long childId) {
        return childrenService.deleteChild_Admin(childId);
    }

}
