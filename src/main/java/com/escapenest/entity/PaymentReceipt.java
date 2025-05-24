package com.escapenest.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "payment_receipt")
public class PaymentReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer vnpAmount;
    String vnpBankCode;
    String vnpBankTranNo;
    String vnpCardType;
    LocalDateTime vnpPayDate;
    String vnpOrderInfo;
    String vnpTransactionNo;
    Boolean vnpResponseCode;
    String vnpTmnCode;
    String vnpTransactionStatus;
    Integer vnpTxnRef;
    String vnpSecureHash;

}
