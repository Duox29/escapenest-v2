package com.escapenest.repository;

import com.escapenest.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
    List<Hotel> findHotelByCity_NameIgnoreCaseAndStatusTrue(String name);
    List<Hotel> findHotelByCity_Id(Integer id);
    List<Hotel> findAllByCreatedAtBetweenOrderByCreatedAtDesc(LocalDate start , LocalDate end);
    Hotel findHotelByName(String name);
    Hotel findHotelByUser_Id(Integer id);
    Hotel findByHotline (String hotline);
    long countByAmenityHotelList_Id(Integer id);


}

