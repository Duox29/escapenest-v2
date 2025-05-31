package com.escapenest.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import com.escapenest.entity.Booking;
import com.escapenest.entity.Hotel;
import com.escapenest.entity.Review;
import com.escapenest.entity.User;
import com.escapenest.model.request.UpsertReviewRequest;
import com.escapenest.repository.BookingRepository;
import com.escapenest.repository.ReviewRepository;
import com.escapenest.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewsService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final HotelService hotelService;
    private final HttpSession session;
    private final BookingRepository bookingRepository;

    // tạo reviews mới
    public Review createReview(UpsertReviewRequest upsertReviewRequest) {

        //  lấy ra booking với id được gửi lên
        Booking booking = bookingRepository.findById(upsertReviewRequest.getIdBooking()).orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        // tìm kiếm homestay theo id
        Hotel hotel = booking.getHotel();

        // lấy user đã reviews
        User user = booking.getUser();

        // tính rating rồi set vào hotel
        if (!findAllReview(hotel.getId()).isEmpty()){
            float totalReview = hotel.getRating()*findAllReview(hotel.getId()).size();
            hotel.setRating((totalReview+upsertReviewRequest.getRating())/(findAllReview(hotel.getId()).size() +1));
        }
        else {
            hotel.setRating(Float.valueOf(upsertReviewRequest.getRating()));
        }

        Review review = Review.builder()
                .user(user)
                .comment(upsertReviewRequest.getComment())
                .rating(upsertReviewRequest.getRating())
                .createAt(LocalDate.now())
                .hotel(hotel)
                .build();

        reviewRepository.save(review);
        booking.setIsReviewed(true);
        bookingRepository.save(booking);
        return  review;
    }

    public List<Review> findAllReview(Integer id) {
       return reviewRepository.findAllByHotel_IdOrderByCreateAtDesc(id);
    }

    public Review updateReview(UpsertReviewRequest upsertReviewRequest, Integer id) {
        User user = userRepository.findByEmail(session.getAttribute("MY_SESSION").toString()).orElseThrow(()-> new UsernameNotFoundException("Không tìm thấy user"));

        System.out.println("--------------------------------------------------------------" + user);
        // lấy hotel với review được update
        Hotel hotel = bookingRepository.findById(upsertReviewRequest.getIdBooking()).get().getHotel();
        System.out.println("--------------------------------------------------------------" + hotel);


        // kiểm tra review có tồn tại hay không
        Review review =reviewRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy review có id : " + id));
        System.out.println("--------------------------------------------------------------" + review.getUser());
        // kiểm tra xem id của người đang thực hiện có phải của user đang log in hay không
        if (!Objects.equals(review.getUser().getId(), user.getId())){
            throw new RuntimeException ("Bạn không có quyền truy cập ");
        }

        //kiểm tra xem review có thuộc hotel này hay không
        if (!review.getHotel().getId().equals(hotel.getId())){
            throw new RuntimeException("Review không thuộc hotel này ");
        }

        review.setComment(upsertReviewRequest.getComment());
        review.setRating(upsertReviewRequest.getRating());
        review.setUpdateAt(LocalDate.now());
        return reviewRepository.save(review);
    }

    public void deleteReview(Integer id) {
        User user = userRepository.findByEmail(session.getAttribute("MY_SESSION").toString()).orElseThrow(()-> new UsernameNotFoundException("Không tìm thấy user"));


        System.out.println("--------------------------------------------------------------" + user);

        // kiểm tra review có tồn tại hay không
        Review review =reviewRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Canno find review by Id : " + id));

        System.out.println("--------------------------------------------------------------" + review.getUser());


        Hotel hotel = review.getHotel();
        System.out.println("--------------------------------------------------------------" + hotel);

        // kiểm tra xem id của người đang thực hiện có phải của user đang log in hay không
        if (!Objects.equals(review.getUser().getId(), user.getId())){
            throw new RuntimeException ("Bạn không có quyền truy cập ");
        }

        reviewRepository.delete(review);
        List<Review> list = reviewRepository.findAllByHotel_IdOrderByCreateAtDesc(hotel.getId());
        float rating = 0;
        for (Review re : list ){
            rating+=re.getRating();
        }
        rating=rating/list.size();
        hotel.setRating(rating);

    }
}
