package com.escapenest.controller.adminController;

import com.escapenest.entity.Booking;
import com.escapenest.repository.BookingRepository;
import com.escapenest.service.BookingService;
import lombok.RequiredArgsConstructor;
import com.escapenest.entity.PaymentReceipt;
import com.escapenest.service.PaymentReceiptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/admin/payment-history")
@RequiredArgsConstructor

public class PaymentHistoryController {
    private final PaymentReceiptService paymentHistoryService;
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;

    @GetMapping()
    public String getAllPaymentHistory(Model model ){
        List<PaymentReceipt> paymentHistoryServices = paymentHistoryService.getAllPaymentHistoryOrderByCreateDesc();
        model.addAttribute("paymentHistoryServices" , paymentHistoryServices);
        return "admin/payment-history/index";
    }
    @GetMapping("/chi-tiet/{id}")
    public String getPaymentDetail(@PathVariable("id") Integer id, Model model) {
        Booking booking = bookingService.getBooking(id);
        model.addAttribute("booking",booking);
        return "admin/payment-history/detail";
    }
}
