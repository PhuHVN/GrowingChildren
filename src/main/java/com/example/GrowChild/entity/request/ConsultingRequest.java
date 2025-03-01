package com.example.GrowChild.entity.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultingRequest {
    @NotBlank
    public String title;

    @NotBlank
    public String comment;
}
