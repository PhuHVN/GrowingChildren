package com.example.GrowChild.mapstruct.toDTO;

import com.example.GrowChild.dto.ScheduleDTO;
import com.example.GrowChild.entity.respone.ScheduleDoctor;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ScheduleToDTO {

    default ScheduleDTO toDTO(ScheduleDoctor scheduleDoctor) {
        return ScheduleDTO.builder()
                .scheduleId(scheduleDoctor.getScheduleId())
                .doctorId(scheduleDoctor.getDoctor() != null ?scheduleDoctor.getDoctor().getUser_id() : null)
                .doctorName(scheduleDoctor.getDoctor() != null ?scheduleDoctor.getDoctor().getFullName() : null)
                .scheduleWork(scheduleDoctor.getScheduleWork())
                .isDelete(scheduleDoctor.isDelete())
                .build();

    }

    default List<ScheduleDTO> toDTOList(List<ScheduleDoctor> records) {
        return records.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
