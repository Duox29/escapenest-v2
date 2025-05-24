package com.escapenest.repository;

import com.escapenest.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review , Integer> {
    List<Review> findAllByHotel_IdOrderByCreateAtDesc(Integer id);

    List<Review> findReviewByHotel_Id(Integer id);
}
