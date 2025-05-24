package com.escapenest.controller.adminController;

import lombok.RequiredArgsConstructor;
import com.escapenest.model.dto.RevenueDayDto;
import com.escapenest.service.BookingService;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/revenue")
public class RevenueController {
    private final BookingService bookingService ;
    @GetMapping
    public String pageRevenue (Model model){
        List<RevenueDayDto> revenueDtoListDay = bookingService.getRevenueByDay(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        int revenueYear = bookingService.totalRevenueYear(LocalDate.now().getYear());
        long revenueMonth = bookingService.totalMonthCurrent(LocalDate.now().getYear(),LocalDate.now().getMonthValue());
        System.out.println(LocalDate.now().getDayOfMonth()-1);
        model.addAttribute("revenueDay" , revenueDtoListDay.get(LocalDate.now().getDayOfMonth()-1).getTotalPrice());
        model.addAttribute("revenueYear" ,revenueYear);
        model.addAttribute("revenueMonth" ,revenueMonth);
        return "/admin/revenue/index" ;
    }
}
