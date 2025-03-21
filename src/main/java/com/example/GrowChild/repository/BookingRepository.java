package com.example.GrowChild.repository;

import com.example.GrowChild.entity.response.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBookingByIsDeleteFalse();

    Booking findBookingByIsDeleteFalseAndBookId(long bookId);
}
