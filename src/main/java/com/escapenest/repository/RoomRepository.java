package com.escapenest.repository;

import com.escapenest.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {

    List<Room> findRoomByHotel_Id(Integer id);
    List<Room> findRoomByHotel_IdAndStatusTrue(Integer id);

    Room findRoomByName(String id);

    long countByAmenityRoomList_Id(Integer id);
}
