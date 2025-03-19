package com.example.GrowChild.entity.response;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long recordId;

    @ManyToOne
    @JoinColumn(name = "childrenId", nullable = false)
    Children child;

    @Min(value = 0)
    double weight;

    @Min(value = 0)
    double height;

    @Nullable
    double bmi;

    int age;

    LocalDate date;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    User parent;


    boolean isDelete = false;


    @PrePersist
    @PreUpdate
    void calculateBMI() {
        if (height > 0) { // cant div 0
            this.bmi = weight / (height * height);
        }
    }
}
