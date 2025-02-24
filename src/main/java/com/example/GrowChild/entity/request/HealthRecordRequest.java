package com.example.GrowChild.entity.request;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class HealthRecordRequest {

    @Min(value = 0)
    public double weight;

    @Min(value = 0)
    public double height;


}
