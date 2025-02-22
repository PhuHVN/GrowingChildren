package com.example.GrowChild.repository;

import com.example.GrowChild.entity.Children;
import com.example.GrowChild.entity.ScheduleDoctor;
import com.mysql.cj.log.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository< ScheduleDoctor, Long> {

    List<ScheduleDoctor> findSchdeduleDoctorByIsDeleteFalse();
}
