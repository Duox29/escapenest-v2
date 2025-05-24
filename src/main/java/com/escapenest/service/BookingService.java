package com.escapenest.service;

import com.escapenest.model.dto.RevenueDayDto;
import com.escapenest.model.dto.RevenueMonthDto;
import com.escapenest.model.dto.TotalBookingByRoomTypeDto;
import com.escapenest.model.dto.TotalBookingMonthDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.escapenest.entity.Booking;
import com.escapenest.entity.Hotel;
import com.escapenest.entity.Room;
import com.escapenest.entity.User;
import com.escapenest.model.request.UpsertBookingRequest;
import com.escapenest.model.enums.PaymentMethod;
import com.escapenest.model.enums.StatusBooking;
import com.escapenest.repository.BookingRepository;
import com.escapenest.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class    BookingService {
    private final BookingRepository bookingRepository;
    private final AuthService authService;
    private final HotelService hotelService;
    private final RoomService roomService;
    private final UserService userService;
    private final RoomRepository roomRepository;

    public Page<Booking> getAllBookingByIdUer(Integer id, Integer pageNumber, Integer limit) {
        Pageable pageable = PageRequest.of(pageNumber - 1, limit, Sort.by("createAt").descending());
        return bookingRepository.findAllByUser_IdOrderByCreateAtDesc(id, pageable);
    }

    //
    @Transactional
    public Booking bookingHotel(UpsertBookingRequest bookingRequest) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkIn = LocalDate.parse(bookingRequest.getCheckIn(), dateTimeFormatter);
        LocalDate checkOut = LocalDate.parse(bookingRequest.getCheckOut(), dateTimeFormatter);
        // lấy ra phòng mà người dùng đã yêu cầu đặt
        Room room = roomService.getRoomById(bookingRequest.getIdRoom());

        if(!roomService.roomCheck(room,checkIn,checkOut,bookingRequest.getNumberRoom())) {
            throw new RuntimeException("Số lượng phòng không đủ, vui lòng thử lại");
        }
        if (room.getCapacity()< bookingRequest.getGuest()/ bookingRequest.getNumberRoom()){
            throw new RuntimeException("Số người vượt quá giới hạn, vui lòng thử lại");
        }

        String strPaymentMethod = bookingRequest.getPaymentMethod().trim();
        PaymentMethod paymentMethod = PaymentMethod.valueOf(strPaymentMethod);
        User user = authService.getUserCurrent();
        Booking booking = Booking.builder()
                .user(user)
                .hotel(hotelService.getHotelById(bookingRequest.getIdHotel()))
                .room(roomService.getRoomById(bookingRequest.getIdRoom()))
                .nameCustomer(bookingRequest.getNameCustomer())
                .emailCustomer(bookingRequest.getEmailCustomer())
                .phoneCustomer(bookingRequest.getPhoneCustomer())
                .guests(bookingRequest.getGuest())
                .checkIn(checkIn)
                .checkOut(checkOut)
                .isReviewed(false)
                .price(bookingRequest.getPrice())
                .numberRoom(bookingRequest.getNumberRoom())
                .paymentMethod(paymentMethod)
                .statusBooking(StatusBooking.PENDING)
                .createAt(LocalDate.now())
                .build();
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingById(Integer id) {
//        User user = userService.getUserCurrent();
//        List<Booking> bookingList = bookingRepository.findAllByUser_Id(user.getId());
//        for (Booking booking : bookingList){
//            if(booking.getId() == id){
//                BookingDto bookingDto = new BookingDto();
//                bookingDto.setId(bookingDto.getId());
//                bookingDto.setGuests(bookingDto.getGuests());
//                bookingDto.setCheckIn(bookingDto.getCheckIn());
//                bookingDto.setCheckOut(bookingDto.getCheckOut());
//                bookingDto.setNumberRoom(bookingDto.getNumberRoom());
//                bookingDto.setNameRoom(bookingDto.getNameRoom());
//                bookingDto.y(bookingDto.getId());
//                bookingDto.setId(bookingDto.getId());
//            }
//        }
        return List.of(bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Không thấy booking nào có id như trên ")));
    }

    public Booking getBooking(Integer id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Không thể tìm thấy booking như trên "));
    }

    public void cancelBooking(Integer id) {
        Booking booking = getBooking(id);
        booking.setStatusBooking(StatusBooking.CANCELLED);
        bookingRepository.save(booking);
    }

    public Page<Booking> getBookingAdmin(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createAt").descending());
        return bookingRepository.findAll(pageable);
    }


    @Transactional
    public void updateBooking(Integer id, UpsertBookingRequest bookingRequest) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Không thể tìm thấy booking có id :" + id));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkIn = LocalDate.parse(bookingRequest.getCheckIn(), dateTimeFormatter);
        LocalDate checkOut = LocalDate.parse(bookingRequest.getCheckOut(), dateTimeFormatter);
        String strPaymentMethod = bookingRequest.getPaymentMethod().trim();
        PaymentMethod paymentMethod = PaymentMethod.valueOf(strPaymentMethod);
        booking.setRoom(roomService.getRoomById(bookingRequest.getIdRoom()));
        booking.setNameCustomer(bookingRequest.getNameCustomer());
        booking.setEmailCustomer(bookingRequest.getEmailCustomer());
        booking.setPhoneCustomer(bookingRequest.getPhoneCustomer());
        booking.setGuests(bookingRequest.getGuest());
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        booking.setPrice(bookingRequest.getPrice());
        booking.setNumberRoom(bookingRequest.getNumberRoom());
        booking.setPaymentMethod(paymentMethod);
        booking.setStatusBooking(StatusBooking.CONFIRMED);
        booking.setUpdateAt(LocalDate.now());
        bookingRepository.save(booking);
    }

    public List<Booking> getBookingNew() {

        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        return bookingRepository.findBookingByCreateAtBetweenAndStatusBookingOrderByCreateAtDesc(start, end, StatusBooking.CONFIRMED);
    }

    public List<RevenueDayDto> getRevenueByDay(Integer year, Integer month) {
        List<RevenueDayDto> revenueDtoList = bookingRepository.getTotalRevenueDay();
        System.out.println("Lấy doanh thu"+revenueDtoList);
        List<RevenueDayDto> result = new ArrayList<>();
        LocalDate dateSelect = LocalDate.of(year, month, 1);

        for (int i = 0; i < dateSelect.lengthOfMonth(); i++) {
            LocalDate localDate = dateSelect.plusDays(i);
            boolean check = false;
            for (RevenueDayDto revenueDto1 : revenueDtoList) {
                if (revenueDto1.getMonth() == localDate.getMonthValue() &&
                        revenueDto1.getDay() == localDate.getDayOfMonth() &&
                        revenueDto1.getYear() == localDate.getYear()) {
                    // Tính 15% doanh thu
                    double revenuePercentage = revenueDto1.getTotalPrice() * 0.15;
                    revenueDto1.setTotalPrice((long) revenuePercentage);

                    result.add(revenueDto1);
                    check = true;
                    break;

                }
            }
            if (!check) {
                RevenueDayDto revenueDto = new RevenueDayDto();
                revenueDto.setMonth(localDate.getMonthValue());
                revenueDto.setDay(localDate.getDayOfMonth());
                revenueDto.setYear(localDate.getYear());
                revenueDto.setTotalPrice(0);
                result.add(revenueDto);
            }
        }
        return result;
    }

    public List<RevenueMonthDto> getRevenueByMonth(Integer year) {
        List<RevenueMonthDto> revenueDtoList = bookingRepository.getTotalRevenueByMonth();
        System.out.println("Lấy doanh thu"+revenueDtoList);
        List<RevenueMonthDto> result = new ArrayList<>();
        LocalDate current = LocalDate.of(year, 1, 1);

        for (int i = 0; i < 12; i++) {
            LocalDate localDate = current.plusMonths(i);
            boolean check = false;
            for (RevenueMonthDto revenueDto1 : revenueDtoList) {
                if (revenueDto1.getMonth() == localDate.getMonthValue() &&
                        revenueDto1.getYear() == localDate.getYear()) {
                    result.add(revenueDto1);
                    check = true;
                    break;

                }
            }
            if (!check) {
                RevenueMonthDto revenueDto = new RevenueMonthDto();
                revenueDto.setMonth(localDate.getMonthValue());
                revenueDto.setYear(year);
                revenueDto.setTotalPrice(0);
                result.add(revenueDto);
            }
        }
        return result;
    }

    // lấy tổng doanh thu của năm hiện tại
    public int totalRevenueYear(int year) {
        // lấy doanh thu của từng tháng rồi ộng vào
        List<RevenueMonthDto> result = getRevenueByMonth(year);
        System.out.println("Doanh thu nè năm" +result.toString());
        int sum = 0;
        for (RevenueMonthDto revenueMonthDto : result) {
            System.out.println("Doanh thu nè tháng"+revenueMonthDto);

            sum += (int) revenueMonthDto.getTotalPrice();
        }
        return (int) (sum * 0.15);
    }

    public long totalMonthCurrent(int year, int monthValue) {
        List<RevenueDayDto> result = getRevenueByDay(year, monthValue);
        System.out.println("Doanh thu nè tháng"+result.toString());
        long sum = 0;
        for (RevenueDayDto revenueDayDto : result) {
            System.out.println("Doanh thu nè tháng"+revenueDayDto);
            sum += revenueDayDto.getTotalPrice();
        }
        return sum;
    }

    public List<Booking> getAllBookingHotel() {
        Hotel hotel = hotelService.getHotelByAccountCurrent();
        return bookingRepository.findAllByHotel_Id(hotel.getId());
    }

    public void confirmBooking(Integer id ) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking nào có id : " + id));
       booking.setStatusBooking(StatusBooking.CONFIRMED);
       Room room = booking.getRoom();
       Integer current = room.getUsedQuantity();
       if(current == null){
           current = 0;
       }
       room.setUsedQuantity(current+1);
       bookingRepository.save(booking);
    }
    public void rejectBooking(Integer id ) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking nào có id : " + id));
        booking.setStatusBooking(StatusBooking.REJECTED);
        bookingRepository.save(booking);
    }

    public void confirmCheckOutBooking(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking nào có id : " + id));
        booking.setStatusBooking(StatusBooking.COMPLETE);
        Room room = booking.getRoom();
        Integer current = room.getUsedQuantity();
        room.setUsedQuantity(current-1);
        bookingRepository.save(booking);
    }

    // doanh thu của từng ngày của tháng được chọn
    public List<RevenueDayDto> getRevenueByMonthAndHotel(Integer idHotel, Integer month, Integer year) {
        List<RevenueDayDto> revenueMonthDtoList =bookingRepository.getTotalRevenueByMonth(idHotel);

        List<RevenueDayDto> result = new ArrayList<>();
        LocalDate dateSelect = LocalDate.of(year, month, 1);

        for (int i = 0; i < dateSelect.lengthOfMonth(); i++) {
            LocalDate localDate = dateSelect.plusDays(i);
            boolean check = false;
            for (RevenueDayDto revenueDto1 : revenueMonthDtoList) {
                if (revenueDto1.getMonth() == localDate.getMonthValue() &&
                        revenueDto1.getDay() == localDate.getDayOfMonth() &&
                        revenueDto1.getYear() == localDate.getYear()) {
                    revenueDto1.setTotalPrice((long) (revenueDto1.getTotalPrice()*0.85));
                    result.add(revenueDto1);
                    check = true;
                    break;

                }
            }
            if (!check) {
                RevenueDayDto revenueDto = new RevenueDayDto();
                revenueDto.setMonth(localDate.getMonthValue());
                revenueDto.setDay(localDate.getDayOfMonth());
                revenueDto.setYear(localDate.getYear());
                revenueDto.setTotalPrice(0);
                result.add(revenueDto);
            }
        }
        return result;

    }

    public List<TotalBookingByRoomTypeDto> getTotalBookingByRoomType (Integer idHotel , Integer year){
        Hotel hotel  = hotelService.getHotelById(idHotel);
        List<Room> roomList = roomService.getRoomByIdHotel(idHotel);
        List<TotalBookingByRoomTypeDto> result = new ArrayList<>();
       List<TotalBookingByRoomTypeDto> totalBookingByRoomTypeDtoList = bookingRepository.getTotalBookingByRoomTypeAndYear(idHotel,year);
        for (int i = 0; i < roomList.size(); i++) {
            boolean check = false;
            for (TotalBookingByRoomTypeDto totalBookingByRoomTypeDto : totalBookingByRoomTypeDtoList){
                if (totalBookingByRoomTypeDto.getTypeRoom().equals(roomList.get(i).getName())){
                    result.add(totalBookingByRoomTypeDto);
                    check=true;
                    break;
                }
            }
            if (!check){
                TotalBookingByRoomTypeDto totalBookingByRoomTypeDtoNew = new TotalBookingByRoomTypeDto();
                totalBookingByRoomTypeDtoNew.setTotalBooking(0);
                totalBookingByRoomTypeDtoNew.setTypeRoom(roomList.get(i).getName());
                result.add(totalBookingByRoomTypeDtoNew);
            }

        }
        return result;
    }

    public List<RevenueMonthDto> getRevenueByYearAndHotel(Integer idHotel, Integer year) {
        List<RevenueMonthDto> revenueDtoList = bookingRepository.getTotalRevenueByMonthAndHotelId(idHotel,year);
        List<RevenueMonthDto> result = new ArrayList<>();
        LocalDate current = LocalDate.of(year, 1, 1);
        for (int i = 0; i < 12; i++) {
            LocalDate localDate = current.plusMonths(i);
            boolean check = false;
            for (RevenueMonthDto revenueDto1 : revenueDtoList) {
                if (revenueDto1.getMonth() == localDate.getMonthValue()) {
                    revenueDto1.setTotalPrice((long) (revenueDto1.getTotalPrice()*0.85));
                    result.add(revenueDto1);
                    check = true;
                    break;

                }
            }
            if (!check) {
                RevenueMonthDto revenueDto = new RevenueMonthDto();
                revenueDto.setMonth(localDate.getMonthValue());
                revenueDto.setYear(year);
                revenueDto.setTotalPrice(0);
                result.add(revenueDto);
            }
        }
        return result;
    }

    public TotalBookingMonthDto getTotalBookingByMonCurrent(int monthValue, int year , int idHotel) {
        Optional<TotalBookingMonthDto> result = bookingRepository.findTotalBookingMonthByHotel(idHotel, monthValue, year);
        return result.orElse(new TotalBookingMonthDto(monthValue, year, 0)); // Trả về đối tượng mới với giá trị 0
    }
    public TotalBookingMonthDto getTotalBookingPendingMonthByHotel( int idHotel) {
        Optional<TotalBookingMonthDto> result = bookingRepository.findTotalBookingPendingMonthByHotel(idHotel);
        return result.orElse(new TotalBookingMonthDto(LocalDate.now().getMonthValue(),LocalDate.now().getYear(),0)); // Trả về đối tượng mới với giá trị 0
    }

    // tổng doanh thu sau triết khấu của năm hiện tại
    public long getTotalRevenueYearCurrent(Integer idHotel,Integer year) {
        List<RevenueMonthDto> revenueMonthDtoList = getRevenueByYearAndHotel(idHotel,year);
        long sum = 0;
        for (RevenueMonthDto revenueMonthDto : revenueMonthDtoList){
            sum += revenueMonthDto.getTotalPrice();
        }
        return sum ;
    }

    // tỏng doanh thu tháng hện tịa
    public RevenueMonthDto getTotalRevenueMonth(Integer id, int year, int monthValue) {
        List<RevenueMonthDto> revenueMonthDtoList = bookingRepository.getTotalRevenueByMonthAndHotelId(id,year);
        for (RevenueMonthDto revenueMonthDto : revenueMonthDtoList){
            if (revenueMonthDto.getMonth()==monthValue && revenueMonthDto.getYear()==year){
                revenueMonthDto.setTotalPrice((long) (revenueMonthDto.getTotalPrice()*0.85));
                return revenueMonthDto;
            }
        }
        RevenueMonthDto revenueMonthDtoNew = new RevenueMonthDto();
        revenueMonthDtoNew.setYear(year);
        revenueMonthDtoNew.setTotalPrice(0);
        revenueMonthDtoNew.setMonth(monthValue);
        return  revenueMonthDtoNew;
    }

    public void deleteBooking(Integer idBooking) {
        Booking booking = bookingRepository.findById(idBooking).orElseThrow(() -> new RuntimeException("Không tìm thấy id nào booking trên ."));
        bookingRepository.delete(booking);
    }

    public void editStatus(Integer id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy id nào booking trên ."));
        booking.setStatusBooking(StatusBooking.CANCELLED);
        bookingRepository.save(booking);

    }
}
