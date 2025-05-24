package com.escapenest.repository;

import com.escapenest.entity.RoomPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RoomPriceRepository extends JpaRepository<RoomPrice ,Integer> {
    List<RoomPrice> findAllByDate (LocalDate date);
    List<RoomPrice> findAllByDateAfter (LocalDate date);
    RoomPrice findByDateAndRoom_Id (LocalDate date , Integer idRoom);
}
