package com.escapenest.repository;

import com.escapenest.entity.AmenityRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRoomRepository extends JpaRepository<AmenityRoom, Integer> {

//    List<AmenityRoom> findAmenityRoomByHotel_Id(int id);

    AmenityRoom findByName(String name);
}
