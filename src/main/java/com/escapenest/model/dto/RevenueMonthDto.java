package com.escapenest.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class RevenueMonthDto {
    Integer  month;
    Integer  year;
    long  totalPrice;
}