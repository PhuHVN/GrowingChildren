package com.example.GrowChild.until;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class VNPayUtil {

    // Hàm tạo URL thanh toán VNPay
    public static String generateVNPayUrl(String vnp_PayUrl, Map<String, String> params, String hashSecret) throws Exception {
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames); // Sắp xếp tham số theo bảng chữ cái

        StringBuilder query = new StringBuilder();
        StringBuilder hashData = new StringBuilder();


        for (String fieldName : fieldNames) {
            String fieldValue = params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8)).append("=")
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8)).append("&");

                hashData.append(fieldName).append("=").append(fieldValue).append("&");
            }
        }

        if (!hashData.isEmpty() && hashData.charAt(hashData.length() - 1) == '&') {
            hashData.setLength(hashData.length() - 1);
        }
        String queryUrl = query.substring(0, query.length() - 1); // Bỏ dấu & cuối
        String hashDataStr = hashData.substring(0, hashData.length() - 1); // Bỏ dấu & cuối
        String secureHash = hmacSHA512(hashSecret, hashDataStr);
        System.out.println("Raw data before hash: " + hashData);
        System.out.println("Generated SecureHash: " + hmacSHA512(hashSecret, hashData.toString()));

        return vnp_PayUrl + "?" + queryUrl + "&vnp_SecureHash=" + secureHash;
    }

    // Hàm tạo mã HMAC-SHA512
    public static String hmacSHA512(String key, String data) throws Exception {
        Mac hmacSha512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmacSha512.init(secretKey);
        byte[] hmacBytes = hmacSha512.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hmacBytes);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
