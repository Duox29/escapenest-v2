package com.escapenest.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
    VN_PAY("VN Pay"),
    PAY_AT_ACCOMMODATION("Thanh toán tại nơi lưu trú");
    private final String value;
}
