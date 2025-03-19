package com.example.GrowChild.dto;

import com.example.GrowChild.entity.enumStatus.BookingStatus;
import com.example.GrowChild.entity.response.Children;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    long bookId;

    String doctorId;

    String doctorName;

    String parentName;

    String parentId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    LocalTime scheduleWork;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime bookDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate scheduleDate;

    long childId;

    String childName;

    String comment;

    boolean isBooking;

    BookingStatus status;

}
