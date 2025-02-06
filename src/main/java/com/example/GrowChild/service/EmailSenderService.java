package com.example.GrowChild.service;

import com.example.GrowChild.entity.OTP;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    private Map<String, OTP> otpStore = new HashMap<>();

    public void saveOTP(String email, String code){
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5); //time now + 5min = expiration time
        OTP otp = new OTP(code,expirationTime);
        otpStore.put(email,otp); // add in map
    }

    public String generateCode(){
        return String.valueOf((int)(Math.random() * 1000000)); // random 6 char number
    }

    public boolean verifyOtp(String email ,String code){
        OTP otp = otpStore.get(email); //get otp from key email
        if(otp != null && otp.getExpirationTime().isAfter(LocalDateTime.now())){ //check time + 5min < time now
            return otp.getCode().equals(code); //
        }
        return false; //ko dung or het han
    }
    public void sendEmail(String toMail) {
        Random random = new Random();
        int code = random.nextInt(1000000);// random with 6 char number
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("phuhvnse182371@fpt.edu.vn"); // email company
            msg.setTo(toMail); // to register user email
            msg.setSubject("Mã xác nhận đăng nhập Website Tracking Grow cho trẻ "); // title

            msg.setText("Xin chào " + toMail +" ,\n" + // content
                    "\n" +
                    "Chúng tôi rất vui được chào đón bạn đến với **Tracking Grow** " +
                    "\nPhần mềm hỗ trợ theo dõi và phát triển toàn diện dành cho trẻ! Đây là nền tảng giúp phụ huynh dễ dàng quản lý, theo dõi sức khỏe, chiều cao, cân nặng của trẻ.\n" +
                    "\n" +
                    "---\n" +
                    "\n" +
                    "**Mã xác nhận đăng nhập của bạn:**  \n" +
                    " "+ generateCode() +"  \n" +
                    "(Lưu ý: Mã này sẽ hết hạn sau 10 phút, vui lòng sử dụng ngay để hoàn tất đăng nhập.)");

            mailSender.send(msg);
            System.out.println("Mail sent successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Log error to debug
            System.err.println("Error while sending mail: " + e.getMessage());
        }
    }

    public void sendEmailWithHtml(String toMail,String otp) {

        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg,true); // use format html

            helper.setFrom("phuhvnse182371@fpt.edu.vn"); // email company
            helper.setTo(toMail); // to register user email
            helper.setSubject("Mã xác nhận đăng nhập Website Tracking Grow cho trẻ "); // title

            String htmlContent = "<html><head>"
                    + "<style>"
                    + "body { font-family: Arial, sans-serif; font-size: 16px; color: #333; background-color: #f4f4f9; }"  // Light grey background
                    + "h1 { color: #4CAF50; font-size: 24px; }"
                    + "strong { color: #2C3E50; }"
                    + "p { margin: 10px 0; }"
                    + "hr { border: 1px solid #ccc; margin: 20px 0; }"
                    + "</style>"
                    + "</head><body>"
                    + "<p>Xin chào <strong>" + toMail + "</strong> ,</p>"
                    + "<p>Chúng tôi rất vui được chào đón bạn đến với <strong>Tracking Grow</strong>! </p>"
                    + "<p>Phần mềm hỗ trợ theo dõi và phát triển toàn diện dành cho trẻ! Đây là nền tảng giúp phụ huynh dễ dàng quản lý, theo dõi sức khỏe, chiều cao, cân nặng của trẻ.</p>"
                    + "<hr>"
                    + "<p><strong>Mã xác nhận đăng nhập của bạn:</strong></p>"
                    + "<h1><strong>" + otp + "</strong></h1>"
                    + "<p>(Lưu ý: Mã này sẽ hết hạn sau 5 phút, vui lòng sử dụng ngay để hoàn tất đăng nhập.)</p>"
                    + "</body></html>";


            helper.setText(htmlContent,true);

            mailSender.send(msg);
            System.out.println("Mail sent successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Log error to debug
            System.err.println("Error while sending mail: " + e.getMessage());
        }
    }
}
