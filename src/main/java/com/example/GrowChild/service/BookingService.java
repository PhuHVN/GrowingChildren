package com.example.GrowChild.service;

import com.example.GrowChild.dto.BookingDTO;
import com.example.GrowChild.dto.BookingStatus;
import com.example.GrowChild.dto.GrowthStatus;
import com.example.GrowChild.entity.request.BookingRequest;
import com.example.GrowChild.entity.respone.Booking;
import com.example.GrowChild.entity.respone.ScheduleDoctor;
import com.example.GrowChild.entity.respone.User;
import com.example.GrowChild.mapstruct.toDTO.BookToDTO;
import com.example.GrowChild.repository.BookingRepository;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    UserService userService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    BookToDTO bookToDTO;

    public boolean createBooking(BookingRequest bookingRequest){
        User parent = userService.getUser(bookingRequest.getParentId());
        if(parent == null || ! parent.getRole().getRoleName().equals("Parent")){
            throw new RuntimeException("Parent not found!");
        }
        ScheduleDoctor scheduleDoctor = scheduleService.getScheduleById(bookingRequest.getScheduleId());
        if (scheduleDoctor == null){
            throw new RuntimeException("schedule not found!");
        }
        Booking booking = Booking.builder()
                .schedule(scheduleDoctor)
                .parent(parent)
                .bookDate(LocalDateTime.now())
                .comment(bookingRequest.getComment())
                .bookingStatus(BookingStatus.PENDING)
                .build();

        bookingRepository.save(booking);
        return true;
    }

    public List<Booking> getBookings(){
        return bookingRepository.findAll();
    }

    public List<BookingDTO> getBookingsDTO(){
        return bookToDTO.toDTOList(bookingRepository.findAll());
    }

    public BookingDTO getBookingDTOById(long id){
        return bookToDTO.toDTO(getBookingById(id));
    }

    private Booking getBookingById(long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found!"));
    }

    public BookingDTO doctorConfirmBooking(long bookId){
        Booking booking = getBookingById(bookId);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        Booking confirmBooking = bookingRepository.save(booking);
        return bookToDTO.toDTO(confirmBooking);
    }

    public Booking updateBooking(long id){
        return null;
    }

    public String deleteBooking_Admin(long id){
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
        return "Delete Successful!";
    }

    public String deleteBooking_User(long id){
        Booking booking = getBookingById(id);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        return "Delete Successful!";
    }

}
