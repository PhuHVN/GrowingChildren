package com.example.GrowChild.repository;

import com.example.GrowChild.entity.Children;
import com.example.GrowChild.entity.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HealthRecordRepository extends JpaRepository<HealthRecord,Long> {
    List<HealthRecord> findHealthRecordByIsDeleteFalse();
    List<HealthRecord> findByChildChildrenIdOrderByDateAsc(Long childId);

}
