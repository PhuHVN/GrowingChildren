package com.example.GrowChild.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private long commentId;
    private long blogId;
    private String comment;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") // Format thời gian hiển thị
    private LocalDateTime date;
}
