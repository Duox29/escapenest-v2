package com.escapenest.rest;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.ImageRoom;
import com.escapenest.model.request.UpsertRoomRequest;
import com.escapenest.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotel-manager/room")
public class RoomApi {

    private final RoomService roomService;
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRoom (@RequestBody UpsertRoomRequest request, @PathVariable Integer id){
        return ResponseEntity.ok(roomService.updateRoom(request,id));
    }
    @PostMapping("/create")
    public ResponseEntity<?> createRoom (@RequestBody UpsertRoomRequest request){
        return new ResponseEntity<>(roomService.createRoom(request) , HttpStatus.CREATED);

    }@DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoom ( @PathVariable Integer id){
        roomService.deleteRoom(id);
        return  ResponseEntity.noContent().build();
    }

    @PostMapping("/upload-image/{id}")
    public ResponseEntity<?> uploadImageRoom (@RequestParam("file") MultipartFile multipartFile , @PathVariable Integer id){
        return ResponseEntity.ok(roomService.uploadImageRoom(multipartFile,id));
    }
    @GetMapping("/get-image/{id}")
    public ResponseEntity<?> getAllImageRoom ( @PathVariable Integer id ,
                                               @RequestParam(required = false ,defaultValue = "1") Integer page,
                                               @RequestParam(required = false ,defaultValue = "8") Integer limit){
        Page<ImageRoom> imageRoomPage  = roomService.getAllImageRoom(id,limit,page);
        return ResponseEntity.ok(imageRoomPage);
    }
    @DeleteMapping("/delete-image/{id}")
    public ResponseEntity<?> deleteImageRoom (@PathVariable String id ){
        roomService.deleteImageRoom(id);
        return ResponseEntity.noContent().build();
    }
}
