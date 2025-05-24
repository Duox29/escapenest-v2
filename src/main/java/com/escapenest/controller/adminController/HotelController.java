package com.escapenest.controller.adminController;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.City;
import com.escapenest.entity.Hotel;
import com.escapenest.model.dto.RevenueMonthDto;
import com.escapenest.model.dto.TotalBookingMonthDto;
import com.escapenest.model.enums.RentalType;
import com.escapenest.service.BookingService;
import com.escapenest.service.CityService;
import com.escapenest.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;
    private final CityService cityService ;
    private final BookingService bookingService;

    @GetMapping
    public String viewHomePage(Model model, @RequestParam(required = false , defaultValue = "1") Integer page ,
                                            @RequestParam(required = false , defaultValue = "10") Integer limit){
        Page<Hotel> hotelPage = hotelService.getAllHotel(page, limit);
        model.addAttribute("hotelPage" , hotelPage);
        model.addAttribute("currentPage" , page);
        return "admin/hotel/index";
    }

    @GetMapping("/create")
    public String viewCreateHotelPage(Model model){
        List<City> cityList = cityService.getAllCity();
        System.out.println("nè"+cityList);
        model.addAttribute("rentalTypes" , RentalType.values());
        model.addAttribute("cityList" ,cityList);
        return "admin/hotel/create";
    }
    @GetMapping("/{id}/detail")
    public String viewDetailHotelPage(@PathVariable Integer id , Model model){
        Hotel hotel = hotelService.getHotelById(id);
        List<City> cityList = cityService.getAllCity();
        model.addAttribute("hotel" , hotel);
        model.addAttribute("rentalTypes" , RentalType.values());
        model.addAttribute("cityList" ,cityList);
        return "admin/hotel/detail";
    }
    @GetMapping("/report")
    public String getDashboardHotel(@RequestParam("id") Integer id, Model model){
        Hotel hotel = hotelService.getHotelById(id);
        // Tổng booking theo tháng
        TotalBookingMonthDto totalBookingMonthDto   = bookingService.getTotalBookingByMonCurrent(
                LocalDate.now().getMonthValue(),LocalDate.now().getYear(),id);
        // tổng booking đang chờ xác nhận
        TotalBookingMonthDto totalBookingMonthDtoPending = bookingService.getTotalBookingPendingMonthByHotel(id);
        long totalRevenueYear = bookingService.getTotalRevenueYearCurrent(id,LocalDate.now().getYear());
        RevenueMonthDto totalRevenueMonth = bookingService.getTotalRevenueMonth(id,LocalDate.now().getYear(),LocalDate.now().getMonthValue());

        System.out.println(totalBookingMonthDto + " Tổng doanh thu nè ");
        System.out.println(totalBookingMonthDtoPending + " Tổng doanh thu nè ");

        model.addAttribute("hotel" , hotel);
        model.addAttribute("totalRevenueYear" , totalRevenueYear);
        model.addAttribute("totalRevenueMonth" , totalRevenueMonth);
        model.addAttribute("totalBookingMonthDto" , totalBookingMonthDto);
        model.addAttribute("totalBookingMonthDtoPending" , totalBookingMonthDtoPending);
        return "admin/hotel/dashboard";
    }
}
