package com.escapenest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.escapenest.model.enums.AmenityRoomType;

@Entity
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AmenityRoom extends Amenity {
    @Enumerated(EnumType.STRING)
    AmenityRoomType amenityRoomType;
}
