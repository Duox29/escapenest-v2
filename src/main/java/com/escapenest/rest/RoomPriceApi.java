package com.escapenest.rest;

import lombok.RequiredArgsConstructor;
import com.escapenest.model.dto.RoomPriceDto;
import com.escapenest.model.request.RoomPriceRequest;
import com.escapenest.service.RoomPriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/room-price")
@RestController
@RequiredArgsConstructor
public class RoomPriceApi {

    private final RoomPriceService roomPriceService;
    @PostMapping("/update")
    public ResponseEntity<?> setRoomPrice(@RequestBody RoomPriceRequest request){
        roomPriceService.setRoomPrice(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/day")
    public ResponseEntity<?> getRoomPriceDay(@RequestParam String date){
        List<RoomPriceDto> list = roomPriceService.getRoomPriceDay(date);
        return ResponseEntity.ok(list);
    }

}
