package com.escapenest.entity;

import jakarta.persistence.*;
import lombok.*;
import com.escapenest.model.enums.RoomType;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    LocalDate startDate;
    LocalDate endDate;

    @Enumerated(EnumType.STRING)
    RoomType roomType;
    Double discountPercentage; // phần trăm giảm giá

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    Hotel hotel;

    LocalDate createdAt;

    LocalDate updatedAt;
}
