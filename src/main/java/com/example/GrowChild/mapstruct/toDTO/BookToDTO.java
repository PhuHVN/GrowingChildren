package com.example.GrowChild.mapstruct.toDTO;

import com.example.GrowChild.dto.BookingDTO;
import com.example.GrowChild.entity.response.Booking;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookToDTO {

    default BookingDTO toDTO(Booking booking) {
        return BookingDTO.builder()
                .bookId(booking.getBookId())
                .doctorId(booking.getSchedule().getDoctor().getUser_id())
                .doctorName(booking.getSchedule().getDoctor().getFullName())
                .parentName(booking.getParent().getFullName())
                .parentId(booking.getParent().getUser_id())
                .bookDate(booking.getBookDate())
                .scheduleWork(booking.getSchedule().getScheduleWork())
                .scheduleDate(booking.getSchedule().getScheduleDate())
                .comment(booking.getComment())
                .status(booking.getBookingStatus())
                .build();
    }

    default List<BookingDTO> toDTOList(List<Booking> bookings) {
        return bookings.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
