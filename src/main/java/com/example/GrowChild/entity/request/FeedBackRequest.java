package com.example.GrowChild.entity.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBackRequest {
    @NotBlank
    public int rate;

    @NotBlank
    public String comment;

}
