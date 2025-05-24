package com.escapenest.controller;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.PaymentReceipt;
import com.escapenest.repository.PaymentReceiptRepository;
import com.escapenest.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class VnPayController {

    private  final BookingService bookingService;
    private  final PaymentReceiptRepository paymentReceiptRepository;

    @GetMapping("/status-payment")
    public String getStatusPayment(Model model,
                                   @RequestParam("vnp_Amount") String vnpAmount,
                                   @RequestParam("vnp_BankCode") String vnpBankCode,
                                   @RequestParam("vnp_BankTranNo") String vnpBankTranNo,
                                   @RequestParam("vnp_CardType") String vnpCardType,
                                   @RequestParam("vnp_PayDate") String vnpPayDate,
                                   @RequestParam("vnp_OrderInfo") String vnpOrderInfo,
                                   @RequestParam("vnp_TransactionNo") String vnpTransactionNo,
                                   @RequestParam("vnp_ResponseCode") String vnpResponseCode,
                                   @RequestParam("vnp_TmnCode") String vnpTmnCode,
                                   @RequestParam("vnp_TransactionStatus") String vnpTransactionStatus,
                                   @RequestParam("vnp_TxnRef") String vnpTxnRef,
                                   @RequestParam("vnp_SecureHash") String vnpSecureHash){


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime dateTime = LocalDateTime.parse(vnpPayDate, formatter);
        if (!"00".equals(vnpResponseCode)) {
            bookingService.editStatus(Integer.valueOf(vnpTxnRef));
        }else {
            PaymentReceipt paymentReceipt = new PaymentReceipt();
            paymentReceipt.setVnpAmount(Integer.parseInt(vnpAmount)/100);
            paymentReceipt.setVnpBankCode(vnpBankCode);
            paymentReceipt.setVnpBankTranNo(vnpBankTranNo);
            paymentReceipt.setVnpCardType(vnpCardType);
            paymentReceipt.setVnpOrderInfo(vnpOrderInfo);
            paymentReceipt.setVnpPayDate(dateTime);
            paymentReceipt.setVnpTransactionNo(vnpTransactionNo);
            paymentReceipt.setVnpTmnCode(vnpTmnCode);
            paymentReceipt.setVnpTransactionStatus(vnpTransactionStatus);
            paymentReceipt.setVnpTxnRef(Integer.parseInt(vnpTxnRef));
            paymentReceipt.setVnpSecureHash(vnpSecureHash);
            paymentReceipt.setVnpResponseCode(true);
            paymentReceiptRepository.save(paymentReceipt); // lamgf được đến phần lưu, mai kiểm tra xem luuu ook k
        }

        model.addAttribute("status",vnpResponseCode);
        model.addAttribute("vnpBankCode",vnpBankCode);
        model.addAttribute("vnpTxnRef",vnpTxnRef);
        model.addAttribute("vnpOrderInfo",vnpOrderInfo);
        model.addAttribute("vnpPayDate",dateTime);
        return "web/status-payment";
    }
}
