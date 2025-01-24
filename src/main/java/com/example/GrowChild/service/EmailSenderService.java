package com.example.GrowChild.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;




    public void sendEmail(String toMail) {
        Random random = new Random();
        int code = random.nextInt(1000000);
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("phuhvnse182371@fpt.edu.vn");
            msg.setTo(toMail);
            msg.setText("Xin chào " + toMail +" ,\n" +
                    "\n" +
                    "Chúng tôi rất vui được chào đón bạn đến với **Tracking Grow** " +
                    "\nPhần mềm hỗ trợ theo dõi và phát triển toàn diện dành cho trẻ! Đây là nền tảng giúp phụ huynh dễ dàng quản lý, theo dõi sức khỏe, chiều cao, cân nặng của trẻ.\n" +
                    "\n" +
                    "---\n" +
                    "\n" +
                    "**Mã xác nhận đăng nhập của bạn:**  \n" +
                    " "+code+"  \n" +
                    "(Lưu ý: Mã này sẽ hết hạn sau 10 phút, vui lòng sử dụng ngay để hoàn tất đăng nhập.)");
            msg.setSubject("Mã xác nhận đăng nhập Website Tracking Grow cho trẻ ");

            mailSender.send(msg);
            System.out.println("Mail sent successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Log the full stack trace for debugging
            System.err.println("Error while sending mail: " + e.getMessage());
        }
    }
}
