package com.example.GrowChild.api;

import com.example.GrowChild.entity.enumStatus.GrowthStatus;
import com.example.GrowChild.entity.enumStatus.MembershipType;
import com.example.GrowChild.entity.enumStatus.PaymentStatus;
import com.example.GrowChild.entity.respone.Membership;
import com.example.GrowChild.service.MembershipService;
import com.example.GrowChild.service.PaymentService;
import com.example.GrowChild.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class VNPayAPI {

    @Autowired
    private VNPayService vnPayService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    MembershipService membershipService;

    @PostMapping("/submitOrder")
    public String submitOrder(@RequestParam("amount") int price,
                              @RequestParam("Status") MembershipType type,
                              @RequestParam("userId") String userId,
                              HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        System.out.println(baseUrl);
        String vnpayUrl = vnPayService.createOrder(price, type, userId,baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model) {
        String responseCode = request.getParameter("vnp_ResponseCode"); // Mã phản hồi VNPay
        String txnRef = request.getParameter("vnp_TxnRef"); // Mã giao dịch của mình
        String transactionId = request.getParameter("vnp_TransactionNo"); // Mã giao dịch từ VNPay
        String paymentTime = request.getParameter("vnp_PayDate"); // Thời gian thanh toán
        String totalPrice = request.getParameter("vnp_Amount"); // Số tiền thanh toán
        String orderInfo = request.getParameter("vnp_OrderInfo"); // Dữ liệu thêm của đơn hàng
        String secureHash = request.getParameter("vnp_SecureHash"); // Chữ ký bảo mật


        System.out.println("Response Code: " + responseCode);
        System.out.println("Transaction Reference: " + txnRef);
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Payment Time: " + paymentTime);
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Order Info: " + orderInfo);
        System.out.println("Secure Hash: " + secureHash);
        // Nếu thanh toán thành công
        if ("00".equals(responseCode)) {
            String userId = orderInfo.split("_userId:")[1];
            System.out.println(userId);
            String membership = orderInfo.split("_userId:")[0];
            System.out.println(membership);
            Membership membership1 = membershipService.getMembershipByType(MembershipType.valueOf(membership));
            long membershipId = membership1.getMembershipId();
            double price = Double.parseDouble(totalPrice);
            paymentService.updatePaymentStatus(transactionId,userId,paymentTime,membershipId,price,PaymentStatus.SUCCESS);
            // Truyền thông tin đến giao diện
            model.addAttribute("orderId", txnRef);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("paymentTime", paymentTime);
            model.addAttribute("transactionId", transactionId);

            return "ordersuccess"; // Trả về trang thanh toán thành công
        } else {
            // Nếu thanh toán thất bại
            model.addAttribute("message", "Thanh toán thất bại!");
            return "orderfail";
        }
    }
}
