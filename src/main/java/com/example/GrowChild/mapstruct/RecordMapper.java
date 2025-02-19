package com.example.GrowChild.mapstruct;

import com.example.GrowChild.dto.RecordDTO;
import com.example.GrowChild.entity.HealthRecord;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RecordMapper {

    default RecordDTO toDTO(HealthRecord record) {
        return RecordDTO.builder()
                .recordId(record.getRecord_id())
                .weight(record.getWeight())
                .height(record.getHeight())
                .bmi(record.getBmi())
                .age(record.getAge())
                .date(record.getDate())
                .childId(record.getChild() != null ? record.getChild().getChildrenId() : null)
                .childName(record.getChild() != null ? record.getChild().getChildrenName() : null)
                .doctorId(record.getDoctor() != null ? record.getDoctor().getUser_id() : null)
                .doctorName(record.getDoctor() != null ? record.getDoctor().getFullName() : null)
                .build();
    }

    default List<RecordDTO> toDTOList(List<HealthRecord> records) {
        return records.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
