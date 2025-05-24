package com.escapenest.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SupportType {
    CHANGE_AND_CANCEL("Thay đổi & Hủy"),
    PAYMENT_AND_RECEIPT("Thanh toán & Biên nhận"),
    REFUND("Hoàn trả"),
    ACCOUNT_SECURITY("Bảo mật tài khoản"),
    TRAVEL_ALERT("Cảnh báo du lịch"),
    SECURITY("Bảo mật");

    private final String value;
}
