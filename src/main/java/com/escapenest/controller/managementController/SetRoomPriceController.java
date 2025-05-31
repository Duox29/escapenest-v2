package com.escapenest.controller.managementController;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.Room;
import com.escapenest.repository.RoomPriceRepository;
import com.escapenest.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotel-manager/room-price")
public class SetRoomPriceController {
    private final RoomService roomService;
    private final RoomPriceRepository roomPriceRepository;
    @GetMapping()
    public String getPageRoomPrice (Model model){
        List<Room > roomList = roomService.getRoomHotelManager();
        model.addAttribute("roomList" , roomList);
//        RoomPrice roomPrice = roomPriceRepository.findById(1).orElseThrow(()-> new RuntimeException("Không tìm thấy"));
//        roomPriceRepository.delete(roomPrice);
        return "/hotel-management/price/index";
    }
}
