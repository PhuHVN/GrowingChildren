package com.example.GrowChild.repository;

import com.example.GrowChild.entity.respone.Consulting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultingRepository extends JpaRepository<Consulting, Long> {
    List<Consulting> findConsultingByIsDeleteFalse();
    Consulting findConsultingByIsDeleteFalseAndConsultingId(long ConsultingId);
}
