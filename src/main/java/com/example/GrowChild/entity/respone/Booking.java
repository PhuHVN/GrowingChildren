package com.example.GrowChild.entity.respone;

import com.example.GrowChild.dto.BookingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    @ManyToOne
    @JoinColumn(name = "schedule_id",nullable = false)
    private ScheduleDoctor schedule;

    @ManyToOne
    @JoinColumn(name = "parent_id",nullable = false)
    private User parent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime bookDate;

    @Column(nullable = true)
    String comment;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;



}
