package com.example.GrowChild.repository;

import com.example.GrowChild.entity.response.Booking;
import com.example.GrowChild.entity.response.Consulting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultingRepository extends JpaRepository<Consulting, Long> {
    List<Consulting> findConsultingByIsDeleteFalse();

    Consulting findConsultingByIsDeleteFalseAndConsultingId(long ConsultingId);

    List<Consulting> findConsultingByIsDeleteFalseAndBookingId(Booking booking);
}
