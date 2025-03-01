package com.example.GrowChild.service;


import com.example.GrowChild.dto.FeedBackDTO;
import com.example.GrowChild.entity.request.FeedBackRequest;
import com.example.GrowChild.entity.respone.Consulting;
import com.example.GrowChild.entity.respone.FeedBack;
import com.example.GrowChild.entity.respone.User;
import com.example.GrowChild.mapstruct.toDTO.FeedBackToDTO;
import com.example.GrowChild.repository.FeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedBackService {
    @Autowired
    FeedBackRepository feedBackRepository;

    @Autowired
    UserService userService;

    @Autowired
    FeedBackToDTO feedBackToDTO;




    public FeedBack createFeedBack(FeedBackRequest feedBackRequest,
                                   String doctor_id, String parent_id){
        User doctor = userService.getUser(doctor_id);
        if(doctor == null || !doctor.getRole().getRoleName().equals("Doctor")){ // find doctor
            throw new RuntimeException("Doctor not found");
        }
        User parent = userService.getUser(doctor_id);
        if(parent == null || !parent.getRole().getRoleName().equals("Parent")){ // find parent
            throw new RuntimeException("Parent not found");
        }
        FeedBack feedBack = FeedBack.builder()
                .parentId(parent)
                .doctorId(doctor)
                .rate(feedBackRequest.getRate())
                .comment(feedBackRequest.getComment())
                .build();
        return feedBackRepository.save(feedBack);
    }

    public List<FeedBackDTO> getAll(){
        List<FeedBack> list = feedBackRepository.findFeedbackByIsDeleteFalse();
        return feedBackToDTO.toDTOList(list);
    }

    public FeedBackDTO getFeedBackById(long feedback_id){
        FeedBack existFeedBack = getFeedbackByIsDeleteAndFeedbackId(feedback_id);
        if(existFeedBack == null){
            throw new RuntimeException("FeedBack not found!");
        }
        return feedBackToDTO.toDTO(existFeedBack);
    }

    private FeedBack getFeedbackByIsDeleteAndFeedbackId(long feedback_id){
        return feedBackRepository.findFeedbackByIsDeleteFalseAndFeedbackId(feedback_id);
    }

    public FeedBackDTO updateFeedBack(long feedback_id, FeedBack feedBack){
        FeedBack existFeedBack = getFeedbackByIsDeleteAndFeedbackId(feedback_id);
        if (feedBack == null) return null;
        existFeedBack = FeedBack.builder()
                .feedbackId(existFeedBack.getFeedbackId())
                .rate(feedBack.getRate())
                .comment(feedBack.getComment())
                .consultingId(existFeedBack.getConsultingId())
                .parentId(existFeedBack.getParentId())
                .doctorId(existFeedBack.getDoctorId())
                .build();

        FeedBack updateFeedBack = feedBackRepository.save(existFeedBack);
        return feedBackToDTO.toDTO(updateFeedBack);
    }

    public String deleteFeedBack(long feedback_id){
        FeedBack existFeedBack = getFeedbackByIsDeleteAndFeedbackId(feedback_id);
        existFeedBack.setDelete(true);
        feedBackRepository.save(existFeedBack);
        return "Delete Successfully!";
    }
}
