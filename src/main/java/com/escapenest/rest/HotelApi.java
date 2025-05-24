package com.escapenest.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.escapenest.entity.Hotel;
import com.escapenest.entity.User;
import com.escapenest.model.dto.HotelDto;
import com.escapenest.model.request.UpsertHotelRequest;
import com.escapenest.model.request.UpsertPolicyRequest;
import com.escapenest.model.request.UpsertRoomRequest;
import com.escapenest.security.CustomUserDetailService;
import com.escapenest.service.HotelService;

import com.escapenest.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelApi {

    private final CustomUserDetailService customUserDetailService;
    private final HotelService hotelService;
    private final UserService userService;
    @PostMapping("/user/create")
    public ResponseEntity<?> createHotelUser(
            @ModelAttribute UpsertHotelRequest upsertHotelRequest,
            @ModelAttribute UpsertRoomRequest upsertRoomRequest,
            @ModelAttribute UpsertPolicyRequest upsertPolicyRequest,
            @RequestParam(value = "fileHotel" ,required = false) MultipartFile fileHotel,
            @RequestParam(value = "fileRoom" , required = false) MultipartFile fileRoom) {

        log.info("APi log");
        Hotel hotel = hotelService.createHotelUser(upsertHotelRequest,upsertRoomRequest,upsertPolicyRequest,fileHotel,fileRoom);
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }

    @PostMapping("/favourite/{id}")
    public ResponseEntity<List<Hotel>> saveHotelFavourite(@PathVariable Integer id){
        userService.saveHotelFavourite(id);
        return ResponseEntity.ok().build();
    }
    // xóa khách sạn ra khỏi danh sanh yêu thích trang danh sách yêu thiích
    @DeleteMapping("/delete/list-favourite/{id}")
    public ResponseEntity<Page<Hotel>> deleteHotelFavourite(@PathVariable Integer id,@RequestParam Integer pageNumber){
        Page<Hotel> hotelPage = userService.deleteHotelFavourite(id,pageNumber);
        return ResponseEntity.ok(hotelPage);
    }

    // xóa khách sạn ra khỏi danh sách yêu thích trang danh sách khách sạn
    @DeleteMapping("/delete/favourite/{id}")
    public ResponseEntity<?> deleteHotelFavourite(@PathVariable Integer id){
        User user  = userService.deleteHotelFavourite(id);
        return ResponseEntity.ok(user);
    }
    @GetMapping()
    public ResponseEntity<?> getPaginationHotel(@RequestParam(required = false , defaultValue = "1") Integer pageNumber ,
                                                          @RequestParam(required = false , defaultValue = "10") Integer limit){
        Page<HotelDto> paginationHotel= hotelService.getPaginationHotel(pageNumber,limit);
        System.out.println();
        return new ResponseEntity<>(paginationHotel, HttpStatus.OK);
    }
    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> updateHotelAdmin(@PathVariable Integer id , @RequestBody UpsertHotelRequest request) {
        return new ResponseEntity<>( hotelService.updateHotelAdmin(id, request), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHotelManager(@PathVariable Integer id , @RequestBody UpsertHotelRequest request) {
        return new ResponseEntity<>( hotelService.updateHotel(id, request), HttpStatus.OK);
    }

   @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> updateHotelAdmin(@PathVariable Integer id ) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/update-poster/{id}")
    public ResponseEntity<?> updatePost( @RequestBody String urlImage, @PathVariable Integer id){
        hotelService.updateHotelPoster(id,urlImage);
        return ResponseEntity.noContent().build();
    }

}
