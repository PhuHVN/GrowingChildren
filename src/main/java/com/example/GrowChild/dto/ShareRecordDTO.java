package com.example.GrowChild.dto;

import com.example.GrowChild.entity.response.Consulting;
import com.example.GrowChild.entity.response.HealthRecord;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ShareRecordDTO {
    private long shareRecordId;
    private long consulting;
    private long healthRecord;
    private double weight;
    private double height;
    private double bmi;

}
