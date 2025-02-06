package com.example.GrowChild.api;

import com.example.GrowChild.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("email")
public class EmailSenderAPI {
    @Autowired
    private EmailSenderService senderService;


    @PostMapping("/send-email")
    public void mailSender(@RequestParam String to) {
        try {
            senderService.sendEmail(to);
            System.out.println("Sent successful to " + to);
        }catch (Exception e){
            System.err.println("Error while sent mail: " + e.getMessage());
        }
    }

    @PostMapping("/send-email_html")
    public void mailSenderHtml(@RequestParam String to, @RequestParam String code) {
        try {
            senderService.sendEmailWithHtml(to,code);
            System.out.println("Sent successful to " + to);
        }catch (Exception e){
            System.err.println("Error while sent mail: " + e.getMessage());
        }
    }

    @PostMapping("verifyOtp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String enterCode){
        boolean invalid = senderService.verifyOtp(email,enterCode);
        if(invalid){
           return  ResponseEntity.ok("Successful");
        }
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid OTP");
    }
}
