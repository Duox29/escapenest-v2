package com.escapenest.repository;

import com.escapenest.entity.AmenityHotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityHotelRepository extends JpaRepository<AmenityHotel,Integer> {
    AmenityHotel findByName(String name);
}
