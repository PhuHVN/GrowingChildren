package com.example.GrowChild.api;

import com.example.GrowChild.dto.RecordDTO;
import com.example.GrowChild.entity.request.HealthRecordRequest;
import com.example.GrowChild.entity.response.HealthRecord;
import com.example.GrowChild.service.HealthRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("healthRecord")
public class HealthRecordAPI {
    @Autowired
    HealthRecordService healthRecordService;

    @PostMapping("createRecord")
    public ResponseEntity createRecord(@Valid @RequestBody HealthRecordRequest healthRecord,
                                       @RequestParam String parentId, @RequestParam long childId) {
        HealthRecord record = healthRecordService.createRecord(healthRecord, parentId, childId);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @GetMapping("records")
    public List<HealthRecord> getAllRecord() {
        return healthRecordService.getAllRecord();
    }

    @GetMapping("records-admin")
    public List<HealthRecord> getAllRecord_Admin() {
        return healthRecordService.getAllRecord_Admin();
    }

    @GetMapping("recordsDTO")
    public List<RecordDTO> getAllRecordDTO() {
        return healthRecordService.getAllRecordDTO();
    }

    @GetMapping("recordDTO/{recordId}")
    public RecordDTO getRecordDTOById(@PathVariable long recordId) {
        return healthRecordService.getRecordDTOById(recordId);
    }

    @GetMapping("record/{recordId}")
    public HealthRecord getRecordById(@PathVariable long recordId) {
        return healthRecordService.getRecordById(recordId);
    }

    @PutMapping("updateRecord/{recordId}")
    public RecordDTO updateRecord(@PathVariable long recordId, @RequestBody HealthRecord healthRecord) {
        return healthRecordService.updateRecord(recordId, healthRecord);
    }

    @DeleteMapping("deleteRecord_User/{recordId}")
    public String deleteRecord_User(@PathVariable long recordId) {
        return healthRecordService.deleteRecord_User(recordId);
    }

    @DeleteMapping("deleteRecord_Admin/{recordId}")
    public String deleteRecord_Admin(@PathVariable long recordId) {
        return healthRecordService.deleteRecord_Admin(recordId);
    }


    @GetMapping("bmi/history/{childId}")
    public ResponseEntity<List<Map<String, Object>>> getBMIHistory(@PathVariable int childId) {
        List<Map<String, Object>> history = healthRecordService.getBMIHistory(childId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("records/{childId}")
    public ResponseEntity<List<Map<String, Object>>> getRecordByChildId(@PathVariable int childId) {
        List<Map<String, Object>> history = healthRecordService.getRecordByChildId(childId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("getGrowthStatus/bmi/{bmi}")
    public ResponseEntity<String> getGrowthStatus(@RequestParam double bmi) {
        return ResponseEntity.ok("Status: " + healthRecordService.getGrowStatus(bmi));
    }

    @GetMapping("getGrowthStatusChange/childId/{childId}")
    public ResponseEntity<String> getChangeStatus(@PathVariable long childId,@RequestParam double bmiLastRecord, @RequestParam double bmiCurrentRecord){
        return ResponseEntity.ok("Current BMI status: " + bmiCurrentRecord +", compared to last month: "+ bmiLastRecord +", and there are changes: "+healthRecordService.getChangeStatus(childId,bmiLastRecord,bmiCurrentRecord)+".");
    }
}
