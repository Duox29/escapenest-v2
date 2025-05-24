package com.escapenest.rest;

import lombok.RequiredArgsConstructor;
import com.escapenest.model.dto.RevenueDayDto;
import com.escapenest.model.dto.RevenueMonthDto;
import com.escapenest.service.BookingService;
import com.escapenest.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/revenue")
@RequiredArgsConstructor
public class RevenueApi {
    private final BookingService bookingService;
    private final HotelService hotelService;

    @GetMapping("/day/{year}/{month}")
    public ResponseEntity<List<RevenueDayDto>> getRevenueDayByMonth (@PathVariable Integer year, @PathVariable Integer month){
        return ResponseEntity.ok(bookingService.getRevenueByDay(year,month));
    }

    @GetMapping("/month/{year}")
    public ResponseEntity<List<RevenueMonthDto>> getRevenueMonthByYear (@PathVariable Integer year){
        return ResponseEntity.ok(bookingService.getRevenueByMonth(year));
    }
}
