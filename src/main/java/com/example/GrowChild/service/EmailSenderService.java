package com.example.GrowChild.service;

import com.example.GrowChild.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserRepository userRepository;

    public String generateCode() {
        return String.valueOf((int) (Math.random() * 1000000)); // random 6 char number
    }


    public void sendEmail(String toMail) {
        Random random = new Random();
        int code = random.nextInt(1000000);// random with 6 char number
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("phuhvnse182371@fpt.edu.vn"); // email company
            msg.setTo(toMail); // to register user email
            msg.setSubject("Mã xác nhận đăng nhập Website Tracking Grow cho trẻ "); // title

            msg.setText("Xin chào " + toMail + " ,\n" + // content
                    "\n" +
                    "Chúng tôi rất vui được chào đón bạn đến với **Tracking Grow** " +
                    "\nPhần mềm hỗ trợ theo dõi và phát triển toàn diện dành cho trẻ! Đây là nền tảng giúp phụ huynh dễ dàng quản lý, theo dõi sức khỏe, chiều cao, cân nặng của trẻ.\n" +
                    "\n" +
                    "---\n" +
                    "\n" +
                    "**Mã xác nhận đăng nhập của bạn:**  \n" +
                    " " + generateCode() + "  \n" +
                    "(Lưu ý: Mã này sẽ hết hạn sau 10 phút, vui lòng sử dụng ngay để hoàn tất đăng nhập.)");

            mailSender.send(msg);
            System.out.println("Mail sent successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Log error to debug
            System.err.println("Error while sending mail: " + e.getMessage());
        }
    }

    public void resetPasswordEmail(String toMail, String otp){
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true); // use format html

            helper.setFrom("phuhvnse182371@fpt.edu.vn"); // email company
            helper.setTo(toMail); // to register user email
            helper.setSubject("Mã xác nhận đổi mật khẩu Website Tracking Grow cho trẻ "); // title
            String htmlContent = "<html><head>"
                    + "<meta charset=\"UTF-8\">"
                    + "<style>"
                    + "body { font-family: Arial, sans-serif; font-size: 16px; color: #333; background-color: #f4f4f9; padding: 20px; }"
                    + ".container { max-width: 600px; margin: 0 auto; background: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }"
                    + "h1 { color: #4CAF50; font-size: 24px; margin-bottom: 20px; }"
                    + "p { margin: 10px 0; line-height: 1.5; }"
                    + "strong { color: #2C3E50; }"
                    + "hr { border: 0; height: 1px; background: #ccc; margin: 20px 0; }"
                    + ".otp-code { font-size: 28px; font-weight: bold; color: #E53935; text-align: center; margin: 20px 0; }"
                    + ".note { font-size: 14px; color: #777; margin-top: 10px; }"
                    + "</style>"
                    + "</head><body>"
                    + "<div class=\"container\">"
                    + "<p>Xin chào <strong>" + toMail + "</strong>,</p>"
                    + "<p>Chúng tôi rất vui được chào đón bạn đến với <strong>Tracking Grow</strong>!</p>"
                    + "<p>Phần mềm hỗ trợ theo dõi và phát triển toàn diện dành cho trẻ. Đây là nền tảng giúp phụ huynh dễ dàng quản lý, theo dõi sức khỏe, chiều cao, cân nặng của trẻ.</p>"
                    + "<hr>"
                    + "<p><strong>Mã xác nhận đổi mật khẩu của bạn:</strong></p>"
                    + "<div class=\"otp-code\">" + otp + "</div>"
                    + "<p class=\"note\">(Lưu ý: Mã này sẽ hết hạn sau 5 phút, vui lòng sử dụng ngay để hoàn tất đổi mật khẩu.)</p>"
                    + "</div>"
                    + "</body></html>";
            helper.setText(htmlContent,true);
            mailSender.send(msg);
            System.out.println("Mail sent successfully!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void sendEmailWithHtml(String toMail, String otp) {

        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true); // use format html

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


            helper.setText(htmlContent, true);

            mailSender.send(msg);
            System.out.println("Mail sent successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Log error to debug
            System.err.println("Error while sending mail: " + e.getMessage());
        }
    }
}
