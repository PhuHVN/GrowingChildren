//package com.example.GrowChild.entity;
//
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import lombok.*;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class FeedBack {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public int feedbackId;
//
//    @NotBlank
//    public int rate;
//
//    @NotBlank
//    public String comment;
//
//    @ManyToOne
//    @JoinColumn(name = "parent_id",nullable = false)
//    public User parentId;
//
//    @ManyToOne
//    @JoinColumn(name = "doctor_id",nullable = false)
//    public User doctorId;
//
//}
