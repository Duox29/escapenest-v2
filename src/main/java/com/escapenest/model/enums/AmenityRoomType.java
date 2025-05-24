package com.escapenest.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AmenityRoomType {
    BATHROOM( "Phòng tắm"), // Phòng tắm
    BEDROOM( "Phòng ngủ"), // Phòng ngủ
    ENTERTAINMENT( "Giải trí"), // Giải trí
    KITCHEN( "Bếp"), // Bếp
    INTERNET( "Internet"), // Internet
    OTHERS( "Khác"); // Khác
    private final String value;
}
