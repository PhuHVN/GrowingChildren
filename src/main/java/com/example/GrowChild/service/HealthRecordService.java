package com.example.GrowChild.service;

import com.example.GrowChild.dto.RecordDTO;
import com.example.GrowChild.entity.enumStatus.GrowthStatus;
import com.example.GrowChild.entity.request.HealthRecordRequest;
import com.example.GrowChild.entity.respone.Children;
import com.example.GrowChild.entity.respone.HealthRecord;
import com.example.GrowChild.entity.respone.User;
import com.example.GrowChild.mapstruct.toDTO.RecordToDTO;
import com.example.GrowChild.repository.HealthRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HealthRecordService {
    @Autowired
    HealthRecordRepository healthRecordRepository;
    @Autowired
    UserService userService;
    @Autowired
    ChildrenService childrenService;
    @Autowired
    RecordToDTO recordToDTO;

    //create Record
    public HealthRecord createRecord(HealthRecordRequest healthRecordRequest, String parent_id, long childId) {
        User parent = userService.getUser(parent_id);
        if (parent == null || !parent.getRole().getRoleName().equals("Parent")) { // find parent
            throw new RuntimeException("Parent not found");
        }
        Children child = childrenService.getChildrenByIsDeleteFalseAndChildrenId(childId);
        if (child == null) {
            throw new RuntimeException("Children not found");
        }

        HealthRecord record = HealthRecord.builder()
                .parent(parent)
                .child(child)
                .height(healthRecordRequest.getHeight_m())
                .weight(healthRecordRequest.getWeight_kg())
                .date(LocalDate.now())
                .build();

        return healthRecordRepository.save(record);

    }

    public List<Map<String, Object>> getBMIHistory(long childId) {
        List<HealthRecord> records = healthRecordRepository.findByChildChildrenIdOrderByDateAsc(childId); // get data from db
        List<Map<String, Object>> response = new ArrayList<>();

        for (HealthRecord record : records) { //list data
            Map<String, Object> data = new HashMap<>();
            data.put("date", record.getDate()); // add data date in map
            data.put("bmi", calculateBMI(record.getWeight(), record.getHeight())); // add bmi
            response.add(data);
        }

        return response; //return info by Json
    }

    //cal bmi
    private double calculateBMI(double weight, double height) {
        if (height == 0) return 0; // error div with 0
        return weight / (height * height);
    }


    public List<RecordDTO> getAllRecordDTO() {
        List<HealthRecord> records = healthRecordRepository.findHealthRecordByIsDeleteFalse();
        return recordToDTO.toDTOList(records);
    }

    public List<HealthRecord> getAllRecord() {
        return healthRecordRepository.findHealthRecordByIsDeleteFalse();
    }

    public List<HealthRecord> getAllRecord_Admin() {
        return healthRecordRepository.findAll();
    }

    public RecordDTO getRecordDTOById(Long recordId) {
        HealthRecord record = getRecordById(recordId);
        return recordToDTO.toDTO(record);
    }

    public HealthRecord getRecordById(Long recordId) {
        return healthRecordRepository.findById(recordId).orElseThrow(() -> new RuntimeException("Record not found!"));
    }

    public RecordDTO updateRecord(long recordId, HealthRecord healthRecord) {
        HealthRecord record = getRecordById(recordId);
        if (record == null) return null;
        record = HealthRecord.builder()
                .record_id(record.getRecord_id())
                .parent(record.getParent())
                .height(healthRecord.getHeight())
                .weight(healthRecord.getWeight())
                .age(record.getAge())
                .child(record.getChild())
                .date(record.getDate())
                .build();
        HealthRecord updateRecord = healthRecordRepository.save(record);
        return recordToDTO.toDTO(updateRecord);
    }

    public String deleteRecord_User(long recordId) {
        HealthRecord healthRecord = getRecordById(recordId);
        healthRecord.setDelete(true);
        healthRecordRepository.save(healthRecord);
        return "Delete Successful!";
    }

    public String deleteRecord_Admin(long recordId) {
        HealthRecord healthRecord = getRecordById(recordId);
        healthRecordRepository.delete(healthRecord);
        return "Delete Successful!";
    }

    public List<Map<String, Object>> getRecordByChildId(long childId) {
        List<HealthRecord> records = healthRecordRepository.findByChildChildrenIdOrderByDateAsc(childId); // get data from db
        List<Map<String, Object>> response = new ArrayList<>();

        for (HealthRecord record : records) { //list data
            Map<String, Object> data = new HashMap<>();
            data.put("recordId", record.getRecord_id());
            data.put("date", record.getDate()); // add data date in map
            data.put("bmi", calculateBMI(record.getWeight(), record.getHeight())); // add bmi
            data.put("weight", record.getWeight());
            data.put("height", record.getHeight());
            response.add(data);
        }

        return response; //return info by Json
    }

    public GrowthStatus getGrowStatus(double bmi) {
        if (bmi < 14) {
            return GrowthStatus.UNDERWEIGHT;
        } else if (bmi >= 14 && bmi < 18.5) {
            return GrowthStatus.NORMAL;
        } else if (bmi >= 18.5 && bmi < 22) {
            return GrowthStatus.OVERWEIGHT;
        } else {
            return GrowthStatus.OBESE;
        }
    }


}
