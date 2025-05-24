package com.escapenest.controller.adminController;

import com.escapenest.entity.AmenityHotel;
import com.escapenest.entity.AmenityRoom;
import lombok.RequiredArgsConstructor;
import com.escapenest.model.enums.AmenityHotelType;
import com.escapenest.model.enums.AmenityRoomType;
import com.escapenest.service.AmenityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AmenityController {

    private final AmenityService amenityService;

    @GetMapping("/amenity")
    public String getAmenityBedPage(Model model ){
        List<AmenityRoom> amenityRoomList = amenityService.getAllAmenityRoom();
        List<AmenityHotel> amenityHotelList = amenityService.getAllAmenityHotel();
        model.addAttribute("amenityRoomList" ,amenityRoomList );
        model.addAttribute("amenityHotelList" ,amenityHotelList );
        model.addAttribute("amenityHotelType" , AmenityHotelType.values());
        model.addAttribute("amenityRoomType" , AmenityRoomType.values());
        return "/admin/amenity/index";
    }
}
