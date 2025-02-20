package com.example.GrowChild.service;

import com.example.GrowChild.dto.RecordDTO;
import com.example.GrowChild.entity.Children;
import com.example.GrowChild.entity.HealthRecord;
import com.example.GrowChild.entity.User;
import com.example.GrowChild.mapstruct.RecordMapper;
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
    RecordMapper recordMapper;
    //create Record
    public HealthRecord createRecord(HealthRecord healthRecord,String parent_id,long childId) {
        User parent = userService.getUser(parent_id);
        if(parent == null || !parent.role.getRoleName().equals("Doctor")){ // find parent
            throw new RuntimeException("Doctor not found");
        }
        Children child = childrenService.getChildrenByIsDeleteFalseAndChildrenId(childId);
        if(child == null){
            throw new RuntimeException("Children not found");
        }
        healthRecord = HealthRecord.builder()
                .parent(pa)
                .child(child)
                .height(healthRecord.getHeight())
                .weight(healthRecord.getWeight())
                .age(child.getAge())
                .gender(child.getGender())
                .date(LocalDate.now())
                .build();
        return healthRecordRepository.save(healthRecord);

    }

    public List<Map<String,Object>> getBMIHistory(long childId){
        List<HealthRecord> records = healthRecordRepository.findByChildChildrenIdOrderByDateAsc(childId); // get data from db
        List<Map<String,Object>> response = new ArrayList<>();

        for(HealthRecord record : records){ //list data
            Map<String, Object> data = new HashMap<>();
            data.put("date",record.getDate()); // add data date in map
            data.put("bmi",calculateBMI(record.getWeight(), record.getHeight())); // add bmi
            response.add(data);
        }

        return response; //return info by Json
    }

    //cal bmi
    private double calculateBMI(double weight, double height) {
        if (height == 0) return 0; // error div with 0
        return weight / (height * height);
    }

    public List<RecordDTO> getAllRecordDTO(){
        List<HealthRecord> records = healthRecordRepository.findHealthRecordByIsDeleteFalse();
        return recordMapper.toDTOList(records);
    }
    public List<HealthRecord> getAllRecord(){
        return healthRecordRepository.findHealthRecordByIsDeleteFalse();
    }
    public RecordDTO getRecordDTOById(Long recordId){
        HealthRecord record = getRecordById(recordId);
        return recordMapper.toDTO(record);
    }
    public HealthRecord getRecordById(Long recordId){
        return healthRecordRepository.findById(recordId).orElseThrow(() -> new RuntimeException("Record not found!"));
    }

    public RecordDTO updateRecord(long recordId,HealthRecord healthRecord){
        HealthRecord record = getRecordById(recordId);
        if(record == null) return null;
        record = HealthRecord.builder()
                .parent(record.getParent())
                .height(healthRecord.getHeight())
                .weight(healthRecord.getWeight())
                .child(record.getChild())
                .date(record.getDate())
                .build();
        HealthRecord updateRecord = healthRecordRepository.save(record);
        return recordMapper.toDTO(updateRecord);
    }

    public String deleteRecord(long recordId){
        HealthRecord healthRecord = getRecordById(recordId);
        healthRecord.setDelete(true);
        return "Delete Successful!";
    }


}
