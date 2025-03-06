package com.example.GrowChild.service;

import com.example.GrowChild.dto.ScheduleDTO;
import com.example.GrowChild.entity.request.ScheduleRequest;
import com.example.GrowChild.entity.response.ScheduleDoctor;
import com.example.GrowChild.entity.response.User;
import com.example.GrowChild.mapstruct.toDTO.ScheduleToDTO;
import com.example.GrowChild.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    UserService userService;
    @Autowired
    ScheduleToDTO scheduleToDTO;

    public boolean createSchedule(ScheduleRequest schedule, String doctorId) {
        User doctor = userService.getUser(doctorId);
        if (doctor == null || !doctor.getRole().getRoleName().equals("Doctor")) {
            throw new IllegalArgumentException("Doctor not found!");
        }

        if(LocalDate.now().isAfter(schedule.getScheduleDate())){
            throw new IllegalArgumentException("Schedule date must be in the future!");
        }

        ScheduleDoctor scheduleDoctor = ScheduleDoctor.builder()
                .scheduleWork(schedule.getScheduleWork())
                .doctor(doctor)
                .scheduleDate(schedule.getScheduleDate())
                .isDelete(false)
                .isBooking(false)
                .build();
        scheduleRepository.save(scheduleDoctor);
        return true;

    }

    public List<ScheduleDoctor> getAll_Admin() {
        return scheduleRepository.findAll();
    }

    public List<ScheduleDTO> getAll() {
        return scheduleToDTO.toDTOList(scheduleRepository.findScheduleDoctorByIsDeleteFalse());

    }

    public ScheduleDTO getScheduleDTOById(long scheduleId) {
        return scheduleToDTO.toDTO(scheduleRepository.findById(scheduleId).orElseThrow(() -> new RuntimeException("Schedule not found!")));
    }

    public ScheduleDoctor getScheduleById(long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() -> new RuntimeException("Schedule not found!"));
    }

    public List<ScheduleDTO> getScheduleByDoctorId(String doctorId) {
        List<ScheduleDTO> schedules = getAll();
        List<ScheduleDTO> list = new java.util.ArrayList<>();
        for (ScheduleDTO scheduleDTO : schedules) {
            if (scheduleDTO.getDoctorId().equals(doctorId)) {
                list.add(scheduleDTO);
            }
        }
        return list;
    }

    public ScheduleDTO updateSchedule(ScheduleDoctor scheduleDoctor, long scheduleId) {
        ScheduleDoctor schedule = getScheduleById(scheduleId);
        schedule.setScheduleWork(scheduleDoctor.getScheduleWork());
        ScheduleDoctor updateSchedule = scheduleRepository.save(schedule);
        return scheduleToDTO.toDTO(updateSchedule);

    }

    public String deleteSchedule(long scheduleId, String doctorId) {
        ScheduleDoctor scheduleDoctor = getScheduleById(scheduleId);
        if (!scheduleDoctor.getDoctor().getUser_id().equals(doctorId)) {
            throw new IllegalArgumentException("You only delete by your own schedule");
        }
        scheduleDoctor.setDelete(true);
        scheduleRepository.save(scheduleDoctor);
        return "Delete Successful!";

    }

    public String deleteSchedule_Admin(long scheduleId) {
        ScheduleDoctor scheduleDoctor = getScheduleById(scheduleId);
        scheduleRepository.delete(scheduleDoctor);
        return "Delete Successful!";

    }

}
