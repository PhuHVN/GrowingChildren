package com.example.GrowChild.api;

import com.example.GrowChild.dto.RecordDTO;
import com.example.GrowChild.entity.respone.HealthRecord;
import com.example.GrowChild.entity.request.HealthRecordRequest;
import com.example.GrowChild.service.HealthRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("HealthRecord")
public class HealthRecordAPI {
    @Autowired
    HealthRecordService healthRecordService;

    @PostMapping("createRecord/{doctorId}/{childId}")
    public ResponseEntity createRecord(@Valid @RequestBody HealthRecordRequest healthRecord , @RequestParam String parentId, @RequestParam long childId){
        HealthRecord record =healthRecordService.createRecord(healthRecord,parentId,childId);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @GetMapping("getAllRecord")
    public List<HealthRecord> getAllRecord(){
        return healthRecordService.getAllRecord();
    }

    @GetMapping("getAllRecord_Admin")
    public List<HealthRecord> getAllRecord_Admin(){
        return healthRecordService.getAllRecord_Admin();
    }

    @GetMapping("getAllRecordDTO")
    public List<RecordDTO> getAllRecordDTO(){
        return healthRecordService.getAllRecordDTO();
    }

    @GetMapping("getRecordDTOById/{recordId}")
    public RecordDTO getRecordDTOById(@RequestParam long recordId){
        return healthRecordService.getRecordDTOById(recordId);
    }

    @GetMapping("getRecordById/{recordId}")
    public HealthRecord getRecordById(@RequestParam long recordId){
        return healthRecordService.getRecordById(recordId);
    }

    @PutMapping("updateRecord/{recordId}")
    public RecordDTO updateRecord(@RequestParam long recordId , @RequestBody HealthRecord healthRecord ){
        return healthRecordService.updateRecord(recordId,healthRecord);
    }

    @DeleteMapping("deleteRecord_User/{recordId}")
    public String deleteRecord_User(@RequestParam long recordId){
        return healthRecordService.deleteRecord_User(recordId);
    }

    @DeleteMapping("deleteRecord_Admin/{recordId}")
    public String deleteRecord_Admin(@RequestParam long recordId){
        return healthRecordService.deleteRecord_Admin(recordId);
    }


    @GetMapping("bmi/history/{childId}")
    public ResponseEntity<List<Map<String, Object>>> getBMIHistory(@PathVariable int childId) {
        List<Map<String, Object>> history = healthRecordService.getBMIHistory(childId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("getRecordByChildId/{childId}")
    public ResponseEntity<List<Map<String, Object>>> getRecordByChildId(@PathVariable int childId) {
        List<Map<String, Object>> history = healthRecordService.getRecordByChildId(childId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("getGrowthStatus/{bmi}")
    public ResponseEntity<String> getGrowthStatus(double bmi){
        return ResponseEntity.ok("Status: " + healthRecordService.getGrowStatus(bmi));
    }
}
