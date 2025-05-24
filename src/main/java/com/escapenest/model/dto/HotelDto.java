package com.escapenest.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import com.escapenest.model.enums.RentalType;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class HotelDto {
    Integer id;
    String name;
    String address;
    Integer star;
    Float rating ;
    String ratingText;
    RentalType rentalType;
    Integer estimatedPrice;
    Integer totalReviews;
    List<String> nameAmenity;
    Set<String> nameAmenityRoom;
    String poster;
}
