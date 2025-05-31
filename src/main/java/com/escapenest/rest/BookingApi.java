package com.escapenest.rest;

import com.escapenest.service.AuthService;
import lombok.RequiredArgsConstructor;
import com.escapenest.entity.Booking;
import com.escapenest.model.request.UpsertBookingRequest;
import com.escapenest.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/booking")
@RequiredArgsConstructor
public class BookingApi {

    private final BookingService bookingService;
    private final AuthService authService;

    @PostMapping("/add")
    public ResponseEntity<?> bookingHotel(@RequestBody UpsertBookingRequest bookingRequest){
        if(authService.isSuspended(bookingRequest.getEmailCustomer())) {
            throw new RuntimeException("Tài khoản đã bị khóa, vui lòng liên hệ để được hỗ trợ.");
        }
        Booking booking = bookingService.bookingHotel(bookingRequest);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer id){
       return ResponseEntity.ok(bookingService.getBookingById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Integer id){
        bookingService.cancelBooking(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Integer id,@RequestBody UpsertBookingRequest bookingRequest){
        bookingService.updateBooking(id, bookingRequest);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/confirm/{id}")
    public ResponseEntity<?> confirmBooking(@PathVariable Integer id){
        bookingService.confirmBooking(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectBooking(@PathVariable Integer id){
        bookingService.rejectBooking(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/confirm-check-out/{id}")
    public ResponseEntity<?> confirmCheckOutBooking(@PathVariable Integer id){
        bookingService.confirmCheckOutBooking(id);
        return ResponseEntity.ok().build();
    }
}
