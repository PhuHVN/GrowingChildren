package com.example.GrowChild.api;

import com.example.GrowChild.dto.BookingDTO;
import com.example.GrowChild.entity.request.BookingRequest;
import com.example.GrowChild.entity.response.Booking;
import com.example.GrowChild.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("BookingAPI")
public class BookingAPI {

    @Autowired
    BookingService bookingService;

    @PostMapping("createBooking")
    public ResponseEntity createBooking(@Valid @RequestBody BookingRequest bookingRequest){
        if(bookingService.createBooking(bookingRequest)){
            return new ResponseEntity<>(bookingRequest, HttpStatus.CREATED);
        };
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cant create booking");
    }

    @GetMapping("getAllBooking-admin")
    public List<Booking> getBookingAdmin(){
        return bookingService.getBookings();
    }

    @GetMapping("getAllBooking-user")
    public List<BookingDTO> getBookingUser(){
        return bookingService.getBookingsDTO();
    }

    @GetMapping("getBooking")
    public BookingDTO getBooking(@RequestParam long bookId){
        return bookingService.getBookingDTOById(bookId);
    }

    @GetMapping("historyBooking-doctor")
    public List<BookingDTO> getBookingHistory_Doctor(@RequestParam String doctorId){
        return bookingService.getBookingDTOByDoctorId(doctorId);
    }

    @GetMapping("historyBooking-parent")
    public List<BookingDTO> getBookingHistory_Parent(@RequestParam String parentId){
        return bookingService.getBookingDTOByParentId(parentId);
    }

    @GetMapping("bookingPending-doctor")
    public List<BookingDTO> getBookingPending(@RequestParam String doctorId){
        return bookingService.getBookingDTOPendingByDoctorId(doctorId);
    }

    @PutMapping("confirmBooking")
    public BookingDTO confirmBooking(@RequestParam long bookId){
        return bookingService.doctorConfirmBooking(bookId);
    }

    @PutMapping("updateBooking")
    public Booking updateBooking(@RequestBody long bookId,@RequestBody String comment){
        return bookingService.updateBooking(bookId,comment);
    }


    @DeleteMapping("delete-admin")
        public String delete_admin(@RequestParam long bookId){
        return bookingService.deleteBooking_Admin(bookId);
    }



    @DeleteMapping("delete-user")
    public String delete_user(@RequestParam long bookId,@RequestParam String parentId){
        return bookingService.deleteBooking_User(bookId,parentId);
    }




}
