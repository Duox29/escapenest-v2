package com.escapenest.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomType {
    SINGLE("Phòng Đơn"),
    DOUBLE("Phòng Đôi"),
    STANDARD("Phòng Tiêu Chuẩn"),
    SUITE("Phòng Suite"),
    DELUXE("Phòng Cao Cấp"),
    STUDIO("Studio");
    private final String value;
}
