package com.escapenest.controller.adminController;
import lombok.RequiredArgsConstructor;
import com.escapenest.entity.Booking;
import com.escapenest.entity.Hotel;
import com.escapenest.entity.User;
import com.escapenest.service.BookingService;
import com.escapenest.service.DashboardService;
import com.escapenest.service.HotelService;
import com.escapenest.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserService userService;
    private final BookingService bookingService;
    private final HotelService hotelService;

    @GetMapping()
    public String getPageDashBoard(Model model){
        List<User> userList = userService.getUserNew();
        List<Booking> bookingList = bookingService.getBookingNew();
        List<Hotel> hotelList = hotelService.getHotelNew();

        model.addAttribute("userRegisterByMonth" , dashboardService.getUserByMonth(5));
        model.addAttribute("totalBookingMonth" , dashboardService.getBookingByMonth(5));
        model.addAttribute("userList" , userList);
        model.addAttribute("totalUser" , userList.size());
        model.addAttribute("bookingList" , bookingList);
        model.addAttribute("hotelList" , hotelList);
        model.addAttribute("totalHotel" , hotelList.size());
        model.addAttribute("totalBooking" , bookingList.size());
        return "admin/dashboard/index";
    }



}
