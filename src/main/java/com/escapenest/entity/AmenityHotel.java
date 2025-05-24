package com.escapenest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.escapenest.model.enums.AmenityHotelType;

@Entity
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AmenityHotel extends Amenity {
    @Enumerated(EnumType.STRING)
    AmenityHotelType amenityHotelType;
}
