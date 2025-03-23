package com.example.GrowChild.service;

import com.example.GrowChild.dto.BookingDTO;
import com.example.GrowChild.entity.enumStatus.BookingStatus;
import com.example.GrowChild.entity.request.BookingRequest;
import com.example.GrowChild.entity.request.UpdateBookingRequest;
import com.example.GrowChild.entity.response.Booking;
import com.example.GrowChild.entity.response.Children;
import com.example.GrowChild.entity.response.ScheduleDoctor;
import com.example.GrowChild.entity.response.User;
import com.example.GrowChild.mapstruct.toDTO.BookToDTO;
import com.example.GrowChild.repository.BookingRepository;
import com.example.GrowChild.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    ScheduleRepository scheduleRepository;
    @Autowired
    BookToDTO bookToDTO;

    @Autowired
    ChildrenService childrenService;

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
        Children children = childrenService.getChildrenByIsDeleteFalseAndChildrenId(bookingRequest.getChildId());
        if (children == null) {
            throw new IllegalArgumentException("Child not found with ID: " + bookingRequest.getChildId());
        }

        Booking booking = Booking.builder()
                .schedule(scheduleDoctor)
                .parent(parent)
                .bookDate(LocalDateTime.now())
                .comment(bookingRequest.getComment())
                .bookingStatus(BookingStatus.PENDING)
                .children(children)
                .isDelete(false)
                .build();

        scheduleDoctor.setBooking(true);
        bookingRepository.save(booking);
        return true;
    }

    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public List<BookingDTO> getBookingsDTO() {
        return bookToDTO.toDTOList(bookingRepository.findBookingByIsDeleteFalse());
    }

    public BookingDTO getBookingDTOById(long id) {
        return bookToDTO.toDTO(getBookingById(id));
    }

    protected Booking getBookingById(long id) {
        Booking booking = bookingRepository.findBookingByIsDeleteFalseAndBookId(id);
        if (booking == null) {
            throw new RuntimeException("Booking not found!");
        }
        return booking;
    }

    public List<BookingDTO> getBookingDTOPendingByDoctorId(String doctorId) {
        List<BookingDTO> bookingPendingDoctor = new ArrayList<>();
        List<BookingDTO> list = getBookingsDTO();
        for (BookingDTO bookingDTO : list) {
            if (bookingDTO.getDoctorId().equals(doctorId) && bookingDTO.getStatus().equals(BookingStatus.PENDING)) {
                bookingPendingDoctor.add(bookingDTO);
            }
        }
        return bookingPendingDoctor;
    }

    public List<BookingDTO> getBookingDTOByDoctorId(String doctorId) {
        List<BookingDTO> bookingHistoryDoctor = new ArrayList<>();
        List<BookingDTO> list = getBookingsDTO();
        for (BookingDTO bookingDTO : list) {
            if (bookingDTO.getDoctorId().equals(doctorId) ) {
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

    public String deleteBooking_Soft(long id) {
        Booking booking = getBookingById(id);
        booking.setDelete(true);
        bookingRepository.save(booking);
        return "Delete Successful!";
    }

    public String deleteBooking_Admin(long id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
        return "Delete Successful!";
    }

    public String cancelledBooking_User(long id, String parentId) {
        Booking booking = getBookingById(id);

        if (!booking.getParent().getUser_id().equals(parentId)) {
            throw new IllegalArgumentException("You only delete by your own booking");
        }
        if(!booking.getBookingStatus().equals(BookingStatus.PENDING)){
            throw new IllegalArgumentException("You only delete pending booking");
        }
        setBookingFalseByScheduleId(booking.getSchedule().getScheduleId());
        booking.setBookingStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);
        return "Cancelled Successful!";
    }

    public String cancelledBooking_Doctor(long id, String doctorId){
        Booking booking = getBookingById(id);
        if (!booking.getSchedule().getDoctor().getUser_id().equals(doctorId)) {
            throw new IllegalArgumentException("You only delete by your own booking");
        }
        if(!booking.getBookingStatus().equals(BookingStatus.PENDING)){
            throw new IllegalArgumentException("You only delete pending booking");
        }
        booking.setBookingStatus(BookingStatus.CANCELLED);
        setBookingFalseByScheduleId(booking.getSchedule().getScheduleId());
        bookingRepository.save(booking);
        return "Cancelled Successful!";
    }

    //set schedule booking false for scheduleId
    public void setBookingFalseByScheduleId(long scheduleId) {
        ScheduleDoctor scheduleWork = scheduleService.getScheduleById(scheduleId);
        scheduleWork.setBooking(false);
        scheduleRepository.save(scheduleWork);
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


    @Scheduled(fixedRate = 600000)
    public void checkBookingTime() {
        List<Booking> bookings = bookingRepository.findAll();
        for (Booking booking : bookings) {
            if (booking.getSchedule().getScheduleDate().isBefore(LocalDate.now()) || booking.getSchedule().getScheduleWork().isAfter(LocalTime.now().plusMinutes(10))) {
                booking.setBookingStatus(BookingStatus.CANCELLED);
                bookingRepository.save(booking);
            }
        }
    }



}
