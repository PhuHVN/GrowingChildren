package com.example.GrowChild.entity.response;

import com.example.GrowChild.entity.enumStatus.BookingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long bookId;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    ScheduleDoctor schedule;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    User parent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime bookDate;

    @Column(nullable = true)
    String comment;

    @Enumerated(EnumType.STRING)
    BookingStatus bookingStatus;


}
