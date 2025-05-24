package com.escapenest.controller.managementController;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.Hotel;
import com.escapenest.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotel-manager")
public class ReportController {
    private final HotelService hotelService;
    @GetMapping("/report")
    public String getReportPage(Model model) {
        Hotel hotel = hotelService.getHotelByAccountCurrent();
        model.addAttribute("hotelId", hotel.getId());
        return "hotel-management/report/index";
    }
}
