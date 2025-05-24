package com.escapenest.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomPriceDto {
    String nameRoom;
    Integer price;
    String typeRoom;
    LocalDate day;
}
