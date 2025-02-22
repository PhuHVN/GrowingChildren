package com.example.GrowChild.service;

import com.example.GrowChild.dto.ScheduleDTO;
import com.example.GrowChild.entity.ScheduleDoctor;
import com.example.GrowChild.entity.User;
import com.example.GrowChild.mapstruct.ScheduleMapper;
import com.example.GrowChild.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    UserService userService;
    @Autowired
    ScheduleMapper scheduleMapper;

    public boolean createSchedule(ScheduleDoctor scheduleDoctor, String doctorId) {
        User doctor = userService.getUser(doctorId);
        if (doctor == null || !doctor.getRole().getRoleName().equals("Doctor")) {
            return false;
        }
        scheduleDoctor.setDoctor(doctor);
        scheduleDoctor.setDelete(false);
        scheduleRepository.save(scheduleDoctor);
        return true;

    }

    public List<ScheduleDoctor> getAll_Admin() {
        return scheduleRepository.findAll();
    }

    public List<ScheduleDTO> getAll() {
        return scheduleMapper.toDTOList(scheduleRepository.findSchdeduleDoctorByIsDeleteFalse());

    }

    public  ScheduleDTO getScheduleDTOById(long scheduleId){
        return scheduleMapper.toDTO(scheduleRepository.findById(scheduleId).orElseThrow(()-> new RuntimeException("Schedule not found!"))) ;
    }

    public  ScheduleDoctor getScheduleById(long scheduleId){
        return scheduleRepository.findById(scheduleId).orElseThrow(()-> new RuntimeException("Schedule not found!")) ;
    }

    public ScheduleDTO getScheduleByDoctorId(String doctorId){
        List<ScheduleDTO> schedules = getAll();
        for(ScheduleDTO scheduleDTO : schedules){
            if(scheduleDTO.getDoctorId().equals(doctorId)){
                return scheduleDTO;
            }
        }
        return null;
    }

    public ScheduleDTO updateSchedule(ScheduleDoctor scheduleDoctor, long scheduleId){
        return null;
    }

    public String deleteSchedule(long scheduleId){
        ScheduleDoctor scheduleDoctor = getScheduleById(scheduleId);
        scheduleDoctor.setDelete(true);
        scheduleRepository.save(scheduleDoctor);
        return "Delete Successful!";

    }
    public String deleteSchedule_Admin(long scheduleId){
        ScheduleDoctor scheduleDoctor = getScheduleById(scheduleId);
        scheduleRepository.delete(scheduleDoctor);
        return "Delete Successful!";

    }
}
