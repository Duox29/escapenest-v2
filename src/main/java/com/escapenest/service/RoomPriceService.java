package com.escapenest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.escapenest.entity.Hotel;
import com.escapenest.entity.Room;
import com.escapenest.entity.RoomPrice;
import com.escapenest.model.dto.RoomPriceDto;
import com.escapenest.model.request.RoomPriceRequest;
import com.escapenest.repository.RoomPriceRepository;
import com.escapenest.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class RoomPriceService {
    private final RoomPriceRepository roomPriceRepository;
    private final RoomRepository roomRepository;
    private final HotelService hotelService;


    //đặt giá phòng cho từng phòng và từng ngày riêng biệt
    public void setRoomPrice(RoomPriceRequest request) {
        // lấy homestay đang đăng nhập
        Hotel hotel = hotelService.getHotelByAccountCurrent();
        // lấy danh sách phòng của homestay đó
        Room room = roomRepository.findById(request.getIdRoom())
                .orElseThrow(()-> new RuntimeException("Không tìm thấy phòng nòa có id : "+ request.getIdRoom()));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // lấy ngày bắt đầu và ngày kết thúa( theo ngày mà người dùng đã chọn )
        LocalDate startDate = LocalDate.parse(request.getStartDate(),dateTimeFormatter);
        LocalDate endDate = LocalDate.parse(request.getEndDate(),dateTimeFormatter);
        // taạo đối tượng đê lấy ra thông tin và  khoảng cách giữa 2 ngày
        Period period = Period.between(startDate,endDate);
        // duyệt từng từ ngày bắt đầu đến ngày  kết thúc
        for (int i = 0 ; i <= period.getDays(); i++) {
            // tạo đối tượng localdate cho từng ngày cụ thể
            LocalDate localDate = startDate.plusDays(i);
            // kiểm tra ngày đó đã được set giá hay chưa
            RoomPrice roomPricePresent = roomPriceRepository.findByDateAndRoom_Id(localDate, request.getIdRoom());

            for (String day : request.getDayApply()){
                // nếu ngày đó  đã đuợc set giá trước đó thì chỉ cần thay đổi giá của ngày hôm đó thôi ,  không cần tạo đối tượng mới
                if (roomPricePresent!= null &&
                        roomPricePresent.getDate().getDayOfWeek().toString().equalsIgnoreCase(day)){
                    // set giá rồi lưu lại vào database
                    roomPricePresent.setPrice(request.getPrice());
                    roomPriceRepository.save(roomPricePresent);
                    break;
                }
                // nếu chưa được set cho ngày này thì tạo một đối tượng mới rồi lưu lại vào dâtbase
                else if (roomPricePresent == null && day.equalsIgnoreCase(localDate.getDayOfWeek().toString())){
                    RoomPrice roomPrice = RoomPrice.builder()
                            .date(localDate)
                            .price(request.getPrice())
                            .hotel(hotel)
                            .room(room)
                            .createdAt(LocalDate.now())
                            .build();
                    roomPriceRepository.save(roomPrice);
                }
            }
        }

    }


    // đặt giá phòng cho từng loại phòng và từng ngày riêng
    public List<RoomPriceDto> getRoomPriceDay(String date) {
        // lấy ra homestay đang đăng nhập hiện tại
        Hotel hotel = hotelService.getHotelByAccountCurrent();
        // lấy các danh sách phòng của homestay đó
        List<Room> roomList = roomRepository.findRoomByHotel_Id(hotel.getId());
        // lấy ra ngày mà người dùng muốn xem giá của từng phòng
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date,dateTimeFormatter);
        List<RoomPriceDto> roomPriceDtos = new ArrayList<>();
        // tìm các RoomPrice theo ngày hôm đó
        List<RoomPrice> roomPriceList = roomPriceRepository.findAllByDate(localDate);
        // duyệt từng phòng
        for (int i = 0; i < roomList.size(); i++) {
            boolean check = false;
            // duyệt từng Roomprice để xem rằng với vòng hiện tại thì đã được set giá cho ngày hôm đó hay chưa
            for (RoomPrice roomPrice : roomPriceList) {
                // nếu chưa được set giá thì lấy giá default của phòng
                if (Objects.equals(roomPrice.getHotel().getId(), hotel.getId())
                        && Objects.equals(roomList.get(i).getId(), roomPrice.getRoom().getId())){
                    roomPriceDtos.add(new RoomPriceDto(roomPrice.getRoom().getName(), roomPrice.getPrice(),
                            roomPrice.getRoom().getRoomType().getValue(),roomPrice.getDate()));
                    check = true;
                    break;
                }

            }
            // lấy ra giá default của phòng
            if (!check){
                Room room = roomList.get(i);
                RoomPriceDto roomPriceDto = new RoomPriceDto(room.getName(),room.getPriceDefault(),room.getRoomType().getValue(),localDate);
                roomPriceDtos.add(roomPriceDto);
            }
        }
        return roomPriceDtos;
    }
}
