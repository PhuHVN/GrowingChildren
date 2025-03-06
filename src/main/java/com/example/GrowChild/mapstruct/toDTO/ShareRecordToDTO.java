package com.example.GrowChild.mapstruct.toDTO;

import com.example.GrowChild.dto.ShareRecordDTO;
import com.example.GrowChild.entity.response.ShareRecord;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ShareRecordToDTO {
//    default ShareRecordDTO toDTO(ShareRecord shareRecord){
//        return ShareRecordDTO.builder()
//                .shareRecordId(shareRecord.getShareRecordId())
//                .consultingId(shareRecord.getConsultingId().getConsultingId())
//                .healthRecordId(shareRecord.getHealthRecordId().getRecord_id())
//                .build();
//    }
//
//    default List<ShareRecordDTO> toDTOList(List<ShareRecord> shareRecordList){
//        return shareRecordList.stream().map(this::toDTO).collect(Collectors.toList());
//    }

    //    ShareRecordDTO toDTO(ShareRecord shareRecord);
//
//    default List<ShareRecordDTO> toDTOList(List<ShareRecord> shareRecordList) {
//        return shareRecordList.stream().map(this::toDTO).collect(Collectors.toList());
//    }
    default ShareRecordDTO toDTO(ShareRecord shareRecord) {
        return ShareRecordDTO.builder()
                .shareRecordId(shareRecord.getShareRecordId())
                .consulting(shareRecord.getConsulting().getConsultingId())
                .healthRecord(shareRecord.getHealthRecord().getRecord_id())
                .height(shareRecord.getHealthRecord().getHeight())
                .weight(shareRecord.getHealthRecord().getWeight())
                .bmi(shareRecord.getHealthRecord().getBmi())
                .build();
    }

    default List<ShareRecordDTO> toDTOList(List<ShareRecord> shareRecordList) {
        return shareRecordList.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
