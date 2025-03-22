package com.example.GrowChild.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultingDTO {
    private long consultingId;

    private String title;

    private String comment;

    private long bookingId;

    private String doctorId;

    private String parentId;

    private long childId;

    private String nameParent;

    private String nameDoctor;

    private String nameChild;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") // Format thời gian hiển thị
    private LocalDateTime date;
}
