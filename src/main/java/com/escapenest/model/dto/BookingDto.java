package com.escapenest.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import com.escapenest.model.enums.RoomType;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDto {
    Integer id;
    Integer guests;
    LocalDate checkIn;
    LocalDate checkOut;
    Integer numberRoom;
    String nameRoom;
    RoomType typeRoom;
}
