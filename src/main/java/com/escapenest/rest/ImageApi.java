package com.escapenest.rest;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.ImageHotel;
import com.escapenest.entity.ImageRoom;
import com.escapenest.entity.ImageUser;
import com.escapenest.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController()
@RequestMapping("api/images")
@RequiredArgsConstructor
public class ImageApi {

    private final ImageService imageService;

    @PostMapping("/upload/user")
    public ResponseEntity<?> uploadImageUser (@RequestParam("file") MultipartFile file){
        ImageUser imageUser = imageService.uploadImageForUser(file);
        return new ResponseEntity<>(imageUser, HttpStatus.CREATED);
    }
    @PostMapping("/upload-hotel/{idHotel}")
    public ResponseEntity<?> uploadImageHotel (@RequestParam("file") MultipartFile file, @PathVariable Integer idHotel){
        ImageHotel imageHotel = imageService.uploadImageHotel(idHotel,file);
        System.out.println("ID homestay: " + idHotel);
        return new ResponseEntity<>(imageHotel, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<?> deleteImageUser ( @PathVariable String id){
       imageService.deleteImageUser(id);
        return  ResponseEntity.noContent().build();
    }
    @DeleteMapping("/delete/hotel/{id}")
    public ResponseEntity<?> deleteImageHotel ( @PathVariable String id){
       imageService.deleteImageHotel(id);
        return  ResponseEntity.noContent().build();
    }
    @GetMapping("/get-image-room/{idRoom}")
    public ResponseEntity<?> getImageRoomByIdRoom(@PathVariable Integer idRoom){
        List<ImageRoom> imageRoomList = imageService.getAllImageRoomByIdRoom(idRoom);
        return ResponseEntity.ok(imageRoomList);
    }


}
