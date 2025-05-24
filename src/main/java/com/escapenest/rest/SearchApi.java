package com.escapenest.rest;

import lombok.RequiredArgsConstructor;
import com.escapenest.model.dto.HotelDto;
import com.escapenest.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchApi {
    private final HotelService hotelService;
    @GetMapping("/{city}")
    public ResponseEntity<?> getHotel ( @PathVariable String city,
                                        @RequestParam(name = "checkIn") String checkIn,
                                        @RequestParam(name = "checkOut") String checkOut,
                                        @RequestParam(required = false, defaultValue = "1") Integer numberGuest,
                                        @RequestParam(required = false, defaultValue = "1") Integer numberRoom ){
        List<HotelDto> hotelList =  hotelService.getHotelHomPage(city,checkIn,checkOut,numberGuest,numberRoom);
       return ResponseEntity.ok(hotelList);
    }
    @GetMapping("/all/{city}")
    public ResponseEntity<?> getHotelAll ( @PathVariable String city,
                                        @RequestParam(name = "checkIn") String checkIn,
                                        @RequestParam(name = "checkOut") String checkOut,
                                        @RequestParam(required = false, defaultValue = "1") Integer numberGuest,
                                        @RequestParam(required = false, defaultValue = "1") Integer numberRoom ){
        List<HotelDto> hotelList =  hotelService.getAllHotelHomepage(city,checkIn,checkOut,numberGuest,numberRoom);
        return ResponseEntity.ok(hotelList);
    }
    @GetMapping("/search")
    public List<HotelDto> getHotelBySearchV2(
            @RequestParam String nameCity,
            @RequestParam(required = false) String checkIn,
            @RequestParam(required = false) String checkOut,
            @RequestParam(required = false, defaultValue = "1") Integer numberGuest,
            @RequestParam(required = false, defaultValue = "1") Integer numberRoom) {

        // Pass null if checkIn or checkOut is not provided
        String checkInString = checkIn != null ? checkIn : null;
        String checkOutString = checkOut != null ? checkOut : null;

        // Call the service method with String parameters
        List<HotelDto> availableHotels = hotelService.getHotelBySearchV2(nameCity, checkInString, checkOutString, numberGuest, numberRoom);
        log.info("API Search called.");
        return availableHotels; // Return the list of available hotels
    }
}
