package com.example.GrowChild.service;

import com.example.GrowChild.dto.ConsultingDTO;
import com.example.GrowChild.dto.FeedBackDTO;
import com.example.GrowChild.dto.ShareRecordDTO;
import com.example.GrowChild.entity.respone.Consulting;
import com.example.GrowChild.entity.respone.FeedBack;
import com.example.GrowChild.entity.respone.HealthRecord;
import com.example.GrowChild.entity.respone.ShareRecord;
import com.example.GrowChild.mapstruct.toDTO.ShareRecordToDTO;
import com.example.GrowChild.repository.ConsultingRepository;
import com.example.GrowChild.repository.HealthRecordRepository;
import com.example.GrowChild.repository.ShareRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShareRecordService {

    @Autowired
    ShareRecordToDTO shareRecordToDTO;
    @Autowired
    ConsultingSevice consultingService;

    @Autowired
    HealthRecordService healthRecordService;

    @Autowired
    ShareRecordRepository shareRecordRepository;


    public ShareRecordDTO shareHealthRecord(long consultingId, long recordId) {
        Consulting consulting1 = consultingService.getConsultingById2(consultingId);
        if (consulting1 == null) {
            throw new RuntimeException("Consulting not found!");
        }

        HealthRecord healthRecord = healthRecordService.getRecordById(recordId);
        if (healthRecord == null) {
            throw new RuntimeException("Health Record not found!");
        }

        ShareRecord shareRecord = new ShareRecord();
        shareRecord.setConsulting(consulting1);  // thay vì setConsultingId
        shareRecord.setHealthRecord(healthRecord);  // thay vì setHealthRecordId

        shareRecordRepository.save(shareRecord);
        return shareRecordToDTO.toDTO(shareRecord);
    }
    public List<ShareRecord> getSharedRecordsByConsulting(Long consultingId) {
        return shareRecordRepository.findByConsulting_ConsultingId(consultingId);
    }
    public List<ShareRecordDTO> getAll(){
        List<ShareRecord> list = shareRecordRepository.findShareRecordByIsDeleteFalse();
        return shareRecordToDTO.toDTOList(list);
    }

    public ShareRecordDTO getShareRecordById(long shareRecord_id){
        ShareRecord existRecord = getShareRecordByIsDeleteAndShareRecordId(shareRecord_id);
        if(existRecord == null){
            throw new RuntimeException("Share record not found!");
        }
        return shareRecordToDTO.toDTO(existRecord);
    }

    private ShareRecord getShareRecordByIsDeleteAndShareRecordId(long shareRecord_id){
        return shareRecordRepository.findShareRecordByIsDelete_ShareRecordId(shareRecord_id);
    }
    public String deleteShareRecord(long shareRecord_id){
        ShareRecord existShareRecord = getShareRecordByIsDeleteAndShareRecordId(shareRecord_id);
        existShareRecord.setDelete(true);
        shareRecordRepository.save(existShareRecord);
        return "Delete Successfully!";
    }
}
