package com.escapenest.repository;

import com.escapenest.entity.ImageRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRoomRepository extends JpaRepository<ImageRoom,String> {
    Page<ImageRoom> findAllByRoom_Id(Integer id, Pageable pageable);
    List<ImageRoom> findAllByRoom_Id(Integer id);

}
