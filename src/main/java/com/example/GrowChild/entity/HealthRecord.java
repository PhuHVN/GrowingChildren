package com.example.GrowChild.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

public class HealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String record_id;

    public String child_id;

    @Min(value = 0)
    public double weight;

    @Min(value = 0)
    public double height;

    public LocalDateTime create_at;

    public String doctor_id;

}
