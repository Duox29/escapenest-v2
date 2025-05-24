package com.escapenest.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageType {
    FACADE("Mặt tiền"), // Ảnh mặt tiền
    EXTERIOR("Ngoại thất"), // Ảnh ngoại thất
    LOBBY("Sảnh"), // Ảnh sảnh
    ROOM("Phòng"), // Ảnh phòng
    SWIMMING_POOL("Hồ bơi"), // Ảnh hồ bơi
    RESTAURANT("Nhà hàng"), // Ảnh nhà hàng
    FITNESS_CENTER("Phòng tập gym"), // Ảnh phòng tập gym
    SPA("Spa"), // Ảnh Spa
    CONFERENCE_ROOM("Phòng họp"), // Ảnh phòng họp
    OTHER("Khác"); // Ảnh khác

    private final String value;
}
