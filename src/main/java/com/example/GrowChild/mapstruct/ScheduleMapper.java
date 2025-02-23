package com.example.GrowChild.mapstruct;

import com.example.GrowChild.dto.RecordDTO;
import com.example.GrowChild.dto.RoleDTO;
import com.example.GrowChild.dto.ScheduleDTO;
import com.example.GrowChild.entity.HealthRecord;
import com.example.GrowChild.entity.Role;
import com.example.GrowChild.entity.ScheduleDoctor;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

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
