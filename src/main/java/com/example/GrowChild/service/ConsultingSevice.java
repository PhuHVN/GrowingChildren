package com.example.GrowChild.service;

import com.example.GrowChild.dto.ConsultingDTO;
import com.example.GrowChild.entity.request.ConsultingRequest;
import com.example.GrowChild.entity.respone.Blog;
import com.example.GrowChild.entity.respone.Children;
import com.example.GrowChild.entity.respone.Consulting;
import com.example.GrowChild.entity.respone.User;
import com.example.GrowChild.mapstruct.toDTO.ConsultingToDTO;
import com.example.GrowChild.repository.ConsultingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultingSevice {
    @Autowired
    ConsultingRepository consultingRepository;

    @Autowired
    UserService userService;

    @Autowired
    ConsultingToDTO consultingToDTO;

    @Autowired
    ChildrenService childrenService;

    //Create
    public Consulting createConsulting(ConsultingRequest consultingRequest, String doctor_id,
                                       String parent_id, long child_id){
        User doctor = userService.getUser(doctor_id);
        if(doctor == null || !doctor.role.getRoleName().equals("Doctor")){ // find doctor
            throw new RuntimeException("Doctor not found");
        }
        User parent = userService.getUser(doctor_id);
        if(parent == null || !parent.role.getRoleName().equals("Parent")){ // find parent
            throw new RuntimeException("Parent not found");
        }
        Children child = childrenService.getChildrenByIsDeleteFalseAndChildrenId(child_id);
        if(child == null){ // find child
            throw new RuntimeException("Children not found");
        }

        Consulting consulting = Consulting.builder()
                .parentId(parent)
                .childId(child)
                .doctorId(doctor)
                .title(consultingRequest.getTitle())
                .comment(consultingRequest.getComment())
                .date(LocalDateTime.now())
                .build();

        return consultingRepository.save(consulting);
    }

    public List<ConsultingDTO> getAll(){
        List<Consulting> list = consultingRepository.findConsultingByIsDeleteFalse();
        return consultingToDTO.toDTOList(list);
    }

    public ConsultingDTO getConsultingById(long consulting_id){
        Consulting existConsulting = getConsultingByIsDeleteAndConsultingId(consulting_id);
        if(existConsulting == null){
            throw new RuntimeException("Consulting not found!");
        }
        return consultingToDTO.toDTO(existConsulting);
    }

    private Consulting getConsultingByIsDeleteAndConsultingId(long consulting_id){
        return consultingRepository.findConsultingByIsDeleteFalseAndConsultingId(consulting_id);
    }

    public ConsultingDTO updateConsulting(long consulting_id, Consulting consulting){
        Consulting existConsulting = getConsultingByIsDeleteAndConsultingId(consulting_id);
        if (consulting == null) return null;
        existConsulting = Consulting.builder()
                .consultingId(existConsulting.getConsultingId())
                .title(consulting.getTitle())
                .comment(consulting.getComment())
                .date(LocalDateTime.now())
                .parentId(existConsulting.getParentId())
                .doctorId(existConsulting.getDoctorId())
                .bookingId(existConsulting.getBookingId())
                .build();
        Consulting updateConsulting = consultingRepository.save(existConsulting);
        return consultingToDTO.toDTO(updateConsulting);
    }

    public String deleteConsulting(long consulting_id){
        Consulting existConsulting = getConsultingByIsDeleteAndConsultingId(consulting_id);
        existConsulting.setDelete(true);
        consultingRepository.save(existConsulting);
        return "Delete Successful!";
    }
}
