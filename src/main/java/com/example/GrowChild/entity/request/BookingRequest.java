package com.example.GrowChild.entity.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    long scheduleId;
    String parentId;
    String comment;
}
