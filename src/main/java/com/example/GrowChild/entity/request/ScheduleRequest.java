package com.example.GrowChild.entity.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime scheduleWork;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;


}
