package com.example.GrowChild.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChildDTO {
    private long childrenId;
    private String childrenName;
    private int age;
    private String gender;
    private String parentId;
    private String parentName;
    private boolean isDelete;


}
