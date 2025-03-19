package com.example.GrowChild.mapstruct.toDTO;

import com.example.GrowChild.dto.ConsultingDTO;
import com.example.GrowChild.entity.response.Consulting;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ConsultingToDTO {

    default ConsultingDTO toDTO(Consulting consulting) {
        return ConsultingDTO.builder()
                .consultingId(consulting.getConsultingId())
                .title(consulting.getTitle())
                .comment(consulting.getComment())
                .date(consulting.getDate())
                .parentId(consulting.getParentId().getUser_id())
                .doctorId(consulting.getDoctorId().getUser_id())
                .bookingId(consulting.getBookingId().getBookId())
                .childId(consulting.getChildId().getChildrenId())
                .build();
    }

    default List<ConsultingDTO> toDTOList(List<Consulting> consultingList) {
        return consultingList.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
