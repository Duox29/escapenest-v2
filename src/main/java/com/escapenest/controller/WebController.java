package com.escapenest.controller;

import com.escapenest.entity.*;
import com.escapenest.model.dto.HotelDto;
import com.escapenest.model.enums.*;
import com.escapenest.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import com.escapenest.model.dto.CityDto;
import com.escapenest.model.dto.RoomDto;
import com.escapenest.model.response.VerifyAccountResponse;
import com.escapenest.repository.BookingRepository;
import com.escapenest.repository.ImageRepository;
import com.escapenest.repository.ImageRoomRepository;

import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final CityService cityService;
    private final RoomService roomService;
    private final SupportService supportService;
    private final AuthService authService;
    private final BookingService bookingService;

    private final HotelService hotelService;
    private final HttpSession session;
    private  final BookingRepository bookingRepository;
    private final ReviewsService reviewsService;
    private final ImageService imageService;
    private final AmenityService amenityService;
    private final ImageRoomRepository imageRoomRepository;
    private final ImageRepository imageRepository;


    @GetMapping("/")
    public String getHome(Model model) {
        List<CityDto> cityList = cityService.findCityFavourite();
        model.addAttribute("cityFavourite", cityList);
        return "web/home";
    }

    @GetMapping("/danh-sach-khach-san")
    public String   getHotelList(@RequestParam String nameCity,
                               @RequestParam(required = false) String checkIn,
                               @RequestParam(required = false) String checkOut,
                               @RequestParam(required = false, defaultValue = "1") Integer numberGuest,
                               @RequestParam(required = false, defaultValue = "1") Integer numberRoom,
                               Model model) {

        hotelService.getHotelBySearch(nameCity, checkIn, checkOut, numberGuest, numberRoom);
        if (session.getAttribute("MY_SESSION")!= null) {
            List<Hotel> hotelFavourite = hotelService.getAllHotelFavourite((String) session.getAttribute("MY_SESSION"));
            model.addAttribute("hotelFavourite", hotelFavourite);
        }
        City city = cityService.getCityByName(nameCity);
        model.addAttribute("city", city);
        model.addAttribute("listAmenityHotel", amenityService.getAllAmenityHotel().subList(0,10));
        model.addAttribute("listAmenityRoom", amenityService.getAllAmenityRoom().subList(0,10));
        model.addAttribute("city", city);
        model.addAttribute("nameCity", nameCity);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("numberGuest", numberGuest);
        model.addAttribute("numberRoom", numberRoom);
//        model.addAttribute("hotelList", hotelList);
        return "web/hotel-list";
    }

    @GetMapping("/danh-sach-khach-san-v2")
    public String getHotelListV2(@RequestParam String nameCity,
                                 @RequestParam(required = false) String checkIn,
                                 @RequestParam(required = false) String checkOut,
                                 @RequestParam(required = false, defaultValue = "1") Integer numberGuest,
                                 @RequestParam(required = false, defaultValue = "1") Integer numberRoom,
                                 Model model) {

        // Gọi service mới, lấy danh sách HotelDto
        List<HotelDto> hotelList = hotelService.getHotelBySearchV2(nameCity, checkIn, checkOut, numberGuest, numberRoom);

        // Nếu có session (đang đăng nhập) thì lấy homestay yêu thích
        if (session.getAttribute("MY_SESSION") != null) {
            List<Hotel> hotelFavourite = hotelService.getAllHotelFavourite((String) session.getAttribute("MY_SESSION"));
            model.addAttribute("hotelFavourite", hotelFavourite);
        }

        // Thêm các dữ liệu khác vào model để hiển thị trên Thymeleaf
        City city = cityService.getCityByName(nameCity);
        model.addAttribute("city", city);
        model.addAttribute("nameCity", nameCity);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("numberGuest", numberGuest);
        model.addAttribute("numberRoom", numberRoom);
        model.addAttribute("listAmenityHotel", amenityService.getAllAmenityHotel().subList(0, 10));
        model.addAttribute("listAmenityRoom", amenityService.getAllAmenityRoom().subList(0, 10));

        // Đây là điểm khác biệt: dùng dữ liệu từ hàm V2
        model.addAttribute("hotelList", hotelList);

        return "web/hotel-list"; // Trả về view Thymeleaf
    }



    @GetMapping("/chi-tiet-khach-san/{id}")
    public String getHotelDetail(@PathVariable Integer id, Model model,
                                 @RequestParam(value = "nameCity") String nameCity,
                                 @RequestParam(required = false) String checkIn,
                                 @RequestParam(required = false) String checkOut,
                                 @RequestParam(required = false, defaultValue = "1") Integer numberGuest,
                                 @RequestParam(required = false, defaultValue = "1") Integer numberRoom) {
        // lấy hotel theo id
        Hotel hotel = hotelService.getHotelById(id);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkInDay = LocalDate.parse(checkIn,dateTimeFormatter);
        LocalDate checkOutDay = LocalDate.parse(checkOut,dateTimeFormatter);
        List<RoomDto> roomList = roomService.getDataRoom(id,checkInDay,checkOutDay,numberGuest,numberRoom);
        // danh sách các review của homestay
        List<Review> reviewList = reviewsService.findAllReview(id);
        // lấy các tiện sub cac tiên ích của homestay
        List<AmenityHotel> listAmenity;
        if (hotel.getAmenityHotelList().size()>5){
            listAmenity = hotel.getAmenityHotelList().subList(0,5);
        }else {
            listAmenity = hotel.getAmenityHotelList();
        }
        model.addAttribute("reviewList" , reviewList);
        model.addAttribute("amenityHotels" , listAmenity);
        model.addAttribute("hotel", hotel);
        model.addAttribute("roomList", roomList);
        model.addAttribute("nameCity", nameCity);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("numberGuest", numberGuest);
        model.addAttribute("numberRoom", numberRoom);
        if (session.getAttribute("MY_SESSION")!=null){
            User user = authService.getUserCurrent();
            model.addAttribute("user" , user);
        }
        // lấy danh sách các tiện ích của phòng
        List<AmenityRoomType> amenityRoomTypes = List.of(AmenityRoomType.values());
        model.addAttribute("amenityRoomTypes", amenityRoomTypes);
        // lấy tất cả image hotel
        List<ImageHotel>  imageHotelList = imageService.getAllImageByIdHotel(hotel.getId());
        model.addAttribute("imageHotelList", imageHotelList);
        return "web/hotel-detail";
    }

    @GetMapping("/support")
    public String getSupport(Model model) {
        List<SupportType> supportTypes = List.of(SupportType.values());
        List<Support> supportList = supportService.getAllSupport();
        model.addAttribute("supportTypes", supportTypes);
        model.addAttribute("supportList", supportList);
        return "web/support";
    }

    @Secured({"ROLE_USER","ROLE_HOTEL","ROLE_ADMIN"})
    @GetMapping("/thong-tin-khach-hang")
    public String getProfile(Model model) {
        List<Gender> list = Arrays.stream(Gender.values()).toList();
        model.addAttribute("listGender", list );
        User user = authService.getUserCurrent();
        model.addAttribute("user" , user);

        ImageUser imageUser = imageService.getImageCurrentUser(user.getId());
        model.addAttribute("imageUser" , imageUser);

        return "web/profile-user";
    }

    @Secured({"ROLE_USER","ROLE_HOTEL","ROLE_ADMIN"})
    @GetMapping("/yeu-thich/{id}")
    public String getFavouriteHotel(Model model, @PathVariable Integer id ) {
        Map<City,Integer> mapHotelFavouriteByCity = hotelService.getNameCityHotelFavourite(id);
        model.addAttribute("mapHotelFavouriteByCity" , mapHotelFavouriteByCity);
        Boolean authenticated = SecurityContextHolder.getContext().getAuthentication() != null;
        model.addAttribute("authentication" , authenticated);
        return "web/favourite";
    }

    @Secured({"ROLE_USER","ROLE_HOTEL","ROLE_ADMIN"})
    @GetMapping("/danh-sach-dat-phong/{id}")
    public String getHistoryBooking(Model model,
                                    @RequestParam(required = false , defaultValue = "1") Integer pageNumber ,
                                    @RequestParam(required = false , defaultValue = "10") Integer limit,
                                    @PathVariable Integer id) {
        Page<Booking> page = bookingService.getAllBookingByIdUer(id,pageNumber,limit);
        model.addAttribute("page" , page);
        model.addAttribute("currentPage" , pageNumber);
        return "web/booking";
    }
    @Secured({"ROLE_USER","ROLE_HOTEL","ROLE_ADMIN"})
    @GetMapping("/cancel-booking/{id}")
    public String getCancelBooking(Model model, @PathVariable Integer id) {
        Booking booking = bookingRepository.findById(id).get();
        model.addAttribute("booking",booking);
        return "web/cancel-booking";
    }
    @Secured({"ROLE_USER","ROLE_HOTEL","ROLE_ADMIN"})
    @GetMapping("/chi-tiet-booking/{id}")
    public String getBookingDetail(Model model, @PathVariable Integer id) {
        Booking booking = bookingService.getBooking(id);

        // thứ ngày tháng việt nam
        Locale locale = new Locale("vi","VN");
        String dayOfWeekStar = booking.getCheckIn().getDayOfWeek().getDisplayName(TextStyle.SHORT,locale);
        String dayOfWeekEnd = booking.getCheckOut().getDayOfWeek().getDisplayName(TextStyle.SHORT,locale);
        Period period = Period.between(booking.getCheckIn(),booking.getCheckOut());

        model.addAttribute("booking" , booking);
        model.addAttribute("period" , period);
        model.addAttribute("dayOfWeekStar" , dayOfWeekStar);
        model.addAttribute("dayOfWeekEnd" , dayOfWeekEnd);
        return "web/booking-detail";
    }

    @Secured({"ROLE_USER","ROLE_HOTEL","ROLE_ADMIN"})
    @GetMapping("/danh-sach-yeu-thich/{idUer}/{city}")
    public String getFavouriteList( @RequestParam(required = false , defaultValue = "1") Integer pageNumber,
                                    @RequestParam(required = false , defaultValue = "8") Integer limit,
                                    Model model, @PathVariable Integer idUer ,@PathVariable String city) {
        Page<Hotel> hotelPage = hotelService.findHotelFavouriteByCity(idUer,city,pageNumber,limit);
        model.addAttribute("hotelPage",hotelPage);
        model.addAttribute("listHotel",hotelPage.getContent());
        model.addAttribute("indexPage",pageNumber);
        model.addAttribute("city",city);
        return "web/favourite-list";
    }


    @Secured({"ROLE_USER","ROLE_HOTEL","ROLE_ADMIN"})
    @GetMapping("/thanh-toan/{idHotel}/{idRoom}")
    public String getPayment(@PathVariable Integer idHotel,
                             @PathVariable Integer idRoom,
                             @RequestParam String checkIn,
                             @RequestParam String checkOut,
                             @RequestParam Integer numberGuest,
                             @RequestParam Integer numberRoom,
                             @RequestParam Integer price,
                             Model model) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(checkIn,dateTimeFormatter);
        LocalDate end = LocalDate.parse(checkOut,dateTimeFormatter);
        Hotel hotel = hotelService.getHotelById(idHotel);
        Room room = roomService.getRoomById(idRoom);
        String thumbnail = imageRepository.findFirstImageUrlByRoomId(room.getId());
        if(thumbnail == null) {
            thumbnail = "/web/assets/img/no-image.jpg"; // ảnh mặc định nếu không có ảnh
        }
        room.setThumbnailRoom(thumbnail);
        Locale locale = new Locale("vi","VN");
        String startDayOfWeek = start.getDayOfWeek().getDisplayName(TextStyle.SHORT, locale);
        String endDayOfWeek = end.getDayOfWeek().getDisplayName(TextStyle.SHORT, locale);
        Period period = Period.between(start,end);
        model.addAttribute("startDayOfWeek",startDayOfWeek);
        model.addAttribute("listPaymentMethod", PaymentMethod.values());
        model.addAttribute("endDayOfWeek",endDayOfWeek);
        model.addAttribute("start",start);
        model.addAttribute("end",end);
        model.addAttribute("hotel",hotel);
        model.addAttribute("room",room);
        model.addAttribute("period",period);
        model.addAttribute("numberGuest",numberGuest);
        model.addAttribute("numberRoom",numberRoom);
        model.addAttribute("price",price);
        model.addAttribute("totalPrice",price* period.getDays()*numberRoom);
        return "web/thong-tin-thanh-toan";
    }


    @GetMapping("/account/login")
    public String getLogin(Model model) {
        return "web/auth/login";
    }
    @GetMapping("/account/dang-ky")
    public String getRegister(Model model) {
        return "web/auth/register";
    }
    @GetMapping("/account/quen-mat-khau")
    public String getForgotPassword(Model model,@RequestParam(required = false) String token) {
       model.addAttribute("verifyToken" ,authService.verifyForgotPassword(token)) ;
       model.addAttribute("token" ,token) ;
        return "web/auth/forgot-password";
    }

    @GetMapping("/account/xac-minh-tai-khoan")
    public String getVerifyAccount(@RequestParam(required = false) String token, Model model) {
        VerifyAccountResponse data = authService.verifyAccount(token);
        model.addAttribute("data" , data);
        return "web/auth/verify-account";
    }

    @Secured("ROLE_USER")
    @GetMapping("/confirm/rental-registration")
    public String confirmRentalHotel (Model model){
        return "/web/rental-registration";
    }
    @GetMapping("/create-hotel")
    public String createHotel (Model model){
        List<City> cityList = cityService.getAllCity();
        List<AmenityHotel> amenityHotelList = amenityService.getAllAmenityHotel();
        List<AmenityRoom> amenityRoomList = amenityService.getAllAmenityRoom();
        model.addAttribute("amenityHotelList" , amenityHotelList.subList(0,6));
        model.addAttribute("amenityRoomList" , amenityRoomList.subList(0,6));
        model.addAttribute("rentalTypes" , RentalType.values());
        model.addAttribute("roomTypes" , RoomType.values());
        model.addAttribute("bedTypes" , BedType.values());
        model.addAttribute("bedSizes" , BedSize.values());
        model.addAttribute("cityList" ,cityList);
        return "/web/create-hotel";
    }
}
