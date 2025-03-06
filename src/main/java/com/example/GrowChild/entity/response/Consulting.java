package com.example.GrowChild.entity.response;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Consulting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long consultingId;

    @NotBlank
    public String title;

    @NotBlank
    public String comment;

    public boolean isDelete = false;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    public Booking bookingId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    public User doctorId;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    public User parentId;

    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    public Children childId;

    public LocalDateTime date;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDateTime.now();
    }
}
