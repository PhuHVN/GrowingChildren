package com.example.GrowChild.api;

import com.example.GrowChild.dto.ScheduleDTO;
import com.example.GrowChild.entity.request.ScheduleRequest;
import com.example.GrowChild.entity.response.ScheduleDoctor;
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
    public ResponseEntity createSchedule(@Valid @RequestBody ScheduleRequest scheduleDoctor, @RequestParam String doctor) {
        boolean isCreated = scheduleService.createSchedule(scheduleDoctor, doctor);
        if (!isCreated) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating schedule!");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleDoctor);
    }

    @GetMapping("schedules-admin")
    public List<ScheduleDoctor> scheduleDoctorList() {
        return scheduleService.getAll_Admin();
    }

    @GetMapping("schedulesDTO")
    public List<ScheduleDTO> scheduleDTOList() {
        return scheduleService.getAll();
    }

    @GetMapping("scheduleDTO/{scheduleId}")
    public ScheduleDTO scheduleDTO(@PathVariable long scheduleId) {
        return scheduleService.getScheduleDTOById(scheduleId);
    }

    @GetMapping("schedule/{scheduleId}")
    public ScheduleDoctor getSchedule(@PathVariable long scheduleId) {
        return scheduleService.getScheduleById(scheduleId);
    }

    @GetMapping("scheduleDoctor")
    public ScheduleDTO getSchedule(@RequestParam String doctorId) {
        return scheduleService.getScheduleByDoctorId(doctorId);
    }

    @PutMapping("schedule/{scheduleId}")
    public ScheduleDTO updateSchedule(@Valid @RequestBody ScheduleDoctor scheduleDoctor, @PathVariable long scheduleId) {
        return scheduleService.updateSchedule(scheduleDoctor, scheduleId);
    }

    @DeleteMapping("deleteSchedule-admin/{scheduleId}")
    public String deleteSchedule_Admin(@PathVariable long scheduleId) {
        return scheduleService.deleteSchedule_Admin(scheduleId);
    }

    @DeleteMapping("deleteSchedule/{scheduleId}")
    public String deleteSchedule(@PathVariable long scheduleId, @RequestParam String doctorId) {
        return scheduleService.deleteSchedule(scheduleId, doctorId);
    }


}
