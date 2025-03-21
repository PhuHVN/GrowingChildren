package com.example.GrowChild.entity.request;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class HealthRecordRequest {

    @Min(value = 0)
    public double weight_kg;

    @Min(value = 0)
    public double height_m;

    LocalDate date;


}
