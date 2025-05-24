package com.escapenest.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeVerifyToken {
    NOT_EXIST("Không tồn tại"),
    USED("Đã sử dụng"),
    EXPIRED("Đã hết hạn"),
    SUCCESS("Thành công");
    public final String value;
}
