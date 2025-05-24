package com.escapenest.controller.managementController;

import lombok.RequiredArgsConstructor;
import com.escapenest.entity.Booking;
import com.escapenest.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/hotel-manager/bookings")
@RequiredArgsConstructor
public class BookingHotelController {
	private final BookingService bookingService;

	@GetMapping()
	public String getBookingByHotel(Model model) {
		List<Booking> bookingList = bookingService.getAllBookingHotel();
		model.addAttribute("bookings", bookingList);
		return "/hotel-management/bookings/booking-hotel";
	}

	@GetMapping("/{id}")
	public String bookingDetail(Model model, @PathVariable Integer id) {
		Booking booking = bookingService.getBooking(id);
		model.addAttribute("booking", booking);
		return "/hotel-management/bookings/detail";
	}
}
