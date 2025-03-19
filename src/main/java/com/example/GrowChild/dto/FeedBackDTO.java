package com.example.GrowChild.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBackDTO {
    private long feedbackId;
    private long consultingId;
    private int rate;
    private String comment;
    private String fullNameParent;
    private String parentId;
    private String fullNameDoctor;
    private String doctorId;




}
