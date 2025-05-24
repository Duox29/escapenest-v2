package com.escapenest.repository;

import com.escapenest.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image,String> {
    @Query(value = "SELECT i.url FROM images i WHERE i.room_id = :id ORDER BY i.created_at ASC LIMIT 1", nativeQuery = true)
String findFirstImageUrlByRoomId(@Param("id") int id);
}
