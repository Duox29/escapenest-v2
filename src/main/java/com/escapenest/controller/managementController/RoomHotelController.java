package com.escapenest.controller.managementController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.escapenest.entity.AmenityRoom;
import com.escapenest.entity.Room;
import com.escapenest.model.enums.BedSize;
import com.escapenest.model.enums.BedType;
import com.escapenest.model.enums.RoomType;
import com.escapenest.service.AmenityService;
import com.escapenest.service.HotelService;
import com.escapenest.service.ImageService;
import com.escapenest.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Slf4j
@Controller
@RequestMapping("/hotel-manager/room")
@RequiredArgsConstructor
public class RoomHotelController {
    private final RoomService roomService;
    private final AmenityService amenityService;
    private final ImageService imageService;
    private final HotelService hotelService;


    @GetMapping()
    public String viewPageRoomHotel(Model model) {
        List<Room> roomList = hotelService.getAllRoom();
        model.addAttribute("roomList", roomList);
        return "hotel-management/room/index";
    }

    @GetMapping("/detail/{id}")
    public String viewRoomDetail(Model model, @PathVariable Integer id) {
        List<AmenityRoom> amenityRoomList = amenityService.getAllAmenityRoom();
        Room room = roomService.getRoomById(id); //  logic này ạ
        model.addAttribute("room", room);
        model.addAttribute("roomHotel", room.getAmenityRoomList());
        model.addAttribute("listRoomType", RoomType.values());
        model.addAttribute("amenityRoomAll", amenityRoomList);
        model.addAttribute("bedType", BedType.values());
        model.addAttribute("bedSize", BedSize.values());
        return "hotel-management/room/detail";
    }
    @GetMapping("/create")
    public String viewRoomCreate(Model model) {
        List<AmenityRoom> amenityRoomList = amenityService.getAllAmenityRoom();
        model.addAttribute("amenityRoom", amenityRoomList);
        model.addAttribute("bedType", BedType.values());
        model.addAttribute("bedSize", BedSize.values());
        model.addAttribute("listRoomType", RoomType.values());
        return "hotel-management/room/create";
    }

}
