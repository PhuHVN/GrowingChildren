package com.example.GrowChild.service;

import com.example.GrowChild.dto.BookingDTO;
import com.example.GrowChild.entity.enumStatus.BookingStatus;
import com.example.GrowChild.entity.request.BookingRequest;
import com.example.GrowChild.entity.request.UpdateBookingRequest;
import com.example.GrowChild.entity.response.Booking;
import com.example.GrowChild.entity.response.ScheduleDoctor;
import com.example.GrowChild.entity.response.User;
import com.example.GrowChild.mapstruct.toDTO.BookToDTO;
import com.example.GrowChild.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public boolean createBooking(BookingRequest bookingRequest) {
        User parent = userService.getUser(bookingRequest.getParentId());
        if (parent == null || !parent.getRole().getRoleName().equals("Parent")) {
            throw new IllegalArgumentException("Parent not found!");
        }
        ScheduleDoctor scheduleDoctor = scheduleService.getScheduleById(bookingRequest.getScheduleId());
        if (scheduleDoctor == null) {
            throw new IllegalArgumentException("This schedule not found!");
        }
        if (scheduleDoctor.isBooking()) {
            throw new IllegalArgumentException("This book is already booked");
        }

        Booking booking = Booking.builder()
                .schedule(scheduleDoctor)
                .parent(parent)
                .bookDate(LocalDateTime.now())
                .comment(bookingRequest.getComment())
                .bookingStatus(BookingStatus.PENDING)
                .build();

        scheduleDoctor.setBooking(true);
        bookingRepository.save(booking);
        return true;
    }

    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public List<BookingDTO> getBookingsDTO() {
        return bookToDTO.toDTOList(bookingRepository.findAll());
    }

    public BookingDTO getBookingDTOById(long id) {
        return bookToDTO.toDTO(getBookingById(id));
    }

    protected Booking getBookingById(long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found!"));
    }

    public List<BookingDTO> getBookingDTOPendingByDoctorId(String doctorId) {
        List<BookingDTO> bookinPendingoctor = new ArrayList<>();
        List<BookingDTO> list = getBookingsDTO();
        for (BookingDTO bookingDTO : list) {
            if (bookingDTO.getDoctorId().equals(doctorId) && bookingDTO.getStatus().equals(BookingStatus.PENDING)) {
                bookinPendingoctor.add(bookingDTO);
            }
        }
        return bookinPendingoctor;
    }

    public List<BookingDTO> getBookingDTOByDoctorId(String doctorId) {
        List<BookingDTO> bookingHistoryDoctor = new ArrayList<>();
        List<BookingDTO> list = getBookingsDTO();
        for (BookingDTO bookingDTO : list) {
            if (bookingDTO.getDoctorId().equals(doctorId)) {
                bookingHistoryDoctor.add(bookingDTO);
            }
        }
        return bookingHistoryDoctor;
    }

    public List<BookingDTO> getBookingDTOByParentId(String parentId) {
        List<BookingDTO> bookingHistoryParent = new ArrayList<>();
        List<BookingDTO> list = getBookingsDTO();
        for (BookingDTO bookingDTO : list) {
            if (bookingDTO.getParentId().equals(parentId)) {
                bookingHistoryParent.add(bookingDTO);
            }
        }
        return bookingHistoryParent;
    }

    public BookingDTO doctorConfirmBooking(long bookId) {
        Booking booking = getBookingById(bookId);
        if (!BookingStatus.PENDING.equals(booking.getBookingStatus())) {
            throw new IllegalArgumentException("Only pending booking can be confirm!");
        }
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        Booking confirmBooking = bookingRepository.save(booking);
        return bookToDTO.toDTO(confirmBooking);
    }

    public Booking updateBooking(UpdateBookingRequest bookingRequest) {
        Booking booking = getBookingById(bookingRequest.getId());
        if (booking == null) throw new RuntimeException("Booking not found!");
        booking.setComment(bookingRequest.getComment());
        bookingRepository.save(booking);
        return booking;
    }


    public String deleteBooking_Admin(long id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
        return "Delete Successful!";
    }

    public String deleteBooking_User(long id, String parentId) {
        Booking booking = getBookingById(id);

        if (!booking.getParent().getUser_id().equals(parentId)) {
            throw new IllegalArgumentException("You only delete by your own booking");
        }
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        return "Delete Successful!";
    }

    //set schedule booking false for scheduleId
    public void scheduleBookingDone(long scheduleId) {
        ScheduleDoctor doctor = scheduleService.getScheduleById(scheduleId);
        doctor.setBooking(false);
    }


    //Booking complete for bookingId
    public boolean bookingComplete(long bookingId) {
        Booking booking = getBookingById(bookingId);
        if (booking.getBookingStatus().equals(BookingStatus.CONFIRMED)) {
            throw new IllegalArgumentException("Booking is confirmed!");
        }
        booking.setBookingStatus(BookingStatus.COMPLETED);
        booking.getSchedule().setBooking(false);
        bookingRepository.save(booking);
        return false;
    }

}
