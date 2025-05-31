package com.escapenest.rest;

import lombok.RequiredArgsConstructor;
import com.escapenest.model.request.UpsertPolicyRequest;
import com.escapenest.service.BookingService;
import com.escapenest.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/hotel-manager/")
@RequiredArgsConstructor
public class HotelManagerApi {

    private final BookingService bookingService;
    private final HotelService hotelService;

    @GetMapping("/revenue-day/{idHotel}")
    public ResponseEntity<?> getRevenueMonthByHotel (@RequestParam Integer month
            , @RequestParam Integer year, @PathVariable Integer idHotel){
        return ResponseEntity.ok(bookingService.getRevenueByMonthAndHotel(idHotel,month,year));

    }
    @GetMapping("/revenue-month/{idHotel}")
    public ResponseEntity<?> getRevenueYearByHotel ( @RequestParam Integer year, @PathVariable Integer idHotel){
        return ResponseEntity.ok(bookingService.getRevenueByYearAndHotel(idHotel,year));

    }
    @GetMapping("/total-booking/{idHotel}")
    public ResponseEntity<?> getTotalBookingByNameRoom (@RequestParam Integer year, @PathVariable Integer idHotel){
        return ResponseEntity.ok(bookingService.getTotalBookingByRoomType(idHotel,year));

    }
    @PostMapping("/update-policy")
    public ResponseEntity<?> updatePolicyHotel (@RequestBody UpsertPolicyRequest request){
        System.out.println(request.getService()+"dịch vụ");
        System.out.println(request+" đối tượng gửi lên");
        hotelService.updatePolicyHotel(request);
        return ResponseEntity.noContent().build();
    }
}
