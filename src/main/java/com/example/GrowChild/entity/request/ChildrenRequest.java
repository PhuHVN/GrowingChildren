package com.example.GrowChild.entity.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChildrenRequest {

    @NotBlank(message = "Name children not blank!")
    public String childrenName;

    @Min(value = 0, message = "Age greater than 0")
    @Max(value = 20)
    public int age;

    @Column(nullable = false)
    public String gender;
}
