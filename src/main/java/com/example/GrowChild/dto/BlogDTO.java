package com.example.GrowChild.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {
    private long blogId;


    private String title;


    private String description;


    private String content;


    private String parentId;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") // Format thời gian hiển thị
    private LocalDateTime date;
}
