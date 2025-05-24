package com.escapenest.rest;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.Review;
import com.escapenest.model.request.UpsertReviewRequest;
import com.escapenest.service.ReviewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reviews")
@RequiredArgsConstructor
public class ReviewApi {
    private final ReviewsService reviewsService;
    @PostMapping("/create")
    public ResponseEntity<?> createReviews(@RequestBody UpsertReviewRequest upsertReviewRequest){
        Review review= reviewsService.createReview(upsertReviewRequest);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@RequestBody UpsertReviewRequest upsertReviewRequest, @PathVariable Integer id){
        Review review= reviewsService.updateReview(upsertReviewRequest,id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview( @PathVariable Integer id){
        reviewsService.deleteReview(id);
        return  ResponseEntity.ok().build();
    }
}
