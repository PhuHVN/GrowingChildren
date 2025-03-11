package com.example.GrowChild.mapstruct.toDTO;

import com.example.GrowChild.dto.FeedBackDTO;
import com.example.GrowChild.entity.response.FeedBack;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FeedBackToDTO {
    default FeedBackDTO toDTO(FeedBack feedBack) {
        return FeedBackDTO.builder()
                .feedbackId(feedBack.getFeedbackId())
                .rate(feedBack.getRate())
                .comment(feedBack.getComment())
                .parentId(feedBack.getParentId().getUser_id())
                .doctorId(feedBack.getDoctorId().getUser_id())
                .consultingId(feedBack.getConsultingId().getConsultingId())
                .build();
    }

    default List<FeedBackDTO> toDTOList(List<FeedBack> feedBackList) {
        return feedBackList.stream().map(this::toDTO).collect(Collectors.toList());

    }
}
