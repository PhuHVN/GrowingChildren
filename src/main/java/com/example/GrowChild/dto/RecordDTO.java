package com.example.GrowChild.dto;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordDTO {
    private Long recordId;
    private double weight;
    private double height;
    private double bmi;
    private int age;
    private LocalDate date;
    private String childName;
    private Long childId;
    private String doctorName;
    private String doctorId;
}
