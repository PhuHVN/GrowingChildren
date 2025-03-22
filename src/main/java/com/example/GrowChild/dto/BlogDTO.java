package com.example.GrowChild.dto;


import com.example.GrowChild.entity.enumStatus.BlogStatus;
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

    private String hashtag;

    private String content;

    private String fullName;

    private String parentId;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") // Format thời gian hiển thị
    private LocalDateTime date;

    private BlogStatus status;
}
