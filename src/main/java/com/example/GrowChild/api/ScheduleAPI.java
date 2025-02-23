package com.example.GrowChild.api;

import com.example.GrowChild.dto.ScheduleDTO;
import com.example.GrowChild.entity.ScheduleDoctor;
import com.example.GrowChild.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ScheduleWorking")
public class ScheduleAPI {
    @Autowired
    ScheduleService scheduleService;


    @PostMapping("createSchedule")
    public ResponseEntity createSchedule(@Valid @RequestBody ScheduleDoctor scheduleDoctor, @RequestParam String doctor){
        boolean isCreated = scheduleService.createSchedule(scheduleDoctor, doctor);

        if (!isCreated) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating schedule!");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleDoctor);
    }

    @GetMapping("getAll_Admin")
    public List<ScheduleDoctor> scheduleDoctorList(){
        return scheduleService.getAll_Admin();
    }

    @GetMapping("getAllDTO")
    public List<ScheduleDTO> scheduleDTOList(){
        return scheduleService.getAll();
    }

    @GetMapping("getScheduleDTOById/{scheduleId}")
    public ScheduleDTO scheduleDTO(long scheduleId){
        return scheduleService.getScheduleDTOById(scheduleId);
    }
    @GetMapping("getScheduleById/{scheduleId}")
    public ScheduleDoctor schedule(long scheduleId){
        return scheduleService.getScheduleById(scheduleId);
    }

   //update

    @DeleteMapping("deleteSchedule_Admin/{scheduleId}")
    public String deleteSchedule_Admin(long scheduleId){
        return scheduleService.deleteSchedule_Admin(scheduleId);
    }

    @DeleteMapping("deleteSchedule/{scheduleId}")
    public String deleteSchedule(long scheduleId){
        return scheduleService.deleteSchedule(scheduleId);
    }


}
