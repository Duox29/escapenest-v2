package com.escapenest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.escapenest.model.enums.ImageType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class ImageHotel extends Image {
    @Enumerated(EnumType.STRING)
    ImageType imageType;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;



}
