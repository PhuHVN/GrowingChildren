package com.example.GrowChild.dto;


import lombok.*;

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
}
