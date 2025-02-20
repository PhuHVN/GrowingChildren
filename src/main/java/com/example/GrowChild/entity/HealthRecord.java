package com.example.GrowChild.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long record_id;

    @ManyToOne
    @JoinColumn(name = "childrenId", nullable = false)
    private Children child;

    @Min(value = 0)
    public double weight;

    @Min(value = 0)
    public double height;

    @Min(value = 0)
    @Max(value = 20)
    public int age;

    @Nullable
    public double bmi;

    public String gender;


    public LocalDate date;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    public User doctor;


    public boolean isDelete = false;


    @PrePersist
    @PreUpdate
    public void calculateBMI() {
        if (height > 0) {  // Đảm bảo chiều cao hợp lệ trước khi tính
            this.bmi = weight / (height * height);
        }
    }
}
