package com.example.GrowChild.repository;

import com.example.GrowChild.entity.response.ScheduleDoctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository< ScheduleDoctor, Long> {

    List<ScheduleDoctor> findScheduleDoctorByIsDeleteFalse();
}
