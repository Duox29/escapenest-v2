package com.escapenest.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TotalBookingMonthDto {
    long month;
    long year;
    long  totalBooking;
}