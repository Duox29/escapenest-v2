package com.escapenest.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusBooking {
    PENDING("Chờ xác nhận"),
    CONFIRMED("Đã xác nhận"),
    CANCELLED("Đã hủy"),
    REJECTED("Từ chối"),
    COMPLETE("Hoàn tất");

    private final String value;
}
