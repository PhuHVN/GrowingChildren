package com.example.GrowChild.service;

import com.example.GrowChild.dto.ConsultingDTO;
import com.example.GrowChild.entity.request.ConsultingRequest;
import com.example.GrowChild.entity.response.*;
import com.example.GrowChild.mapstruct.toDTO.ConsultingToDTO;
import com.example.GrowChild.repository.ConsultingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
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

    @Autowired
    BookingService bookingService;

    //Create
    public Consulting createConsulting(ConsultingRequest consultingRequest, String doctor_id,
                                       String parent_id, long child_id, long booking_id){
        User doctor = userService.getUser(doctor_id);
        if(doctor == null || !doctor.getRole().getRoleName().equals("Doctor")){ // find doctor
            throw new RuntimeException("Doctor not found");
        }
        User parent = userService.getUser(parent_id);
        if(parent == null || !parent.getRole().getRoleName().equals("Parent")){ // find parent
            throw new RuntimeException("Parent not found");
        }
        Children child = childrenService.getChildrenByIsDeleteFalseAndChildrenId(child_id);
        if(child == null){ // find child
            throw new RuntimeException("Children not found");
        }

        Booking booking = bookingService.getBookingById(booking_id);
            if (booking == null){
                throw new RuntimeException("Booking not found");
            }


        Consulting consulting = Consulting.builder()
                .parentId(parent)
                .childId(child)
                .doctorId(doctor)
                .title(consultingRequest.getTitle())
                .comment(consultingRequest.getComment())
                .date(LocalDateTime.now())
                .bookingId(booking)
                .build();

        return consultingRepository.save(consulting);
    }


    public List<ConsultingDTO> getAll(){
        List<Consulting> list = consultingRepository.findConsultingByIsDeleteFalse();
        return consultingToDTO.toDTOList(list);
    }

    protected Consulting getConsultingByID(long id) {
        return consultingRepository.findById(id).orElseThrow(() -> new RuntimeException("Consulting not found!"));
    }

    public ConsultingDTO getConsultingById(long consulting_id){
        Consulting existConsulting = getConsultingByIsDeleteAndConsultingId(consulting_id);
        if(existConsulting == null){
            throw new RuntimeException("Consulting not found!");
        }
        return consultingToDTO.toDTO(existConsulting);
    }

    //ham lam them de goi share record service
    public Consulting getConsultingById2(long consulting_id){
        return consultingRepository.findById(consulting_id).orElseThrow(() -> new RuntimeException("Consulting not found!"));
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
                .childId(existConsulting.getChildId())
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
