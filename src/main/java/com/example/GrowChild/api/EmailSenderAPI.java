package com.example.GrowChild.api;

import com.example.GrowChild.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
