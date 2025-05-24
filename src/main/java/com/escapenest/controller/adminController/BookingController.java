package com.escapenest.controller.adminController;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.Booking;
import com.escapenest.model.enums.PaymentMethod;
import com.escapenest.service.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping()
    public String viewPageBooking (@RequestParam(required = false , defaultValue = "1") Integer page ,
                                   @RequestParam(required = false , defaultValue = "10") Integer limit,
                                   Model model){
        Page<Booking> pageBooking = bookingService.getBookingAdmin(page,limit);
        model.addAttribute("pageBooking",pageBooking);
        model.addAttribute("contentPage",pageBooking.getContent());
        model.addAttribute("currentPage",page);
        return "admin/booking/index";
    }
    @GetMapping("/detail/{id}")
    public String detailPageBooking (Model model, @PathVariable Integer id){
        Booking booking = bookingService.getBooking(id);
        model.addAttribute("booking",booking);
        model.addAttribute("paymentMethods", PaymentMethod.values());
        return "/admin/booking/detail";
    }

}
