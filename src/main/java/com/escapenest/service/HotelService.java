package com.escapenest.service;

import com.escapenest.entity.*;
import com.escapenest.model.dto.HotelDto;
import com.escapenest.model.dto.RoomDto;
import com.escapenest.repository.*;
import jakarta.persistence.EntityNotFoundException;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.escapenest.exception.BadRequestException;
import com.escapenest.model.enums.UserRole;
import com.escapenest.model.request.UpsertHotelRequest;
import com.escapenest.model.enums.RentalType;
import com.escapenest.model.request.UpsertPolicyRequest;
import com.escapenest.model.request.UpsertRoomRequest;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final AmenityHotelRepository amenityHotelRepository;
    private final CityService cityService;
    private final AmenityService amenityService;
    private final PolicyService policyService;
    private final UserService userService;
    private final RoomService roomService;
    private final ImageService imageService;
    private final ImageHotelRepository imageHotelRepository;
    private final ImageRoomRepository imageRoomRepository;
    private final BookingRepository bookingRepository;

    private final MailService mailService;
    private final HttpSession httpSession;

    public List<HotelDto> hotelListSearch = new ArrayList<>();

    // logic tìm kiếm khách sạn
    public void getHotelBySearch(String nameCity, String checkIn, String checkOut, Integer numberGuest, Integer numberRoom) {
        // lấy ngày checkIn và checkOut từ string
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkInDay = LocalDate.parse(checkIn, dateTimeFormatter);
        LocalDate checkOutDay = LocalDate.parse(checkOut, dateTimeFormatter);

        // lấy danh sách khách sạn theo city
        List<Hotel> hotelList = hotelRepository.findHotelByCity_NameIgnoreCaseAndStatusTrue(nameCity);
        List<HotelDto> availableHotels = new ArrayList<>();
        // duyệt từng khách sạn để kiểm tra các điều kiện
        for (Hotel hotel : hotelList) {
            // laays ra các phòng trống của khách sạn
            List<RoomDto> dataRoom = roomService.getDataRoom(hotel.getId(), checkInDay, checkOutDay, numberRoom, numberGuest);
            if (!dataRoom.isEmpty()) {
                // lấy ra các thông tin của phòng và giá theo thời điểm của ngày chekIn và checkOut
                HotelDto hotelDto = createHotelDto(hotel, dataRoom);
                availableHotels.add(hotelDto);
            }
        }

        hotelListSearch = availableHotels;
    }

    public List<HotelDto> getHotelBySearchV2(String nameCity, String checkIn, String checkOut, Integer numberGuest, Integer numberRoom) {
        // Chuyển đổi chuỗi ngày sang LocalDate
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkInDay = LocalDate.parse(checkIn, dateTimeFormatter);
        LocalDate checkOutDay = LocalDate.parse(checkOut, dateTimeFormatter);

        // Lấy danh sách khách sạn theo tên thành phố và có trạng thái hoạt động
        List<Hotel> hotelList = hotelRepository.findHotelByCity_NameIgnoreCaseAndStatusTrue(nameCity);
        List<HotelDto> availableHotels = new ArrayList<>();

        // Duyệt qua từng khách sạn để kiểm tra phòng trống
        for (Hotel hotel : hotelList) {
            List<RoomDto> dataRoom = roomService.getDataRoom(
                    hotel.getId(), checkInDay, checkOutDay, numberRoom, numberGuest
            );

            if (!dataRoom.isEmpty()) {
                HotelDto hotelDto = createHotelDto(hotel, dataRoom);
                availableHotels.add(hotelDto);
            }
        }

        return availableHotels;
    }

    // logic lấy thông tin để hiển thị tại homepage
    public void getHotelAll(String nameCity, String checkIn, String checkOut, Integer numberGuest, Integer numberRoom) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkInDay = LocalDate.parse(checkIn, dateTimeFormatter);
        LocalDate checkOutDay = LocalDate.parse(checkOut, dateTimeFormatter);
        List<Hotel> hotelList = hotelRepository.findHotelByCity_NameIgnoreCaseAndStatusTrue(nameCity);
        List<HotelDto> availableHotels = new ArrayList<>();
        for (Hotel hotel : hotelList) {
            List<RoomDto> dataRoom = roomService.getDataRoomNoCheck(hotel.getId(), checkInDay, checkOutDay, numberRoom, numberGuest);
            if (!dataRoom.isEmpty()) {
                HotelDto hotelDto = createHotelDto(hotel, dataRoom);
                availableHotels.add(hotelDto);
            }
        }
        hotelListSearch = availableHotels;
    }


    // tạo một HotelDto từ một hotel
    private HotelDto createHotelDto(Hotel hotel, List<RoomDto> dataRoom) {
        Set<String> nameAmenityRoom = new HashSet<>();
        for (int i = 0; i < dataRoom.size(); i++) {
           RoomDto room = dataRoom.get(i);
           for (AmenityRoom amenityRoom : room.getAmenityRoomList()){
               nameAmenityRoom.add(amenityRoom.getName());
           }
        }
        System.out.println("log " + nameAmenityRoom);
        // lấy giá thấp nhất của khách sạn để hiện thị cho người dùng
        int price = getMinPrice(dataRoom);
        int totalReview = reviewRepository.findReviewByHotel_Id(hotel.getId()).size();
        // tạo một dối tượng chữa các thông tin cần thiết của khách sạn
        HotelDto hotelDto = new HotelDto();
        hotelDto.setId(hotel.getId());
        hotelDto.setName(hotel.getName());
        hotelDto.setAddress(hotel.getAddress());
        hotelDto.setTotalReviews(totalReview);
        hotelDto.setRentalType(hotel.getRentalType());
        hotelDto.setStar(hotel.getStar());
        hotelDto.setPoster(hotel.getPoster());
        hotelDto.setRating(hotel.getRating());
        hotelDto.setRatingText(hotel.getRatingText());
        hotelDto.setEstimatedPrice(price);
        hotelDto.setNameAmenityRoom(nameAmenityRoom);
        hotelDto.setNameAmenity(hotel.getAmenityHotelList().stream().map(Amenity::getName).toList());
        return hotelDto;
    }

    // logic lấy giá phòng nhỏ nhất của thời gian người dùng chọn hiển thị ra ngoài giao diện
    public int getMinPrice(List<RoomDto> roomDtoList) {
        // kiểm tra các thông tin của phòng có null hoặc rỗng hay không
        if (roomDtoList == null || roomDtoList.isEmpty()) {
            throw new NoSuchElementException("Không tìm thấy dữ liệu");
        }
        // duyệt từng đối tượng để lấy ra giá phòng rẻ nhất ngày hôm đó
        int minPrice = Integer.MAX_VALUE;
        for (RoomDto roomDto : roomDtoList) {
            if (roomDto.getPriceAverage() < minPrice) {
                minPrice = roomDto.getPriceAverage();
            }
        }
        return minPrice;
    }

    // phân trang cho trang danh sách khách sạn sau khi tìm kiếm
    @Transactional
    public Page<HotelDto> getPaginationHotel(Integer pageNumber, Integer limit) {
        Pageable pageable = PageRequest.of(pageNumber - 1, limit);
        return PageableExecutionUtils.getPage(
                hotelListSearch.subList((int) pageable.getOffset(), Math.min((int) pageable.getOffset() + pageable.getPageSize(), hotelListSearch.size())),
                pageable, () -> hotelListSearch.size()
        );
    }

    public Hotel getHotelById(Integer id) {
        return hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khách sạn nào tương ứng"));
    }

    public List<HotelDto> getHotelHomPage(String city, String checkIn, String checkOut, Integer numberGuest, Integer numberRoom) {
        getHotelBySearch(city, checkIn, checkOut, numberGuest, numberRoom);

        List<HotelDto> hotelDtos =hotelListSearch ;

        // Sắp xếp danh sách theo rating giảm dần
        Collections.sort(hotelDtos, new Comparator<HotelDto>() {
            @Override
            public int compare(HotelDto o1, HotelDto o2) {
                return Double.compare(o2.getRating(), o1.getRating());
            }
        });

        if (hotelDtos.size() > 8) {
            return hotelDtos.subList(0, 8);
        }
        return hotelListSearch;
    }

    public List<HotelDto> getAllHotelHomepage(String city, String checkIn, String checkOut, Integer numberGuest, Integer numberRoom){
        getHotelAll(city, checkIn,checkOut,numberGuest,numberRoom);
        List<HotelDto> hotelDtos = hotelListSearch;
        Collections.sort(hotelDtos, new Comparator<HotelDto>() {
            @Override
            public int compare(HotelDto o1, HotelDto o2) {
                return Double.compare(o2.getRating(), o1.getRating());
            }
        });
        if (hotelDtos.size() > 4) {
            return hotelDtos.subList(0, 4);
        }
        return hotelListSearch;
    }

    // tìm kiếm các khách sạn yêu thích theo thành phố
    public Page<Hotel> findHotelFavouriteByCity(Integer id, String city, Integer pageNumber, Integer limit) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, limit);
        // lấy ra danh sách khách sạn được yêu thích của user
        List<Hotel> hotelList = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy khách sạn nào có id " + id)).getHotelList().stream()
                .filter(hotel -> Objects.equals(hotel.getCity().getName(), city))
                .toList();

        return PageableExecutionUtils.getPage(
                hotelList.subList((int) pageRequest.getOffset(), Math.min((int) pageRequest.getOffset() + pageRequest.getPageSize(), hotelList.size())), pageRequest, hotelList::size

        );

    }

    public Map<City, Integer> getNameCityHotelFavourite(Integer id) {
        List<Hotel> hotelList = userRepository.findById(id).get().getHotelList();
        Map<City, Integer> myMap = new HashMap<>();
        for (Hotel hotel : hotelList) {
            if (!myMap.containsKey(hotel.getCity())) {
                int count = 0;
                for (Hotel value : hotelList) {
                    if (value.getCity().getName().contains(hotel.getCity().getName())) {
                        count++;
                    }
                }
                myMap.put(hotel.getCity(), count);
            }
        }
        return myMap;
    }

    public List<Hotel> getAllHotelFavourite(String email) {
        User optionalUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng nào có email như :" + email));
        return optionalUser.getHotelList();

    }

    public Page<Hotel> getAllHotel(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
        return hotelRepository.findAll(pageable);
    }

    public Hotel updateHotelAdmin(Integer id, UpsertHotelRequest request) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách sạn nào có id : " + id));
        City city = cityService.getCityById(request.getIdCity());
        RentalType rentalType = RentalType.valueOf(request.getRentalType());
        hotel.setUpdatedAt(LocalDate.now());
        hotel.setCity(city);
        hotel.setAddress(request.getAddressHotel());
        hotel.setHotline(request.getPhoneHotel());
        hotel.setEmail(request.getEmailHotel());
        hotel.setStar(request.getStar());
        hotel.setName(request.getNameHotel());
        hotel.setRentalType(rentalType);
        hotel.setDescription(request.getDescriptionHotel());
        hotel.setStatus(request.getStatus());
        String regex = "^0([0-9]{9})";
        if (request.getPhoneHotel().matches(regex)) {
            hotel.setHotline(request.getPhoneHotel());
        } else {
            throw new BadRequestException("Số điện thoại của bạn không hợp lệ ");
        }

        return hotelRepository.save(hotel);

    }

    // admin xóa homestay
    public void deleteHotel(Integer id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy homestay nào có id : " + id));
        List<Review> reviewList = reviewRepository.findReviewByHotel_Id(id);
        List<Room> listRoom = roomRepository.findRoomByHotel_Id(id);
        List<ImageHotel> imageHotelList = imageService.getAllImageByIdHotel(hotel.getId());
        for (Room room : listRoom){
            List<ImageRoom> imageRoomList = imageService.getAllImageRoomByIdRoom(room.getId());
            imageRoomRepository.deleteAll(imageRoomList);
        }
        List<Booking> bookingList = bookingRepository.findAllByHotel_Id(hotel.getId());
        User user = hotel.getUser();
        user.setUserRole(UserRole.ROLE_USER);
        userRepository.save(user);
        reviewRepository.deleteAll(reviewList);
        imageHotelRepository.deleteAll(imageHotelList);
        bookingRepository.deleteAll(bookingList);
        roomRepository.deleteAll(listRoom);
        hotelRepository.delete(hotel);
    }


    public Hotel getHotelByAccountCurrent() {
        User user = userRepository.findByEmail(httpSession.getAttribute("MY_SESSION").toString())
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user trên "));
        System.out.println("Id của khách snaj nè " +hotelRepository.findHotelByUser_Id(user.getId()).getId() );
        return hotelRepository.findHotelByUser_Id(user.getId());
    }


    // hotel-manager update khách sạn
    public Hotel updateHotel(Integer id, UpsertHotelRequest request) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy homestay nào có id : " + id));
        hotel.setDescription(request.getDescriptionHotel());
        List<AmenityHotel> list = new ArrayList<>();
        request.getAmenityHotelList().forEach(idAme -> {
            AmenityHotel amenityHotel = amenityHotelRepository.findById(idAme)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy amenity nào có id : " + idAme));
            list.add(amenityHotel);
        });
        hotel.setAmenityHotelList(list);
        return hotelRepository.save(hotel);
    }

    public List<Hotel> getAllHotelByCity(Integer id) {
        return hotelRepository.findHotelByCity_Id(id);
    }

    // tạo khách sạn dựa trên thông tin đã có
    @Transactional
    public Hotel createHotel(UpsertHotelRequest upsertHotelRequest, City city, User user, PolicyHotel policyHotel, List<AmenityHotel> amenityHotelList) {
        Hotel hotelNew = Hotel.builder()
                .name(upsertHotelRequest.getNameHotel())
                .email(upsertHotelRequest.getEmailHotel())
                .description(upsertHotelRequest.getDescriptionHotel())
                .address(upsertHotelRequest.getAddressHotel())
                .city(city)
                .policyHotel(policyHotel)
                .user(user)
                .star(upsertHotelRequest.getStar())
                .hotline(upsertHotelRequest.getPhoneHotel())
                .amenityHotelList(amenityHotelList)
                .createdAt(LocalDate.now())
                .rating(0.0f)
                .status(false)
                .rentalType(RentalType.valueOf(upsertHotelRequest.getRentalType()))
                .build();
        hotelRepository.save(hotelNew);
        return hotelNew;
    }

    public void updatePolicyHotel(UpsertPolicyRequest request) {
        Hotel hotel = getHotelByAccountCurrent();
        policyService.updatePolicyHotel(request, hotel);
    }

    public List<Room> getAllRoom() {
        Hotel hotel = getHotelByAccountCurrent();
        return roomService.getRoomByIdHotel(hotel.getId());

    }

    @Transactional
    public Hotel createHotelUser(
            UpsertHotelRequest upsertHotelRequest,
            UpsertRoomRequest upsertRoomRequest,
            UpsertPolicyRequest upsertPolicyRequest,
            MultipartFile fileHotel,
            MultipartFile fileRoom) {

        log.info("[START] Tạo mới homestay với số điện thoại: {}", upsertHotelRequest.getPhoneHotel());

        if (hotelRepository.findByHotline(upsertHotelRequest.getPhoneHotel()) != null) {
            log.warn("[DUPLICATE] Số điện thoại đã tồn tại: {}", upsertHotelRequest.getPhoneHotel());
            throw new RuntimeException("Số điện thoại đã tồn tại");
        }

        log.info("[1] Lấy danh sách tiện ích homestay theo ID: {}", upsertHotelRequest.getAmenityHotelList());
        List<AmenityHotel> amenityHotelList = amenityService.getAllAmenityHotelById(upsertHotelRequest.getAmenityHotelList());

        log.info("[2] Lấy thông tin thành phố với id: {}", upsertHotelRequest.getIdCity());
        City city = cityService.getCityById(upsertHotelRequest.getIdCity());

        log.info("[3] Tạo chính sách homestay mới");
        PolicyHotel policyHotel = policyService.createPolicyHotel(upsertPolicyRequest);

        log.info("[4] Lấy user hiện tại từ session");
        User user = userService.getUserCurrent();
        log.info("[4.1] User: {} - {}", user.getId(), user.getEmail());

        log.info("[5] Cập nhật quyền ROLE_HOTEL cho user và lưu lại");
        user.setUserRole(UserRole.ROLE_HOTEL);
        userRepository.save(user);

        log.info("[6] Tạo homestay mới");
        Hotel hotelNew = createHotel(upsertHotelRequest, city, user, policyHotel, amenityHotelList);

        log.info("[7] Tải ảnh homestay");
        ImageHotel imageHotel = imageService.uploadImageHotel(hotelNew.getId(), fileHotel);
        imageHotelRepository.save(imageHotel);
        hotelNew.setPoster(imageHotel.getUrl());

        log.info("[8] Tạo phòng mới cho homestay");
        Room room = roomService.createRoom(upsertRoomRequest);

        log.info("[9] Tải ảnh phòng");
        ImageRoom imageRoom = imageService.saveImageRoom(room, fileRoom);
        room.setThumbnailRoom(imageRoom.getUrl());
        roomRepository.save(room);

        log.info("[10] Gửi email xác nhận cho user: {}", user.getEmail());
        String link = "http://localhost:9000/hotel-manager/information";
        mailService.sendMail(user.getEmail(),
                "Đăng ký tài khoản đối tác",
                "Chào " + user.getName() + "! \n\n" +
                        "Chúc mừng bạn đã trở thành đối tác của EscapeNest.\n\n" +
                        "Truy cập đường link sau để chuyển đến trang quản lý nơi lưu trú của bạn:\n" +
                        link + "\n\nTrân trọng.");

        log.info("[DONE] Tạo homestay hoàn tất với ID: {}", hotelNew.getId());
        return hotelNew;
    }


    @Transactional
    public Hotel createHotelUserr(UpsertHotelRequest upsertHotelRequest, UpsertRoomRequest upsertRoomRequest, UpsertPolicyRequest upsertPolicyRequest, MultipartFile fileHotel, MultipartFile fileRoom) {
        if (hotelRepository.findByHotline(upsertHotelRequest.getPhoneHotel()) != null) {
            throw new RuntimeException("Số điện thoại đã tồn tại ");
        }
        // lây danh sách các tiện ích mà khách sạn chọn để lưu vào tiện ích của khách sạn đó
        List<AmenityHotel> amenityHotelList = amenityService.getAllAmenityHotelById(upsertHotelRequest.getAmenityHotelList());
        // Lấy thành phố  mà khách sạn đã chọn
        City city = cityService.getCityById(upsertHotelRequest.getIdCity());
        // tạo chính sách mới cho khách sạn
        PolicyHotel policyHotel = policyService.createPolicyHotel(upsertPolicyRequest);
        // cập nhật role mới cho user
        log.info("Break point");
        User user = userService.getUserCurrent();
        log.info("Pass point");
        user.setUserRole(UserRole.ROLE_HOTEL);
        userRepository.save(user);
        // tạo dữ liệu khách sạn
        Hotel hotelNew = createHotel(upsertHotelRequest, city, user, policyHotel, amenityHotelList);
        // tạo dối tượng image hotel
        ImageHotel imageHotel = imageService.uploadImageHotel(hotelNew.getId(), fileHotel);
        imageHotelRepository.save(imageHotel);
        // đặt poster cho khách sạn là ảnh đầu tiên
        hotelNew.setPoster(imageHotel.getUrl());
        // tạo dữ liệu cho phòng
        Room room = roomService.createRoom(upsertRoomRequest);
        ImageRoom imageRoom = imageService.saveImageRoom(room, fileRoom);
        room.setThumbnailRoom(imageRoom.getUrl());
        roomRepository.save(room);
        String link = "http://localhost:9000/hotel-manager/information" ;
        mailService.sendMail(user.getEmail(),
                "Đăng ký tài khoản đối tác",
                "Chào " +user.getName()+"! \n" +
                        "\n" +
                        "Chúc mừng bạn đã trở thành đối tác của EscapeNest.\n" +
                        "\n" +
                        "Truy cập đường link sau để chuyển đến trang quản lý nơi lưu trú của bạn :\n" +
                        "\n" +
                        link+"\n" +
                        "\n" +
                        "Trân trọng.\n" );
        return hotelNew;
    }

    public List<Hotel> getHotelNew() {
        LocalDate star = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        return hotelRepository.findAllByCreatedAtBetweenOrderByCreatedAtDesc(star, end);
    }

    public void updateHotelPoster(Integer id , String url) {

        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy homestay trên "));
        hotel.setPoster(url);
        hotelRepository.save(hotel);
    }
}
