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
    private LocalDate date;
    private int age;
    private String childName;
    private Long childId;
    private String parentName;
    private String parentId;
}
