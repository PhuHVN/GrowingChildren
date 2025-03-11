package com.example.GrowChild.entity.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBackRequest {
    @Max(value = 5)
    @Min(value = 0)
    public int rate;

    @NotBlank
    public String comment;

}
