package com.example.GrowChild.repository;

import com.example.GrowChild.entity.response.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthRecordRepository extends JpaRepository<HealthRecord,Long> {
    List<HealthRecord> findHealthRecordByIsDeleteFalse();
    List<HealthRecord> findByChildChildrenIdOrderByDateAsc(Long childId);

}
