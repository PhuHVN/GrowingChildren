package com.example.GrowChild.service;

import com.example.GrowChild.dto.ShareRecordDTO;
import com.example.GrowChild.entity.response.Consulting;
import com.example.GrowChild.entity.response.HealthRecord;
import com.example.GrowChild.entity.response.ShareRecord;
import com.example.GrowChild.mapstruct.toDTO.ShareRecordToDTO;
import com.example.GrowChild.repository.ShareRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<ShareRecordDTO> getAll() {
        // Lấy tất cả bản ghi chưa bị xóa
        List<ShareRecord> shareRecords = shareRecordRepository.findShareRecordByIsDeleteFalse();

        // Chuyển đổi danh sách ShareRecord sang ShareRecordDTO
        return shareRecords.stream()
                .map(shareRecordToDTO::toDTO)
                .collect(Collectors.toList());
    }


    public String deleteShareRecord(long shareRecordId) {
        // Lấy bản ghi từ repository
        ShareRecord recordToDelete = shareRecordRepository.findById(shareRecordId)
                .orElseThrow(() -> new RuntimeException("Share record not found"));

        // Đánh dấu bản ghi là đã xóa
        recordToDelete.setDelete(true);

        // Lưu bản ghi đã được cập nhật
        shareRecordRepository.save(recordToDelete);

        return "Delete successfully!";
    }
}
