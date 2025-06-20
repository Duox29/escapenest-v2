package com.escapenest.rest;


import com.escapenest.service.AuthService;
import lombok.RequiredArgsConstructor;
import com.escapenest.config.ConfigVnPay;
import com.escapenest.entity.Booking;
import com.escapenest.model.dto.PaymentRestDto;
import com.escapenest.model.request.UpsertBookingRequest;
import com.escapenest.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("api/payment")
@RequiredArgsConstructor
public class RestPaymentVnPay {
     private final BookingService bookingService ;
     private final AuthService authService;
    @PostMapping("/vn-pay")
    @Transactional
    public ResponseEntity<?> createPaymentVnPay(@RequestBody UpsertBookingRequest bookingRequest) throws UnsupportedEncodingException {
        if(authService.isSuspended(bookingRequest.getEmailCustomer())) {
            throw new RuntimeException("Tài khoản đã bị khóa, vui lòng liên hệ để được hỗ trợ.");
        }
        long amount = bookingRequest.getPrice() * 100;
        Booking booking = bookingService.bookingHotel(bookingRequest);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", ConfigVnPay.vnp_Version);
        vnp_Params.put("vnp_Command", ConfigVnPay.vnp_Command);
        vnp_Params.put("vnp_TmnCode", ConfigVnPay.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", ConfigVnPay.vnp_CurrCode);
        //vnp_Params.put("vnp_BankCode", ConfigVnPay.bankCode);
        vnp_Params.put("vnp_TxnRef", String.valueOf(booking.getId()));// id đơn booking
        vnp_Params.put("vnp_OrderInfo","Thanh toan don dat phong:"+ booking.getId());
        vnp_Params.put("vnp_OrderType", ConfigVnPay.orderType);
        vnp_Params.put("vnp_Locale", ConfigVnPay.vnp_Locale);
        vnp_Params.put("vnp_ReturnUrl", ConfigVnPay.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", ConfigVnPay.vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = ConfigVnPay.hmacSHA512(ConfigVnPay.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = ConfigVnPay.vnp_PayUrl + "?" + queryUrl;
        PaymentRestDto paymentRestDto = new PaymentRestDto();
        paymentRestDto.setStatus("00");
        paymentRestDto.setMessage("Success");
        paymentRestDto.setUrl(paymentUrl);
        System.out.println(paymentUrl);
        System.out.println(paymentRestDto);
        return ResponseEntity.status(HttpStatus.OK).body(paymentRestDto);
    }
}
