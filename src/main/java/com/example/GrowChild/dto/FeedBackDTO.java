package com.example.GrowChild.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

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
    private String parentId;
    private String doctorId;

}
