package com.example.GrowChild.entity.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleDoctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long scheduleId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    User doctor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    LocalTime scheduleWork;

    boolean isDelete;

    boolean isBooking;

    @OneToMany(mappedBy = "bookId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Booking> booking;
}
