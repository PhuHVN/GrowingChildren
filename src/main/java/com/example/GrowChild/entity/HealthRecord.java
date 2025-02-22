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

    @Nullable
    public double bmi;

    public int age;

    public LocalDate date;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    public User parent;


    public boolean isDelete = false;


    @PrePersist
    @PreUpdate
    public void calculateBMI() {
        if (height > 0) { // cant div 0
            this.bmi = weight / (height * height);
        }
    }
}
