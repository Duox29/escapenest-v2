package com.escapenest.service;

import com.escapenest.entity.AmenityHotel;
import com.escapenest.entity.AmenityRoom;
import lombok.RequiredArgsConstructor;
import com.escapenest.exception.BadRequestException;
import com.escapenest.model.enums.AmenityHotelType;
import com.escapenest.model.enums.AmenityRoomType;
import com.escapenest.model.request.UpsertAmenityRequest;
import com.escapenest.repository.AmenityHotelRepository;
import com.escapenest.repository.AmenityRoomRepository;
import com.escapenest.repository.HotelRepository;
import com.escapenest.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AmenityService {

    private final AmenityHotelRepository amenityHotelRepository;
    private final AmenityRoomRepository amenityRoomRepository;
    private final HotelRepository hotelRepository ;
    private final RoomRepository roomRepository ;


    // lấy tất cả tiện ích phòng mà trang web đang có
    public List<AmenityRoom> getAllAmenityRoom() {
        return amenityRoomRepository.findAll();
    }

    // lấy tất cả tiện ích homestay mà trang web đang có
    public List<AmenityHotel> getAllAmenityHotel() {
        return amenityHotelRepository.findAll();
    }

    // admin cập nhật tiện ích homestay
    public AmenityHotel updateAmenityHotel(Integer id, UpsertAmenityRequest request) {
        // kiểm tra xem có tiện ích nào với id được gửi lên hay không ;
        AmenityHotel amenityHotel = amenityHotelRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy tiện ích nào có id :"+id));
        amenityHotel.setName(request.getName());
        amenityHotel.setAmenityHotelType(AmenityHotelType.valueOf(request.getTypeAmenity()));

        return amenityHotelRepository.save(amenityHotel);
    }

    // admin cập nhật tiện ích phòng
    public AmenityRoom updateAmenityRoom(Integer id, UpsertAmenityRequest request) {
        // kiểm tra xem có tiện ích nào với id được gửi lên hay không ;
        AmenityRoom amenityRoom = amenityRoomRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy tiện ích nào có id :"+id));
        amenityRoom.setName(request.getName());
        amenityRoom.setAmenityRoomType(AmenityRoomType.valueOf(request.getTypeAmenity()));
        amenityRoom.setUpdateAt(LocalDate.now());

        return amenityRoomRepository.save(amenityRoom);
    }


    // admin tạo tiện ích homestay
    public AmenityHotel createAmenityHotel(UpsertAmenityRequest request) {

        // kiểm tra xem tên tiện ích đã có hay chưa
        AmenityHotel amenityHotel = amenityHotelRepository.findByName(request.getName());
        if (amenityHotel!=null){
            throw  new RuntimeException("Tên tiện ích trên đã tồn tại ");
        }
        // tạo tiện ích mới
        AmenityHotel amenityHotelNew = new AmenityHotel();
        amenityHotelNew.setName(request.getName());
        amenityHotelNew.setCreatedAt(LocalDate.now());
        amenityHotelNew.setAmenityHotelType(AmenityHotelType.valueOf(request.getTypeAmenity()));
        return amenityHotelRepository.save(amenityHotelNew);
    }
    // admin tạo tiện ích phòng
    public AmenityRoom createAmenityRoom(UpsertAmenityRequest request) {
        // kiểm tra xem tên tiện ích đã có hay chưa
        AmenityRoom amenityRoom = amenityRoomRepository.findByName(request.getName());
        if (amenityRoom!=null){
            throw  new RuntimeException("Tên tiện ích trên đã tồn tại ");
        }

        // tạo tiện ích mới
        AmenityRoom amenityRoomNew = new AmenityRoom();
        amenityRoomNew.setName(request.getName());
        amenityRoomNew.setCreatedAt(LocalDate.now());
        amenityRoomNew.setAmenityRoomType(AmenityRoomType.valueOf(request.getTypeAmenity()));
        return amenityRoomRepository.save(amenityRoomNew);
    }

    // Xóa tiện ích
    public void deleteAmenity(Integer id) {
        AmenityHotel amenityHotel = amenityHotelRepository.findById(id).orElseThrow(() ->new RuntimeException("Không tìm thấy tiện ích nào có id"+id));
        long countHotel = hotelRepository.countByAmenityHotelList_Id(id);
        long countRoom = roomRepository.countByAmenityRoomList_Id(id);
        if (countRoom>0 || countHotel>0){
            throw new BadRequestException("Không thể xóa tiện ích này đi vì tiện ích đang được sử dụng");
        }amenityHotelRepository.delete(amenityHotel);
    }


    // lấy danh sách các tiện ích khi đối tác tạo thông tin homestay
    public List<AmenityHotel> getAllAmenityHotelById(List<Integer> amenityHotelListId) {
        List<AmenityHotel> amenityHotelList = new ArrayList<>();
        // kiểm tra danh sách id được gửi lên và lấy ra tiện ích với id tương ứng
        for (Integer id : amenityHotelListId){
            amenityHotelList.add(amenityHotelRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Không có tiện ích nào có id :" + id)));
        }
        return amenityHotelList;
    }
}
