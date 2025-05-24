package com.escapenest.controller.managementController;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.AmenityHotel;
import com.escapenest.entity.Hotel;
import com.escapenest.entity.ImageHotel;
import com.escapenest.entity.Room;
import com.escapenest.service.AmenityService;
import com.escapenest.service.HotelService;
import com.escapenest.service.ImageService;
import com.escapenest.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/hotel-manager")
@RequiredArgsConstructor
public class InformationHotelController {
    public final HotelService hotelService ;
    public final ImageService imageService ;
    public final RoomService roomService ;
    public final AmenityService amenityService ;


    @GetMapping("/information")
    public String informationHotelDetail(Model model){
        Hotel hotel  = hotelService.getHotelByAccountCurrent();
        List<ImageHotel> imageHotelList = imageService.getAllImageByIdHotel(hotel.getId());
        List<Room> roomList  = roomService.getRoomByIdHotel(hotel.getId());
        List<AmenityHotel> amenityHotelListAll = amenityService.getAllAmenityHotel();
        model.addAttribute("amenityHotelListAll" , amenityHotelListAll);
        model.addAttribute("roomList" , roomList);
        model.addAttribute("imageHotelList" , imageHotelList);
        model.addAttribute("hotel" , hotel);
        return "/hotel-management/information-hotel";
    }

}
