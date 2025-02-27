package com.example.GrowChild.entity.request;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

    long scheduleId;
    String parentId;

    @Column(nullable = true)
    String comment;
}
