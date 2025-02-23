package com.example.GrowChild.entity.respone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTP {
    public String code;
    public LocalDateTime expirationTime;

}
