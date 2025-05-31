package com.escapenest.rest;


import lombok.RequiredArgsConstructor;
import com.escapenest.entity.AmenityHotel;
import com.escapenest.entity.AmenityRoom;
import com.escapenest.model.request.UpsertAmenityRequest;
import com.escapenest.service.AmenityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amenity/")
@RequiredArgsConstructor
public class AmenityApi {
    private final AmenityService amenityService ;
    @GetMapping("/hotel/get-all")
    public ResponseEntity<?> getAllAmenityHotel (){
        List<AmenityHotel> amenityHotelList = amenityService.getAllAmenityHotel();
        return ResponseEntity.ok(amenityHotelList);
    }
    @GetMapping("/room/get-all")
    public ResponseEntity<?> getAllAmenityRoom (){
        List<AmenityRoom> amenityRoomList = amenityService.getAllAmenityRoom();
        return ResponseEntity.ok(amenityRoomList);
    }



    // admin cập nhật tiện ích homestay
    @PutMapping("/update/hotel-amenity/{id}")
    public ResponseEntity<AmenityHotel> updateAmenityHotel (@PathVariable Integer id ,@RequestBody UpsertAmenityRequest request){
        AmenityHotel amenityHotel = amenityService.updateAmenityHotel(id,request);
        return ResponseEntity.ok(amenityHotel);
    }
    // admin cập nhật tiện ích phòng
    @PutMapping("/update/room-amenity/{id}")
    public ResponseEntity<AmenityRoom> updateAmenityRoom (@PathVariable Integer id , @RequestBody UpsertAmenityRequest request){
        AmenityRoom amenityRoom = amenityService.updateAmenityRoom(id,request);
        return ResponseEntity.ok(amenityRoom);
    }

    // admin tạo tiện ích homestay
    @PostMapping("/create/hotel-amenity")
    public ResponseEntity<AmenityHotel> createAmenityHotel (@RequestBody UpsertAmenityRequest request){
        AmenityHotel amenityHotel = amenityService.createAmenityHotel(request);
        return new ResponseEntity<>(amenityHotel, HttpStatus.CREATED);
    }

    // admin tạo tiện ích phòng
    @PostMapping("/create/room-amenity")
    public ResponseEntity<AmenityRoom> createAmenityRoom ( @RequestBody UpsertAmenityRequest request){
        AmenityRoom amenityRoom = amenityService.createAmenityRoom(request);
        return new ResponseEntity<>(amenityRoom, HttpStatus.CREATED);
    }

    // admin xóa tiện ích homestay hoặc phòng
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAmenity (@PathVariable Integer id){
        amenityService.deleteAmenity(id);
        return ResponseEntity.noContent().build();
    }
}
