package com.example.GrowChild.entity.response;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long feedbackId;

    @Max(value = 5)
    @Min(value = 0)
    public int rate;

    @NotBlank
    public String comment;

    public boolean isDelete = false;

    @OneToOne
    @JoinColumn(name = "consulting_id", nullable = false)
    public Consulting consultingId;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    public User parentId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    public User doctorId;

}
