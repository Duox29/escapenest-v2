package com.escapenest.service;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.PaymentReceipt;
import com.escapenest.repository.PaymentReceiptRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentReceiptService {
    private final PaymentReceiptRepository paymentReceiptRepository;

    public List<PaymentReceipt> getAllPaymentHistoryOrderByCreateDesc() {
        return paymentReceiptRepository.findAllOrderByDescending();

    }
}
