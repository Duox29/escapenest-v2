package com.escapenest;


import com.escapenest.entity.*;
import com.escapenest.model.enums.*;
import com.escapenest.repository.*;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@SpringBootTest
class BookingAppApplicationTests {
    @Autowired
    private ImageHotelRepository imageRepository;
    @Autowired
    private ImageRoomRepository imageRoomRepository;
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private SupportRepository supportRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AmenityHotelRepository amenityHotelRepository;
    @Autowired
    private AmenityRoomRepository amenityRoomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void anem() {
//        Random random = new Random();
//        List<Room> roomList = roomRepository.findAll();
//        List<AmenityRoom> amenityRoomList = amenityRoomRepository.findAll();
//        for (Room room :roomList ){
//           List<AmenityRoom> small = new ArrayList<>();
//           for (int i = 0; i < random.nextInt(7,10); i++) {
//               small.add(amenityRoomList.get(random.nextInt(0,amenityRoomList.size())));
//
//           }
//            room.setAmenityRoomList(small);
//           roomRepository.save(room);
//       }


        List<Hotel> hotelList = hotelRepository.findAll();
        List<PolicyHotel> policyHotels = policyRepository.findAll();
        int i = 0;
        for (Hotel hotel : hotelList) {
            hotel.setPolicyHotel(policyHotels.get(i));
            i++;
            hotelRepository.save(hotel);
        }

//        List<AmenityHotel> amenityHotelList = amenityHotelRepository.findAll();
//
//       for (Hotel hotel : hotelList){
//           List<AmenityHotel> small = new ArrayList<>();
//           for (int i = 0; i < random.nextInt(5,8); i++) {
//               small.add(amenityHotelList.get(random.nextInt(0,amenityHotelList.size())));
//
//           }
//           hotel.setAmenityHotelList(small);
//           hotelRepository.save(hotel);
//       }
    }


    @Test
    void createData() {
        String[] hotelNames = {
                "Rex Hotel",
                "Caravelle Saigon",
                "Majestic Saigon",
                "Sheraton Saigon Hotel & Towers",
                "Continental Saigon",
                "InterContinental Saigon",
                "Park Hyatt Saigon",
                "New World Saigon",
                "Liberty Central Saigon Riverside",
                "Liberty Central Saigon Citypoint",
                "Silverland Yen Hotel",
                "The Myst Dong Khoi",
                "Hotel Nikko Saigon",
                "Pullman Saigon Centre",
                "Sofitel Saigon Plaza",
                "Fusion Suites Saigon",
                "Norfolk Mansion",
                "Renaissance Riverside Hotel Saigon",
                "Novotel Saigon Centre",
                "Hotel Majestic",
                "Saigon Prince Hotel",
                "Lotte Legend Hotel Saigon",
                "The Reverie Saigon",
                "Windsor Plaza Hotel",
                "Sofitel Legend Metropole Hanoi",
                "Hanoi Daewoo Hotel",
                "Hilton Hanoi Opera",
                "Pan Pacific Hanoi",
                "Melia Hanoi",
                "Movenpick Hotel Hanoi",
                "InterContinental Hanoi Westlake",
                "JW Marriott Hotel Hanoi",
                "Lotte Hotel Hanoi",
                "Apricot Hotel",
                "The Oriental Jade Hotel",
                "Peridot Grand Hotel & Spa",
                "La Siesta Premium Hang Be",
                "Hanoi La Siesta Hotel & Spa",
                "Hanoi Emerald Waters Hotel & Spa",
                "Hanoi Pearl Hotel",
                "Golden Sun Villa Hotel",
                "Hanoi E Central Hotel",
                "Soleil Boutique Hotel Hanoi",
                "Dal Vostro Hotel & Spa",
                "Hanoi Marvellous Hotel & Spa",
                "Silk Path Boutique Hanoi",
                "The Light Hotel",
                "The Noble Swan Hotel & Spa",
                "Anise Hotel",
                "Imperial Hotel Hue",
                "Indochine Palace",
                "Saigon Morin Hotel",
                "Hotel Saigon Morin",
                "Moonlight Hotel Hue",
                "Eldora Hotel",
                "Cherish Hotel",
                "Huong Giang Hotel Resort & Spa",
                "Park View Hotel",
                "Muong Thanh Holiday Hue Hotel",
                "Duy Tan Hotel",
                "Vinpearl Hotel Hue",
                "Asia Hotel Hue",
                "Gold Hotel",
                "Thanh Lich Royal Boutique Hotel",
                "Green Hotel Hue",
                "Alba Spa Hotel",
                "Rosaleen Boutique Hotel",
                "Valentine Hotel",
                "Villa Hue Hotel",
                "White Lotus Hotel",
                "Alba Hotel",
                "Mondial Hotel Hue",
                "Vina Hotel Hue",
                "Orchid Hotel",
                "Phu Thinh Boutique Resort & Spa",
                "Almanity Hoi An Wellness Resort",
                "Little Hoi An Central Boutique Hotel & Spa",
                "Hoi An Silk Marina Resort & Spa",
                "Lantana Riverside Hoi An Boutique Hotel & Spa",
                "Belle Maison Hadana Hoi An Resort & Spa",
                "Allegro Hoi An . A Little Luxury Hotel & Spa",
                "Lasenta Boutique Hotel Hoian",
                "Sunrise Premium Resort Hoi An",
                "Little Riverside Hoi An . A Luxury Hotel & Spa",
                "La Residencia . A Little Boutique Hotel & Spa",
                "TTC Hotel Premium Hoi An",
                "Hoi An Silk Village Resort & Spa",
                "Essence Hoi An Hotel & Spa",
                "La Siesta Hoi An Resort & Spa",
                "Royal Riverside Hoi An Hotel",
                "Lantana Boutique Hotel Hoi An",
                "Vinh Hung Riverside Resort & Spa",
                "Lantana Riverside Hoi An Boutique Hotel & Spa",
                "Mulberry Collection Silk Marina",
                "Emm Hotel Hoi An",
                "Aurora Riverside Hotel & Villas",
                "Muca Hoi An Boutique Resort & Spa",
                "Phu Thinh Boutique Resort & Spa",
                "Almanity Hoi An Wellness Resort",
                "Little Hoi An Central Boutique Hotel & Spa",
                "Hoi An Silk Marina Resort & Spa",
                "Lantana Riverside Hoi An Boutique Hotel & Spa",
                "Belle Maison Hadana Hoi An Resort & Spa",
                "Allegro Hoi An . A Little Luxury Hotel & Spa",
                "Lasenta Boutique Hotel Hoian",
                "Sunrise Premium Resort Hoi An",
                "Little Riverside Hoi An . A Luxury Hotel & Spa",
                "La Residencia . A Little Boutique Hotel & Spa",
                "TTC Hotel Premium Hoi An",
                "Hoi An Silk Village Resort & Spa",
                "Essence Hoi An Hotel & Spa",
                "La Siesta Hoi An Resort & Spa",
                "Royal Riverside Hoi An Hotel",
                "Lantana Boutique Hotel Hoi An",
                "Vinh Hung Riverside Resort & Spa",
                "Lantana Riverside Hoi An Boutique Hotel & Spa",
                "Mulberry Collection Silk Marina",
                "Emm Hotel Hoi An",
                "Aurora Riverside Hotel & Villas",
                "Muca Hoi An Boutique Resort & Spa",
                "Naman Retreat Da Nang",
                "Hyatt Regency Danang Resort & Spa",
                "InterContinental Danang Sun Peninsula Resort",
                "Furama Resort Danang",
                "Vinpearl Luxury Da Nang",
                "Pullman Danang Beach Resort",
                "Grand Mercure Danang",
                "Hilton Da Nang",
                "Novotel Danang Premier Han River",
                "Four Points by Sheraton Danang",
                "Altara Suites",
                "Monarque Hotel Danang",
                "TMS Hotel Da Nang Beach",
                "Hadana Boutique Hotel Danang",
                "Titan Hotel Da Nang",
                "Haka Hotel & Apartment",
                "Danang Golden Bay",
                "Sanouva Danang Hotel",
                "Vanda Hotel",
                "Brilliant Hotel",
                "Orange Hotel",
                "Gopatel Hotel & Spa",
                "Seahorse Hotel & Office by HAVI",
                "Pariat Hotel Danang",
                "Samdi Hotel",
                "Satya Danang Hotel",
                "Stay Hotel",
                "Lion Sea Hotel",
                "Sea Phoenix Hotel",
                "Belle Maison Parosand Danang",
                "Fivitel Boutique Da Nang",
                "Balcona Hotel Da Nang",
                "Avatar Danang Hotel",
                "Risemount Premier Resort Danang",
                "Belle Maison Parosand Danang",
                "Blossom City Hotel",
                "Luna Diamond Hotel",
                "Azumaya Hotel Da Nang",
                "Senriver Hotel",
                "Palazzo Hotel & Apartment",
                "Hoang Dai 2 Hotel Da Nang",
                "Da Nang - Mikazuki Japanese Resorts & Spa",
                "Hotel Royal Hoi An - MGallery",
                "Muong Thanh Luxury Nha Trang Hotel",
                "InterContinental Nha Trang",
                "Sheraton Nha Trang Hotel & Spa",
                "Sunrise Nha Trang Beach Hotel & Spa",
                "Mia Resort Nha Trang",
                "Amiana Resort and Villas Nha Trang",
                "Novotel Nha Trang",
                "Evason Ana Mandara Nha Trang",
                "Vinpearl Resort Nha Trang",
                "Duyen Ha Resort Cam Ranh",
                "Cam Ranh Riviera Beach Resort & Spa",
                "The Anam",
                "Movenpick Resort Cam Ranh",
                "Radisson Blu Resort Cam Ranh",
                "Fusion Resort Cam Ranh",
                "Golden Peak Resort & Spa",
                "Swandor Cam Ranh Resort",
                "Alma Resort Cam Ranh",
                "An Lam Retreats Ninh Van Bay",
                "Six Senses Ninh Van Bay",
                "Wild Beach Resort & Spa",
                "Whale Island Resort",
                "Diamond Bay Resort & Spa",
                "MerPerle Hon Tam Resort",
                "Vinpearl Luxury Nha Trang",
                "Sheraton Nha Trang Hotel & Spa",
                "Sunrise Nha Trang Beach Hotel & Spa",
                "Mia Resort Nha Trang",
                "Amiana Resort and Villas Nha Trang",
                "Novotel Nha Trang",
                "Evason Ana Mandara Nha Trang",
                "Vinpearl Resort Nha Trang",
                "Duyen Ha Resort Cam Ranh",
                "Cam Ranh Riviera Beach Resort & Spa",
                "The Anam",
                "Movenpick Resort Cam Ranh",
                "Radisson Blu Resort Cam Ranh",
                "Fusion Resort Cam Ranh",
                "Golden Peak Resort & Spa",
                "Swandor Cam Ranh Resort",
                "Alma Resort Cam Ranh",
                "An Lam Retreats Ninh Van Bay",
                "Six Senses Ninh Van Bay",
                "Wild Beach Resort & Spa",
                "Whale Island Resort",
                "Diamond Bay Resort & Spa",
                "MerPerle Hon Tam Resort"
        };
        String[] phoneNumbers = {
                "0351234567", "0351234568", "0351234569", "0351234570", "0351234571",
                "0351234572", "0351234573", "0351234574", "0351234575", "0351234576",
                "0351234577", "0351234578", "0351234579", "0351234580", "0351234581",
                "0351234582", "0351234583", "0351234584", "0351234585", "0351234586",
                "0351234587", "0351234588", "0351234589", "0351234590", "0351234591",
                "0351234592", "0351234593", "0351234594", "0351234595", "0351234596",
                "0351234597", "0351234598", "0351234599", "0351234600", "0351234601",
                "0351234602", "0351234603", "0351234604", "0351234605", "0351234606",
                "0351234607", "0351234608", "0351234609", "0351234610", "0351234611",
                "0351234612", "0351234613", "0351234614", "0351234615", "0351234616",
                "0351234617", "0351234618", "0351234619", "0351234620", "0351234621",
                "0351234622", "0351234623", "0351234624", "0351234625", "0351234626",
                "0351234627", "0351234628", "0351234629", "0351234630", "0351234631",
                "0351234632", "0351234633", "0351234634", "0351234635", "0351234636",
                "0351234637", "0351234638", "0351234639", "0351234640", "0351234641",
                "0351234642", "0351234643", "0351234644", "0351234645", "0351234646",
                "0351234647", "0351234648", "0351234649", "0351234650", "0351234651",
                "0351234652", "0351234653", "0351234654", "0351234655", "0351234656",
                "0351234657", "0351234658", "0351234659", "0351234660", "0351234661",
                "0351234662", "0351234663", "0351234664", "0351234665", "0351234666",
                "0351234667", "0351234668", "0351234669", "0351234670", "0351234671",
                "0351234672", "0351234673", "0351234674", "0351234675", "0351234676",
                "0351234677", "0351234678", "0351234679", "0351234680", "0351234681",
                "0351234682", "0351234683", "0351234684", "0351234685", "0351234686",
                "0351234687", "0351234688", "0351234689", "0351234690", "0351234691",
                "0351234692", "0351234693", "0351234694", "0351234695", "0351234696",
                "0351234697", "0351234698", "0351234699", "0341234567", "0341234568",
                "0341234569", "0341234570", "0341234571", "0341234572", "0341234573",
                "0341234574", "0341234575", "0341234576", "0341234577", "0341234578",
                "0341234579", "0341234580", "0341234581", "0341234582", "0341234583",
                "0341234584", "0341234585", "0341234586", "0341234587", "0341234588",
                "0341234589", "0341234590", "0341234591", "0341234592", "0341234593",
                "0341234594", "0341234595", "0341234596", "0341234597", "0341234598",
                "0341234599", "0341234600", "0341234601", "0341234602", "0341234603",
                "0341234604", "0341234605", "0341234606", "0341234607", "0341234608",
                "0341234609", "0341234610", "0341234611", "0341234612", "0341234613",
                "0341234614", "0341234615", "0341234616", "0341234617", "0341234618",
                "0341234619", "0341234620", "0341234621", "0341234622", "0341234623",
                "0341234624", "0341234625", "0341234626", "0341234627", "0341234628",
                "0341234629", "0341234630", "0341234631", "0341234632", "0341234633",
                "0341234634", "0341234635", "0341234636", "0341234637", "0341234638",
                "0341234639", "0341234640", "0341234641", "0341234642", "0341234643",
                "0341234644", "0341234645", "0341234646", "0341234647", "0341234648",
                "0341234649", "0341234650", "0341234651", "0341234652", "0341234653",
                "0341234654", "0341234655", "0341234656", "0341234657", "0341234658",
                "0341234659", "0341234660", "0341234661", "0341234662", "0341234663",
                "0341234664", "0341234665", "0341234666", "0341234667", "0341234668",
                "0341234669", "0341234670", "0341234671", "0341234672", "0341234673",
                "0341234674", "0341234675", "0341234676", "0341234677", "0341234678",
                "0341234679", "0341234680", "0341234681", "0341234682", "0341234683",
                "0341234684", "0341234685", "0341234686", "0341234687", "0341234688",
                "0341234689", "0341234690", "0341234691", "0341234692", "0341234693",
                "0341234694", "0341234695", "0341234696", "0341234697", "0341234698",
                "0341234699"
        };

        List<PolicyHotel> policyHotels = policyRepository.findAll();
        String[] addresses = {
                "15A Hoàng Hoa Thám, Ba Đình, Hà Nội",
                "20 Nguyễn Văn Ngọc, Ba Đình, Hà Nội",
                "35 Phan Đình Phùng, Ba Đình, Hà Nội",
                "8A Điện Biên Phủ, Ba Đình, Hà Nội",
                "10 Phạm Hùng, Cầu Giấy, Hà Nội",
                "25 Lạc Long Quân, Tây Hồ, Hà Nội",
                "42 Đội Cấn, Ba Đình, Hà Nội",
                "17 Huỳnh Thúc Kháng, Đống Đa, Hà Nội",
                "3A Văn Cao, Ba Đình, Hà Nội",
                "56 Trần Phú, Ba Đình, Hà Nội",
                "91 Nguyễn Chí Thanh, Đống Đa, Hà Nội",
                "18 Huế, Hai Bà Trưng, Hà Nội",
                "5A Nguyễn Tri Phương, Ba Đình, Hà Nội",
                "27B Nguyễn Huy Tưởng, Thanh Xuân, Hà Nội",
                "63 Đào Tấn, Ba Đình, Hà Nội",
                "12A Lý Nam Đế, Hoàn Kiếm, Hà Nội",
                "38 Quang Trung, Hoàn Kiếm, Hà Nội",
                "9B Phan Kế Bính, Ba Đình, Hà Nội",
                "22 Nguyễn Bỉnh Khiêm, Hai Bà Trưng, Hà Nội",
                "7C Ngô Quyền, Hoàn Kiếm, Hà Nội"
        };


        City city = cityRepository.findCityByName("Hồ Chí Minh");
        Random random = new Random();
        Faker faker = new Faker();

        List<User> list = userRepository.findAllByUserRole(UserRole.ROLE_HOTEL);

        for (int i = 0; i < 20; i++) {
            Hotel hotel = Hotel.builder()
                    .name(hotelNames[i + 120])
                    .email(faker.internet().emailAddress())
                    .description(faker.lorem().paragraph())
                    .address(addresses[i])
                    .city(city)
                    .poster("/web/assets/image/dep.jpg")
//                    .policyHotel(policyHotels.get(i))
                    .user(list.get(i + 100))
                    .star(faker.number().numberBetween(1, 5))
                    .hotline(phoneNumbers[i + 100])
                    .rating((float) faker.number().randomDouble(1, 6, 10))
                    .rentalType(RentalType.values()[random.nextInt(RentalType.values().length)])
                    .createdAt(LocalDate.now())
                    .updatedAt(LocalDate.now())
                    .status(true)
                    .build();
            hotelRepository.save(hotel);
        }


//        try {
//            Document doc = Jsoup.connect("https://www.expedia.com.vn/Hanoi-Khach-San-Movenpick-Hotel-Hanoi.h481549.Thong-tin-khach-san?chkin=2024-07-09&chkout=2024-07-11&x_pwa=1&rfrr=HSR&pwa_ts=1720526619095&referrerUrl=aHR0cHM6Ly93d3cuZXhwZWRpYS5jb20udm4vSG90ZWwtU2VhcmNo&useRewards=true&rm1=a2&regionId=6054458&destination=H%C3%A0%20N%E1%BB%99i%2C%20Vi%C3%AA%CC%A3t%20Nam&destType=MARKET&neighborhoodId=6054471&latLong=21.035911%2C105.839431&sort=RECOMMENDED&top_dp=3000000&top_cur=VND&userIntent=&selectedRoomType=320849729&selectedRatePlan=387336180&searchId=d8527df5-0a76-4c8e-b3e6-71d9835bdfb6&propertyUnitRoomInfoDialog=room-info-320849724-1").get();
//            Element nameHotel = doc.selectFirst("h4[class=uitk-heading-4]");//
//            Element address = doc.selectFirst("div[data-stid=content-hotel-address]");
//            Element description = doc.selectFirst("div[class=uitk-text-default-theme]");///
//            Element imgimg = doc.selectFirst("img[class=uitk-image-media]");
//            Elements star = doc.select("svg[class=uitk-rating-icon]");
//            Element elementsRating = doc.selectFirst("h1[class=uitk-badge-base-text]");
//            Hotel hotel = new Hotel();
//            hotel.setStar(star.size());
//            hotel.setRating(Float.valueOf(elementsRating.text()));
//            hotel.setName(nameHotel.text());
//            hotel.setDescription(description.text());
//            hotel.setAddress(address.text());
//            hotel.setHotline("034" + random.nextInt(1000000, 9999999));
//            hotel.setRentalType(RentalType.values()[random.nextInt(RentalType.values().length)]);
//            hotel.setCreatedAt(LocalDate.now());
//            hotel.setStatus(true);
//            hotel.setCity(city);
//            hotel.setPoster(imgimg.attr("src"));
//            hotelRepository.save(hotel);
//            Elements nameRoom = doc.select("h3[class=uitk-heading-6]");
//            Elements nameroomSmal = (Elements) nameRoom.subList(4, nameRoom.size());
//            for (Element nameroom : nameroomSmal) {
//                Element imgRo = doc.selectFirst("img[importance=low]");
//                Room room = new Room();
//                room.setName(nameroom.text());
//                room.setHotel(hotel);
//                room.setName(nameHotel.text());
//                room.setDescription("Phòng Deluxe được thiết kế tinh tế sang trọng nội thất đầy đủ tiện nghi hiện đại, cửa sổ kính rộng thoáng bao quát toàn cảnh thành phố mang đến cho bạn một không gian thanh bình và dễ chịu sẽ là sự lựa chọn cho những doanh nhân và khách du lịch.");
//                room.setCapacity(random.nextInt(1, 4));
//                room.setRoomType(RoomType.values()[random.nextInt(RoomType.values().length)]);
//                room.setCreatedAt(LocalDate.now());
//                room.setArea(random.nextInt(15, 35));
//                room.setQuantity(random.nextInt(1, 5));
//                room.setBedType(BedType.values()[random.nextInt(BedType.values().length)]);
//                room.setBedSize(BedSize.values()[random.nextInt(BedSize.values().length)]);
//                room.setStatus(true);
//                room.setPriceDefault(random.nextInt(300000, 1000000));
//                room.setThumbnailRoom(imgimg.attr("src"));
//                room.setStatus(true);
//                room.setCreatedAt(LocalDate.now());
//                roomRepository.save(room);
//            }
//        } catch (IOException e) {
//            System.out.println("Lỗi");
//        }
    }


    @Test
    void createAmenytiHotel() {
        String[] restaurantFeatures = {
                "Nhà hàng buffet", "Thực đơn đặc biệt", "Phục vụ phòng ăn uống", "Có phục vụ bữa sáng"
        };

        String[] internetFeatures = {
                "Wifi miễn phí"
        };

        String[] fitnessCenterFeatures = {
                "Phòng tập gym"
        };

        String[] swimmingPoolFeatures = {
                "Hồ bơi"
        };

        String[] spaFeatures = {
                "Spa và massage"
        };

        String[] playgroundFeatures = {
                "Sân chơi"
        };

        String[] otherFeatures = {
                "Bar và quầy rượu", "Dịch vụ phòng 24/7", "Tiệc cưới và hội nghị", "Đưa đón sân bay", "Tour du lịch và thuê xe"
        };
        Random random = new Random();
        for (int i = 0; i < restaurantFeatures.length; i++) {
            AmenityHotel amenityHotel = new AmenityHotel();
            amenityHotel.setName(restaurantFeatures[i]);
            amenityHotel.setAmenityHotelType(AmenityHotelType.RESTAURANT);
            amenityHotel.setCreatedAt(LocalDate.now());
            amenityHotelRepository.save(amenityHotel);
        }
        for (int i = 0; i < internetFeatures.length; i++) {
            AmenityHotel amenityHotel = new AmenityHotel();
            amenityHotel.setName(internetFeatures[i]);
            amenityHotel.setAmenityHotelType(AmenityHotelType.INTERNET);
            amenityHotel.setCreatedAt(LocalDate.now());
            amenityHotelRepository.save(amenityHotel);
        }
        for (int i = 0; i < fitnessCenterFeatures.length; i++) {
            AmenityHotel amenityHotel = new AmenityHotel();
            amenityHotel.setName(fitnessCenterFeatures[i]);
            amenityHotel.setAmenityHotelType(AmenityHotelType.INTERNET);
            amenityHotel.setCreatedAt(LocalDate.now());
            amenityHotelRepository.save(amenityHotel);
        }
        for (int i = 0; i < swimmingPoolFeatures.length; i++) {
            AmenityHotel amenityHotel = new AmenityHotel();
            amenityHotel.setName(swimmingPoolFeatures[i]);
            amenityHotel.setAmenityHotelType(AmenityHotelType.SWIMMING_POOL);
            amenityHotel.setCreatedAt(LocalDate.now());
            amenityHotelRepository.save(amenityHotel);
        }
        for (int i = 0; i < spaFeatures.length; i++) {
            AmenityHotel amenityHotel = new AmenityHotel();
            amenityHotel.setName(spaFeatures[i]);
            amenityHotel.setAmenityHotelType(AmenityHotelType.SPA);
            amenityHotel.setCreatedAt(LocalDate.now());
            amenityHotelRepository.save(amenityHotel);
        }
        for (int i = 0; i < playgroundFeatures.length; i++) {
            AmenityHotel amenityHotel = new AmenityHotel();
            amenityHotel.setName(playgroundFeatures[i]);
            amenityHotel.setAmenityHotelType(AmenityHotelType.PLAYGROUND);
            amenityHotel.setCreatedAt(LocalDate.now());
            amenityHotelRepository.save(amenityHotel);
        }
        for (int i = 0; i < otherFeatures.length; i++) {
            AmenityHotel amenityHotel = new AmenityHotel();
            amenityHotel.setName(otherFeatures[i]);
            amenityHotel.setAmenityHotelType(AmenityHotelType.OTHER);
            amenityHotel.setCreatedAt(LocalDate.now());
            amenityHotelRepository.save(amenityHotel);
        }

    }

    @Test
    void createAmenytiRoom() {
        // Khai báo và khởi tạo mảng các tiện ích theo từng loại
        String[] bathroomAmenities = {
                "Áo choàng tắm", "Đồ dùng nhà tắm miễn phí", "Máy sấy tóc",
                "Phòng tắm riêng", "Vòi sen phun mưa (cố định)",
                "Bồn tắm và buồng tắm vòi sen riêng", "Dép đi trong nhà", "Khăn tắm"
        };

        String[] bedroomAmenities = {
                "Máy điều hòa nhiệt độ", "Không có bộ trải giường", "Không có nôi (cũi)/giường cho trẻ sơ sinh"
        };

        String[] entertainmentAmenities = {
                "Truyền hình cáp", "TV màn hình phẳng"
        };

        String[] diningAmenities = {
                "Dụng cụ pha cà phê/trà", "Nước đóng chai miễn phí", "Minibar",
                "Dịch vụ phòng (giới hạn thời gian)"
        };

        String[] internetAmenities = {
                "Wifi miễn phí", "Truy cập Internet có dây miễn phí"
        };

        String[] otherAmenities = {
                "Dịch vụ dọn phòng hàng ngày", "Bàn", "Bàn ủi/dụng cụ ủi quần áo",
                "Khu vực làm việc phù hợp cho laptop", "Không cửa sổ", "Điện thoại",
                "Két an toàn", "Phòng cách âm"
        };
        Random random = new Random();
        for (int i = 0; i < bathroomAmenities.length; i++) {
            AmenityRoom amenityRoom = new AmenityRoom();
            amenityRoom.setName(bathroomAmenities[i]);
            amenityRoom.setAmenityRoomType(AmenityRoomType.BATHROOM);
            amenityRoom.setCreatedAt(LocalDate.now());
            amenityRoomRepository.save(amenityRoom);
        }
        for (int i = 0; i < bedroomAmenities.length; i++) {
            AmenityRoom amenityRoom = new AmenityRoom();
            amenityRoom.setName(bedroomAmenities[i]);
            amenityRoom.setAmenityRoomType(AmenityRoomType.BEDROOM);
            amenityRoom.setCreatedAt(LocalDate.now());
            amenityRoomRepository.save(amenityRoom);
        }

        for (int i = 0; i < entertainmentAmenities.length; i++) {
            AmenityRoom amenityRoom = new AmenityRoom();
            amenityRoom.setName(entertainmentAmenities[i]);
            amenityRoom.setAmenityRoomType(AmenityRoomType.ENTERTAINMENT);
            amenityRoom.setCreatedAt(LocalDate.now());
            amenityRoomRepository.save(amenityRoom);
        }

        for (int i = 0; i < diningAmenities.length; i++) {
            AmenityRoom amenityRoom = new AmenityRoom();
            amenityRoom.setName(diningAmenities[i]);
            amenityRoom.setAmenityRoomType(AmenityRoomType.KITCHEN);
            amenityRoom.setCreatedAt(LocalDate.now());
            amenityRoomRepository.save(amenityRoom);
        }

        for (int i = 0; i < internetAmenities.length; i++) {
            AmenityRoom amenityRoom = new AmenityRoom();
            amenityRoom.setName(internetAmenities[i]);
            amenityRoom.setAmenityRoomType(AmenityRoomType.INTERNET);
            amenityRoom.setCreatedAt(LocalDate.now());
            amenityRoomRepository.save(amenityRoom);
        }

        for (int i = 0; i < otherAmenities.length; i++) {
            AmenityRoom amenityRoom = new AmenityRoom();
            amenityRoom.setName(otherAmenities[i]);
            amenityRoom.setAmenityRoomType(AmenityRoomType.OTHERS);
            amenityRoom.setCreatedAt(LocalDate.now());
            amenityRoomRepository.save(amenityRoom);
        }

    }

    @Test
    void creatUser() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        User user = User.builder()
                .name("Trương Văn Tuyên")
                .email("tuyen@gmail.com")
                .password(passwordEncoder.encode("tuyen123"))
                .avatar("/assets/image/avata-default.jpg")
                .userRole(UserRole.ROLE_ADMIN)
                .birthDay(LocalDate.parse("23/10/2001", dateTimeFormatter))
                .phoneNumber("0352075192")
                .address("Xuân khê , Lý Nhân  , Hà Nam")
                .gender(Gender.MALE)
                .enable(true)
                .createdAt(LocalDate.now())
                .build();
        userRepository.save(user);
    }

    // đã taạo
    @Test
    void createDataUser() {
        Faker faker = new Faker();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            User user = User.builder()
                    .name(faker.lorem().word())
                    .email(faker.internet().emailAddress())
                    .password(passwordEncoder.encode("tuyen123"))
                    .userRole(UserRole.ROLE_HOTEL)
                    .birthDay(faker.date().birthdayLocalDate())
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .gender(Gender.values()[random.nextInt(Gender.values().length)])
                    .enable(true)
                    .address(faker.address().fullAddress())
                    .avatar("/web/assets/image/avata-default.jpg")
                    .createdAt(LocalDate.now())
                    .updateAt(LocalDate.now())
                    .build();

            userRepository.save(user);
        }

    }


    @Test
    void contextLoads() {
        List<City> cityList = cityRepository.findAll();
        String[] provinces = {
                "Hà Giang", "Cao Bằng", "Bắc Kạn", "Tuyên Quang", "Lào Cai",
                "Điện Biên", "Lai Châu", "Sơn La", "Yên Bái", "Hoà Bình",
                "Thái Nguyên", "Lạng Sơn", "Quảng Ninh", "Bắc Giang", "Phú Thọ",
                "Vĩnh Phúc", "Bắc Ninh", "Hà Nội", "Hải Dương", "Hải Phòng",
                "Hưng Yên", "Thái Bình", "Hà Nam", "Nam Định", "Ninh Bình",
                "Thanh Hóa", "Nghệ An", "Hà Tĩnh", "Quảng Bình", "Quảng Trị",
                "Thừa Thiên Huế", "Đà Nẵng", "Quảng Nam", "Quảng Ngãi", "Bình Định",
                "Phú Yên", "Khánh Hòa", "Ninh Thuận", "Bình Thuận", "Kon Tum",
                "Gia Lai", "Đắk Lắk", "Đắk Nông", "Lâm Đồng", "Bình Phước",
                "Tây Ninh", "Bình Dương", "Đồng Nai", "Bà Rịa - Vũng Tàu", "Hồ Chí Minh",
                "Long An", "Tiền Giang", "Bến Tre", "Trà Vinh", "Vĩnh Long",
                "Đồng Tháp", "An Giang", "Kiên Giang", "Cần Thơ", "Hậu Giang",
                "Sóc Trăng", "Bạc Liêu", "Cà Mau"
        };
    }


    // đã tạo
    @Test
    void createDataCity() {
        Faker faker = new Faker();
        String[] provinces = {
                "Hà Giang", "Cao Bằng", "Bắc Kạn", "Tuyên Quang", "Lào Cai",
                "Điện Biên", "Lai Châu", "Sơn La", "Yên Bái", "Hoà Bình",
                "Thái Nguyên", "Lạng Sơn", "Quảng Ninh", "Bắc Giang", "Phú Thọ",
                "Vĩnh Phúc", "Bắc Ninh", "Hà Nội", "Hải Dương", "Hải Phòng",
                "Hưng Yên", "Thái Bình", "Hà Nam", "Nam Định", "Ninh Bình",
                "Thanh Hóa", "Nghệ An", "Hà Tĩnh", "Quảng Bình", "Quảng Trị",
                "Thừa Thiên Huế", "Đà Nẵng", "Quảng Nam", "Quảng Ngãi", "Bình Định",
                "Phú Yên", "Khánh Hòa", "Ninh Thuận", "Bình Thuận", "Kon Tum",
                "Gia Lai", "Đắk Lắk", "Đắk Nông", "Lâm Đồng", "Bình Phước",
                "Tây Ninh", "Bình Dương", "Đồng Nai", "Bà Rịa - Vũng Tàu", "Hồ Chí Minh",
                "Long An", "Tiền Giang", "Bến Tre", "Trà Vinh", "Vĩnh Long",
                "Đồng Tháp", "An Giang", "Kiên Giang", "Cần Thơ", "Hậu Giang",
                "Sóc Trăng", "Bạc Liêu", "Cà Mau"
        };
        for (int i = 0; i < 63; i++) {
            City city = City.builder()
                    .name(provinces[i])
                    .country("Việt Nam")
                    .imageCity("/upload_image/image_city/1717777240306")
                    .createdAt(LocalDate.now())
                    .updatedAt(LocalDate.now())
                    .build();
            cityRepository.save(city);
        }
    }


    // đã tạo
    @Test
    void createDataPolicy() {

        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < 200; i++) {
            String age = String.valueOf(random.nextInt(18, 80));
            PolicyHotel policyHotel = PolicyHotel.builder()
                    .startCheckIn("05:00")
                    .startCheckOut("14:00")
                    .endCheckIn("12:00")
                    .endCheckOut("20:00")
                    .service(faker.lorem().sentence())
                    .cancelPolicy(faker.lorem().sentence())
                    .animal(faker.lorem().word())
                    .ageLimit(age)
                    .note(faker.lorem().paragraph())
                    .createdAt(LocalDate.now())
                    .updatedAt(LocalDate.now())
                    .build();
            policyRepository.save(policyHotel);
        }


    }


    // đã tạo
//    @Test
//    void createDataHotel() {
//        Faker faker = new Faker();
//        Random random = new Random();
//
//        City city = cityRepository.findCityByName("Đà Nẵng");
//        List<City> list = cityRepository.findAll();
//        List<PolicyHotel> policyHotels = policyRepository.findAll();
//        List<User> userList = userRepository.findAllByUserRole(UserRole.ROLE_HOTEL);
//
//
//        for (int i = 0; i < 100; i++) {
//            List<AmenityHotel> amenityHotels1 = new ArrayList<>();
//            Hotel hotel = Hotel.builder()
//                    .name(faker.company().name())
//                    .email(faker.internet().emailAddress())
//                    .description(faker.lorem().paragraph())
//                    .address(faker.address().fullAddress())
//                    .city(list.get(random.nextInt(0,list.size())))
//                    .poster("/web/assets/image/dep.jpg")
////                    .city(city)
////                    .policyHotel(policyHotels.get(i))
////                    .user(userList.get(random.nextInt(userList.size())))
//                    .star(faker.number().numberBetween(1, 5))
//                    .hotline(faker.phoneNumber().phoneNumber())
//                    .rating((float) faker.number().randomDouble(1, 6, 10))
//                    .rentalType(RentalType.values()[random.nextInt(RentalType.values().length)])
//                    .createdAt(LocalDate.now())
//                    .updatedAt(LocalDate.now())
//                    .status(true)
//                    .build();
//
//            hotelRepository.save(hotel);
//        }
//    }
    // đã tạo
    @Test
    void createDataAmenityHotel() {

        Faker faker = new Faker();
        Random random = new Random();
        List<Hotel> hotelList = hotelRepository.findAll();
        List<Room> roomList = roomRepository.findAll();
        int count = 0;

//        for (int i = 0; i < 100; i++) {
//            List<Hotel> hotelList1 = new ArrayList<>();
//            for (int j = 0; j < 10; j++) {
//                hotelList1.add(hotelList.get(random.nextInt(hotelList.size())));
//            }
//
//            AmenityHotel amenityHotel = new AmenityHotel();
//            amenityHotel.setName(faker.company().name());
//            amenityHotel.setCreatedAt(LocalDate.now());
//            amenityHotel.setUpdateAt(LocalDate.now());
//            amenityHotelRepository.save(amenityHotel);
//        }
//

        List<AmenityHotel> amenityHotelList = amenityHotelRepository.findAll();
        for (int i = 0; i < amenityHotelList.size(); i++) {
            amenityHotelList.get(i).setAmenityHotelType(AmenityHotelType.values()[random.nextInt(AmenityHotelType.values().length)]);

            amenityHotelRepository.save(amenityHotelList.get(i));
        }
//        List<AmenityRoom> amenityRoomList  = amenityRoomRepository.findAll();
//        for (int i = 0; i < amenityRoomList.size(); i++) {
//            List<AmenityRoom> amenityRoomList1 = new ArrayList<>();
//            for (int j = 0; j < random.nextInt(5,9); j++) {
//                amenityRoomList1.add(amenityRoomList.get(random.nextInt(amenityHotelList.size())));
//            }
//            roomList.get(i).setAmenityRoomList(amenityRoomList1);
//            roomRepository.save(roomList.get(i));
//        }
    }

    @Test
    void createDataAmenityRoom() {

        Faker faker = new Faker();
        Random random = new Random();

        List<Room> roomList = roomRepository.findAll();

        for (int i = 0; i < 100; i++) {
            AmenityRoom amenityRoom = new AmenityRoom();
            amenityRoom.setName(faker.company().name());
            amenityRoom.setCreatedAt(LocalDate.now());
            amenityRoom.setUpdateAt(LocalDate.now());
            amenityRoom.setAmenityRoomType(AmenityRoomType.values()[random.nextInt(AmenityRoomType.values().length)]);
            amenityRoomRepository.save(amenityRoom);
        }

//        List<AmenityRoom> amenityRoomList = amenityRoomRepository.findAll();
//        List<Hotel> hotelList = hotelRepository.findAll();
//        Random random = new Random();
//       for (AmenityRoom amenityRoom : amenityRoomList){
//           amenityRoom.setHotel(hotelList.get(random.nextInt(hotelList.size())));
//           amenityRoomRepository.save(amenityRoom);
//       }
    }

    // đã tạo
    @Test
    void createDataRoom() {
//        List<Hotel> hotelList = hotelRepository.findHotelByCity_Id(18);
//        Faker faker = new Faker();
//        Random random = new Random();
//        List<AmenityRoom> amenityRoomList = amenityRoomRepository.findAll();
//        System.out.println(amenityRoomList);
//        for (int i = 0; i < 800; i++) {
//            Hotel hotel = hotelList.get(random.nextInt(hotelList.size()));
//
//            List<AmenityRoom> amenityHotels1 = new ArrayList<>();
//            for (int j = 0; j < random.nextInt(5, 10) + 1; j++) {
//                int number = random.nextInt(0, amenityRoomList.size());
//                AmenityRoom amenityRoom = amenityRoomList.get(number);
//                if (!amenityHotels1.contains(amenityRoom)) {
//                    amenityHotels1.add(amenityRoom);
//                }
//            }
//            Room room = Room.builder()
//                    .name(faker.lorem().word())
//                    .description(faker.lorem().sentence())
//                    .capacity(faker.number().numberBetween(1, 10)) // Giả sử dung lượng từ 1 đến 10
//                    .area(faker.number().numberBetween(20, 50)) // Giả sử diện tích từ 20 đến 200
//                    .status(faker.bool().bool())
//                    .quantity(random.nextInt(1,7))
////                    .amenityRoomList(amenityHotels1)
//                    .roomType(RoomType.values()[random.nextInt(RoomType.values().length)])
//                    .bedSize(BedSize.values()[random.nextInt(BedSize.values().length)])
//                    .bedType(BedType.values()[random.nextInt(BedType.values().length)])
//                    .hotel(hotel)
//                    .thumbnailRoom("https://indec.vn/wp-content/uploads/2022/08/image4-8.jpg")
//                    .priceDefault(random.nextInt(300000,0))
//                    .createdAt(LocalDate.now())
//                    .updatedAt(LocalDate.now())
//                    .build();
//
//            roomRepository.save(room);
//        }
        Random ne = new Random();
        List<Room> roomList = roomRepository.findAll();
        for (Room ro : roomList) {
            ro.setPriceDefault(ne.nextInt(300000, 4000000));
            ro.setThumbnailRoom("https://indec.vn/wp-content/uploads/2022/08/image4-8.jpg");
            roomRepository.save(ro);
        }


    }

    @Test
    void createReview() {
        String[] coment = {"Dịch vụ tuyệt vời. 10 điểm không có nhưng", "Homestay sạch sẽ, thoáng mát.", "Rất là sạch sẽ luôn, view đẹp nữa",
                "Phòng sạch sẽ lắm luôn, view cực xịn xò lúc tụi mình check in còn không tin với giá tiền đó mình được ở căn hộ sang xịn như vậy", "Chỗ nghỉ view đỉnh, thoáng mát tiện nghi. Chị chủ siuuuu nhiệt tình, hỗ trợ checkin sớm"};
        List<Hotel>hotelList = hotelRepository.findHotelByCity_Id(18);
        Random random = new Random();
        for (Hotel hotel : hotelList){
            List<Booking> list = bookingRepository.findByHotel_IdAndStatusBooking(hotel.getId(),StatusBooking.COMPLETE);
            for (int i = 0; i < list.size(); i++) {
                hotel.setRating(0.0f);
                int count = 0 ;
                int sum = 0 ;
                Review review = new Review();
                review.setCreateAt(LocalDate.now());
                review.setHotel(hotel);
                review.setComment(coment[random.nextInt(0,coment.length)]);
                review.setRating(random.nextInt(6,9));
                review.setUser(list.get(i).getUser());
                review.setBooking(list.get(i));
                sum+=review.getRating();
                count++;
                reviewRepository.save(review);
                hotel.setRating((float) (sum/count));
                hotelRepository.save(hotel);
            }
        }

    }
    @Test
    void nameUsser() {
        String[] hoTen = {
                "Trần Văn Bảo", "Lê Minh Bình", "Phạm Quang Châu", "Hoàng Văn Công",
                "Vũ Đức Cường", "Đỗ Thành Dũng", "Ngô Quốc Đức", "Bùi Minh Duy", "Đặng Hữu Đạt",
                "Nguyễn Văn Hưng", "Trần Văn Khánh", "Lê Minh Khoa", "Phạm Văn Long", "Hoàng Văn Mạnh",
                "Vũ Văn Nam", "Đỗ Quốc Phong", "Ngô Văn Quân", "Bùi Văn Sơn", "Đặng Văn Thắng",
                "Nguyễn Văn Tài", "Trần Văn Tùng", "Lê Văn Toàn", "Phạm Văn Trung", "Hoàng Văn Tuấn",
                "Vũ Văn Vinh", "Đỗ Văn Việt", "Ngô Văn Vũ", "Bùi Văn Hiếu", "Đặng Văn Huy",
                "Nguyễn Thị Ánh", "Trần Thị Bích", "Lê Thị Cẩm", "Phạm Thị Dung", "Hoàng Thị Hạnh",
                "Vũ Thị Hoa", "Đỗ Thị Huyền", "Ngô Thị Lan", "Bùi Thị Mai", "Đặng Thị Ngọc",
                "Nguyễn Thị Phương", "Trần Thị Thanh", "Lê Thị Thu", "Phạm Thị Thúy", "Hoàng Thị Trang",
                "Vũ Thị Xuân", "Đỗ Thị Yến", "Ngô Thị Anh", "Bùi Thị Bảo", "Đặng Thị Châu",
                "Nguyễn Thị Duyên", "Trần Thị Giang", "Lê Thị Hà", "Phạm Thị Hoài", "Hoàng Thị Hồng",
                "Vũ Thị Hương", "Đỗ Thị Kim", "Ngô Thị Linh", "Bùi Thị Loan", "Đặng Thị Mai",
                "Nguyễn Thị Nga", "Trần Thị Ngân", "Lê Thị Ngọc", "Phạm Thị Nhung", "Hoàng Thị Oanh",
                "Vũ Thị Phúc", "Đỗ Thị Quỳnh", "Ngô Thị Tâm", "Bùi Thị Thảo", "Đặng Thị Thúy",
                "Nguyễn Thị Thùy", "Trần Thị Thuý", "Lê Thị Tiên", "Phạm Thị Tuyết", "Hoàng Thị Vân",
                "Vũ Thị Vui", "Đỗ Thị Xuân", "Ngô Thị Yến", "Bùi Thị Ánh", "Đặng Thị Bích",
                "Nguyễn Thị Cẩm", "Trần Thị Dung", "Lê Thị Hạnh", "Phạm Thị Hoa", "Hoàng Thị Huyền",
                "Vũ Thị Lan", "Đỗ Thị Mai", "Ngô Thị Ngọc", "Bùi Thị Phương", "Đặng Thị Thanh",
                "Nguyễn Thị Thu", "Trần Thị Thúy", "Lê Thị Trang", "Phạm Thị Xuân", "Hoàng Thị Yến"
        };
        String[] emails = {
                "tranvanbao@gmail.com", "leminhbinh@gmail.com", "phamquangchau@gmail.com", "hoangvancong@gmail.com",
                "vuduccuong@gmail.com", "dothanh dung@gmail.com", "ngoquocduc@gmail.com", "buiminhhuy@gmail.com", "danghuudat@gmail.com",
                "nguyenvanhung@gmail.com", "tranvankhanh@gmail.com", "leminhkhoa@gmail.com", "phamvanlong@gmail.com", "hoangvanmanh@gmail.com",
                "vuvannam@gmail.com", "doquocphong@gmail.com", "ngovanquan@gmail.com", "buivanson@gmail.com", "dangvanthang@gmail.com",
                "nguyenvantai@gmail.com", "tranvantung@gmail.com", "levantoan@gmail.com", "phamvantrung@gmail.com", "hoangvantuan@gmail.com",
                "vuvanvinh@gmail.com", "dovanviet@gmail.com", "ngovanvu@gmail.com", "buivanhieu@gmail.com", "dangvanhuy@gmail.com",
                "nguyenthianh@gmail.com", "tranthibich@gmail.com", "lethicam@gmail.com", "phamthidung@gmail.com", "hoangthihanh@gmail.com",
                "vuthihoa@gmail.com", "dothihuyen@gmail.com", "ngothilan@gmail.com", "buithimai@gmail.com", "dangthingoc@gmail.com",
                "nguyenthiphuong@gmail.com", "tranthithanh@gmail.com", "lethithu@gmail.com", "phamthithuy@gmail.com", "hoangthitrang@gmail.com",
                "vuthixuan@gmail.com", "dothiyen@gmail.com", "ngothianh@gmail.com", "buithibao@gmail.com", "dangthichau@gmail.com",
                "nguyenthiduyen@gmail.com", "trangiang@gmail.com", "lethiha@gmail.com", "phamthihoai@gmail.com", "hoangthihong@gmail.com",
                "vuthihuong@gmail.com", "dothikim@gmail.com", "ngothilinh@gmail.com", "buithiloan@gmail.com", "dangthimai@gmail.com",
                "nguyenthinga@gmail.com", "tranthingan@gmail.com", "lethinhoc@gmail.com", "phamthinung@gmail.com", "hoangthioanh@gmail.com",
                "vuthiphuc@gmail.com", "dothiquynh@gmail.com", "ngothitam@gmail.com", "buithithao@gmail.com", "dangthithuy@gmail.com",
                "nguyenthithuy@gmail.com", "tranthithuy@gmail.com", "lethitrang@gmail.com", "phamthixuan@gmail.com", "hoangthiyen@gmail.com"
        };
        String[] soDienThoai = {
                "0351000001", "0351000002", "0351000003", "0351000004", "0351000005",
                "0351000006", "0351000007", "0351000008", "0351000009", "0351000010",
                "0351000011", "0351000012", "0351000013", "0351000014", "0351000015",
                "0351000016", "0351000017", "0351000018", "0351000019", "0351000020",
                "0351000021", "0351000022", "0351000023", "0351000024", "0351000025",
                "0351000026", "0351000027", "0351000028", "0351000029", "0351000030",
                "0351000031", "0351000032", "0351000033", "0351000034", "0351000035",
                "0351000036", "0351000037", "0351000038", "0351000039", "0351000040",
                "0351000041", "0351000042", "0351000043", "0351000044", "0351000045",
                "0351000046", "0351000047", "0351000048", "0351000049", "0351000050",
                "0341000001", "0341000002", "0341000003", "0341000004", "0341000005",
                "0341000006", "0341000007", "0341000008", "0341000009", "0341000010",
                "0341000011", "0341000012", "0341000013", "0341000014", "0341000015",
                "0341000016", "0341000017", "0341000018", "0341000019", "0341000020",
                "0341000021", "0341000022", "0341000023", "0341000024", "0341000025",
                "0341000026", "0341000027", "0341000028", "0341000029", "0341000030",
                "0341000031", "0341000032", "0341000033", "0341000034", "0341000035",
                "0341000036", "0341000037", "0341000038", "0341000039", "0341000040",
                "0341000041", "0341000042", "0341000043", "0341000044", "0341000045",
                "0931000001", "0931000002", "0931000003", "0931000004", "0931000005",
                "0931000006", "0931000007", "0931000008", "0931000009", "0931000010",
                "0931000011", "0931000012", "0931000013", "0931000014", "0931000015",
        };
        String[] diaChi = {
                "Số 1 Tràng Tiền, Hoàn Kiếm, Hà Nội",
                "Số 10 Lý Thái Tổ, Hoàn Kiếm, Hà Nội",
                "Số 20 Nguyễn Chí Thanh, Đống Đa, Hà Nội",
                "Số 15 Bà Triệu, Hoàn Kiếm, Hà Nội",
                "Số 30 Phạm Ngọc Thạch, Đống Đa, Hà Nội",
                "Số 50 Tây Sơn, Đống Đa, Hà Nội",
                "Số 8 Phạm Hùng, Nam Từ Liêm, Hà Nội",
                "Số 35 Láng Hạ, Ba Đình, Hà Nội",
                "Số 2 Kim Mã, Ba Đình, Hà Nội",
                "Số 5 Lê Duẩn, Ba Đình, Hà Nội",
                "Số 40 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội",
                "Số 12 Hai Bà Trưng, Hoàn Kiếm, Hà Nội",
                "Số 60 Giảng Võ, Ba Đình, Hà Nội",
                "Số 28 Trần Phú, Ba Đình, Hà Nội",
                "Số 18 Hàng Bài, Hoàn Kiếm, Hà Nội",
                "Số 75 Nguyễn Thái Học, Ba Đình, Hà Nội",
                "Số 22 Cầu Giấy, Cầu Giấy, Hà Nội",
                "Số 55 Trần Duy Hưng, Cầu Giấy, Hà Nội",
                "Số 7 Hoàng Quốc Việt, Cầu Giấy, Hà Nội",
                "Số 90 Nguyễn Văn Cừ, Long Biên, Hà Nội",
                "Số 45 Nguyễn Văn Linh, Long Biên, Hà Nội",
                "Số 33 Tô Ngọc Vân, Tây Hồ, Hà Nội",
                "Số 18 Lạc Long Quân, Tây Hồ, Hà Nội",
                "Số 27 Âu Cơ, Tây Hồ, Hà Nội",
                "Số 50 Yên Phụ, Tây Hồ, Hà Nội",
                "Số 100 Võ Chí Công, Tây Hồ, Hà Nội",
                "Số 45 Lê Hồng Phong, Ba Đình, Hà Nội",
                "Số 77 Nguyễn Trãi, Thanh Xuân, Hà Nội",
                "Số 33 Khuất Duy Tiến, Thanh Xuân, Hà Nội",
                "Số 10 Nguyễn Xiển, Thanh Xuân, Hà Nội",
                "Số 5 Lê Văn Lương, Thanh Xuân, Hà Nội",
                "Số 9 Hoàng Đạo Thúy, Thanh Xuân, Hà Nội",
                "Số 50 Bạch Mai, Hai Bà Trưng, Hà Nội",
                "Số 60 Minh Khai, Hai Bà Trưng, Hà Nội",
                "Số 25 Đại Cồ Việt, Hai Bà Trưng, Hà Nội",
                "Số 12 Phố Huế, Hai Bà Trưng, Hà Nội",
                "Số 7 Hàng Chuối, Hai Bà Trưng, Hà Nội",
                "Số 90 Lê Thanh Nghị, Hai Bà Trưng, Hà Nội",
                "Số 55 Nguyễn Khoái, Hai Bà Trưng, Hà Nội",
                "Số 20 Hồng Hà, Hoàn Kiếm, Hà Nội",
                "Số 5 Tràng Thi, Hoàn Kiếm, Hà Nội",
                "Số 18 Ngô Quyền, Hoàn Kiếm, Hà Nội",
                "Số 22 Hai Bà Trưng, Hoàn Kiếm, Hà Nội",
                "Số 28 Trần Hưng Đạo, Hoàn Kiếm, Hà Nội",
                "Số 12 Trần Nhật Duật, Hoàn Kiếm, Hà Nội",
                "Số 15 Lý Thường Kiệt, Hoàn Kiếm, Hà Nội",
                "Số 20 Hàng Bài, Hoàn Kiếm, Hà Nội",
                "Số 25 Hàng Gai, Hoàn Kiếm, Hà Nội",
                "Số 30 Hàng Trống, Hoàn Kiếm, Hà Nội",
                "Số 35 Hàng Đào, Hoàn Kiếm, Hà Nội",
                "Số 40 Hàng Bạc, Hoàn Kiếm, Hà Nội",
                "Số 45 Hàng Buồm, Hoàn Kiếm, Hà Nội",
                "Số 50 Hàng Đậu, Hoàn Kiếm, Hà Nội",
                "Số 55 Hàng Giấy, Hoàn Kiếm, Hà Nội",
                "Số 60 Hàng Gà, Hoàn Kiếm, Hà Nội",
                "Số 65 Hàng Mã, Hoàn Kiếm, Hà Nội",
                "Số 70 Hàng Nón, Hoàn Kiếm, Hà Nội",
                "Số 75 Hàng Cót, Hoàn Kiếm, Hà Nội",
                "Số 80 Hàng Bồ, Hoàn Kiếm, Hà Nội",
                "Số 85 Hàng Bông, Hoàn Kiếm, Hà Nội",
                "Số 90 Hàng Gai, Hoàn Kiếm, Hà Nội",
                "Số 95 Hàng Mành, Hoàn Kiếm, Hà Nội",
                "Số 100 Hàng Bè, Hoàn Kiếm, Hà Nội",
                "Số 105 Hàng Gà, Hoàn Kiếm, Hà Nội",
                "Số 110 Hàng Đường, Hoàn Kiếm, Hà Nội",
                "Số 115 Hàng Điếu, Hoàn Kiếm, Hà Nội",
                "Số 120 Hàng Mành, Hoàn Kiếm, Hà Nội",
                "Số 125 Hàng Thùng, Hoàn Kiếm, Hà Nội",
                "Số 130 Hàng Bông, Hoàn Kiếm, Hà Nội",
                "Số 135 Hàng Quạt, Hoàn Kiếm, Hà Nội",
                "Số 140 Hàng Tre, Hoàn Kiếm, Hà Nội",
                "Số 145 Hàng Đào, Hoàn Kiếm, Hà Nội",
                "Số 150 Hàng Ngang, Hoàn Kiếm, Hà Nội",
                "Số 155 Hàng Đào, Hoàn Kiếm, Hà Nội",
                "Số 160 Hàng Bạc, Hoàn Kiếm, Hà Nội",
                "Số 165 Hàng Buồm, Hoàn Kiếm, Hà Nội",
                "Số 170 Hàng Đậu, Hoàn Kiếm, Hà Nội",
                "Số 175 Hàng Giấy, Hoàn Kiếm, Hà Nội",
                "Số 180 Hàng Gà, Hoàn Kiếm, Hà Nội",
                "Số 185 Hàng Mã, Hoàn Kiếm, Hà Nội",
                "Số 190 Hàng Nón, Hoàn Kiếm, Hà Nội",
                "Số 195 Hàng Cót, Hoàn Kiếm, Hà Nội",
                "Số 200 Hàng Bồ, Hoàn Kiếm, Hà Nội",
                "Số 205 Hàng Bông, Hoàn Kiếm, Hà Nội",
                "Số 210 Hàng Gai, Hoàn Kiếm, Hà Nội",
                "Số 215 Hàng Mành, Hoàn Kiếm, Hà Nội",
                "Số 220 Hàng Bè, Hoàn Kiếm, Hà Nội",
                "Số 225 Hàng Gà, Hoàn Kiếm, Hà Nội",
                "Số 230 Hàng Đường, Hoàn Kiếm, Hà Nội",
                "Số 235 Hàng Điếu, Hoàn Kiếm, Hà Nội",
                "Số 240 Hàng Mành, Hoàn Kiếm, Hà Nội",
                "Số 245 Hàng Thùng, Hoàn Kiếm, Hà Nội",
                "Số 250 Hàng Bông, Hoàn Kiếm, Hà Nội"
        };



        List<User> userList = userRepository.findAll();
        Random random = new Random();
        int i = 0;
        for (User user : userList){
//            if (i>=100){
//                i =0;
//            }

           if (i==93){
               i =0;
           }
           user.setName(hoTen[i]);
//           user.setEmail(emails[i]);
//           user.setPhoneNumber(soDienThoai[i]);
//            user.setAddress(diaChi[i]);
           userRepository.save(user);
           i++;
        }

    }
    @Test
    void data (){
        String[] tenPhong = {
                "Phòng Đơn Nghỉ Dưỡng",
                "Phòng Đôi Thanh Lịch",
                "Phòng Tiêu Chuẩn Biển Xanh",
                "Phòng Suite Hạng Sang",
                "Phòng Cao Cấp View Sông",
                "Studio Nghỉ Dưỡng",
                "Phòng Đơn Biển",
                "Phòng Đôi Lãng Mạn",
                "Phòng Tiêu Chuẩn Thành Phố",
                "Phòng Suite Thư Giãn",
                "Phòng Cao Cấp Núi",
                "Studio Sáng Tạo",
                "Phòng Đơn Ven Sông",
                "Phòng Đôi Phố Cổ",
                "Phòng Tiêu Chuẩn Phong Cách",
                "Phòng Suite Đẳng Cấp",
                "Phòng Cao Cấp Biển Đảo",
                "Studio Tối Giản",
                "Phòng Đơn Sơn Cùng",
                "Phòng Đôi Hòa Nhã",
                "Phòng Tiêu Chuẩn Thanh Bình",
                "Phòng Suite Quý Phái",
                "Phòng Cao Cấp Hướng Sông",
                "Studio Hiện Đại",
                "Phòng Đơn Bình Minh",
                "Phòng Đôi Hoàng Gia",
                "Phòng Tiêu Chuẩn Lãng Mạn",
                "Phòng Suite Sang Trọng",
                "Phòng Cao Cấp Mặt Biển",
                "Studio Tinh Tế",
                "Phòng Đơn Tĩnh Lặng",
                "Phòng Đôi Thanh Xuân",
                "Phòng Tiêu Chuẩn Yên Bình",
                "Phòng Suite Nhẹ Nhàng",
                "Phòng Cao Cấp Sông Nước",
                "Studio Phóng Khoáng",
                "Phòng Đơn Đồng Quê",
                "Phòng Đôi Thời Thượng",
                "Phòng Tiêu Chuẩn Ngọt Ngào",
                "Phòng Suite Lý Tưởng",
                "Phòng Cao Cấp Trải Nghiệm",
                "Studio Cổ Điển",
                "Phòng Đơn Thành Đô",
                "Phòng Đôi Vương Giả",
                "Phòng Tiêu Chuẩn Phồn Hoa",
                "Phòng Suite Hòa Mình",
                "Phòng Cao Cấp Nghỉ Dưỡng",
                "Studio Năng Động"
        };

        String[] moTaPhong = {
                "Phòng nghỉ sang trọng với view biển tuyệt đẹp, đầy đủ tiện nghi và không gian rộng rãi.",
                "Biệt thự riêng tư giữa khu vườn xanh mát, cung cấp sự riêng tư và yên tĩnh.",
                "Phòng suite lý tưởng cho những ai tìm kiếm không gian sống rộng rãi và tiện nghi.",
                "Biệt thự ven sông, mang đến cho khách hàng trải nghiệm gần gũi với thiên nhiên.",
                "Phòng gia đình tiện nghi, phù hợp cho cả gia đình nghỉ ngơi và thư giãn.",
                "Suite view biển độc đáo, tận hưởng cảm giác thư thái dưới ánh nắng vàng của bãi biển.",
                "Biệt thự có hồ bơi riêng, dành cho những ai muốn tận hưởng không gian riêng biệt và thư giãn.",
                "Biệt thự bãi biển, mang đến cho du khách trải nghiệm thư thái và tận hưởng bãi biển ngay trước mắt.",
                "Suite Junior, lựa chọn phù hợp cho các cặp đôi mong muốn không gian ấm cúng và sang trọng.",
                "Phòng nghỉ với view núi, khách sạn phù hợp cho những ai yêu thích không gian yên tĩnh và thiên nhiên.",
                "Cottage ấm cúng, là nơi lý tưởng để thư giãn và tận hưởng không gian riêng tư.",
                "Penthouse sang trọng, đầy đủ tiện nghi và view đẹp, phục vụ cho các du khách cao cấp.",
                "Nơi trốn tìm nhiệt đới, với thiết kế nội thất mang đậm phong cách và sắc màu nhiệt đới.",
                "Suite view thành phố, lựa chọn hoàn hảo cho các du khách công tác muốn tiện lợi di chuyển và thư giãn.",
                "Phòng nghỉ tại spa, phục vụ cho những ai muốn tận hưởng dịch vụ chăm sóc sức khỏe và làm đẹp.",
                "Biệt thự view sông, cho phép du khách tận hưởng cảm giác thư thái bên bờ sông êm đềm.",
                "Kỳ nghỉ lãng mạn, với thiết kế nội thất sang trọng và không gian riêng biệt dành cho các cặp đôi.",
                "Cabin bên biển, là nơi lý tưởng để tận hưởng không gian gần gũi với biển cả và thiên nhiên.",
                "Loft tinh tế, với thiết kế hiện đại và không gian mở, phù hợp cho du khách yêu thích phong cách hiện đại.",
                "Studio dễ thương, là nơi lý tưởng cho những ai muốn có không gian sống nhỏ gọn và thoải mái.",
                "Phòng hạng cao cấp, với đầy đủ tiện nghi và dịch vụ sang trọng, phục vụ cho những du khách khó tính.",
                "Nơi nghỉ tại bên hồ, với view hồ trong xanh và không gian yên bình, lý tưởng cho những người yêu thiên nhiên.",
                "Chalet riêng tư, là nơi lý tưởng để gia đình hoặc nhóm bạn tận hưởng không gian sống riêng tư và thoải mái.",
                "Căn hộ cao cấp tầng trên, với view tuyệt đẹp và không gian sống tiện nghi, phù hợp cho những ai muốn không gian rộng rãi.",
                "Phòng view rừng, mang đến cho khách hàng cảm giác gần gũi với thiên nhiên và không khí trong lành của rừng.",
                "Nhà khách cổ điển, với kiến trúc và nội thất mang phong cách cổ điển, phục vụ cho những du khách yêu thích sự lịch lãm và sang trọng.",
                "Suite thanh bình, với không gian yên tĩnh và thiết kế nội thất đẳng cấp, phù hợp cho các cặp đôi hoặc du khách yêu thích sự riêng tư.",
                "Oasis đô thị, là nơi lý tưởng để khách hàng thư giãn và tận hưởng cuộc sống trong thành phố tấp nập.",
                "Loft view biển, với view biển tuyệt đẹp và không gian sống hiện đại, phục vụ cho những du khách yêu thích sự tinh tế và thoải mái.",
                "Biệt thự view núi, là nơi lý tưởng để tận hưởng không gian sống gần gũi với núi non và thiên nhiên.",
                "Phòng garden zen, với không gian thiền và sự thư giãn, phù hợp cho những du khách muốn tìm kiếm sự thanh tịnh.",
                "Villa thanh bình, với view thiên nhiên và không gian sống tiện nghi, là nơi lý tưởng để khách hàng nghỉ ngơi và thư giãn.",
                "Biệt thự thuộc kiến trúc thuộc địa, với kiến trúc mang đậm bản sắc vùng miền, phục vụ cho những du khách yêu thích sự độc đáo và riêng biệt.",
                "Nhà bungalow bên bờ biển, là nơi lý tưởng để tận hưởng không gian gần gũi với biển cả và thiên nhiên.",
                "Cottage cổ điển, với thiết kế nội thất mang phong cách cổ điển và không gian sống thoải mái, phục vụ cho những du khách yêu thích sự lãng mạn và sang trọng.",
                "Suite view cảng biển, với view cảng biển sôi động và không gian sống hiện đại, phục vụ cho những du khách yêu thích sự năng động và sôi động.",
                "Biệt thự view hồ, với view hồ trong xanh và không gian sống tiện nghi, là nơi lý tưởng để nghỉ ngơi và thư giãn.",
                "Phòng view rừng nghỉ dưỡng, với không gian yên bình và thiết kế nội thất tiện nghi, phục vụ cho những du khách yêu thích sự gần gũi với thiên nhiên.",
                "Ngôi nhà thư giãn tại biển, với không gian sống tinh tế"};

                List<Room> list = roomRepository.findAll();
        Random random = new Random();

        for (Room room : list){
            room.setName(tenPhong[random.nextInt(0,tenPhong.length)]);
            room.setDescription(moTaPhong[random.nextInt(0,moTaPhong.length)]);
            roomRepository.save(room);
        }

    }
    @Test
    void tshhs(){
        Random random = new Random();

        Faker faker = new Faker();
        String[] soDienThoai = {
                "0351000001", "0351000002", "0351000003", "0351000004", "0351000005",
                "0351000006", "0351000007", "0351000008", "0351000009", "0351000010",
                "0351000011", "0351000012", "0351000013", "0351000014", "0351000015",
                "0351000016", "0351000017", "0351000018", "0351000019", "0351000020",
                "0351000021", "0351000022", "0351000023", "0351000024", "0351000025",
                "0351000026", "0351000027", "0351000028", "0351000029", "0351000030",
                "0351000031", "0351000032", "0351000033", "0351000034", "0351000035",
                "0351000036", "0351000037", "0351000038", "0351000039", "0351000040",
                "0351000041", "0351000042", "0351000043", "0351000044", "0351000045",
                "0351000046", "0351000047", "0351000048", "0351000049", "0351000050",
                "0341000001", "0341000002", "0341000003", "0341000004", "0341000005",
                "0341000006", "0341000007", "0341000008", "0341000009", "0341000010",
                "0341000011", "0341000012", "0341000013", "0341000014", "0341000015",
                "0341000016", "0341000017", "0341000018", "0341000019", "0341000020",
                "0341000021", "0341000022", "0341000023", "0341000024", "0341000025",
                "0341000026", "0341000027", "0341000028", "0341000029", "0341000030",
                "0341000031", "0341000032", "0341000033", "0341000034", "0341000035",
                "0341000036", "0341000037", "0341000038", "0341000039", "0341000040",
                "0341000041", "0341000042", "0341000043", "0341000044", "0341000045",
                "0931000001", "0931000002", "0931000003", "0931000004", "0931000005",
                "0931000006", "0931000007", "0931000008", "0931000009", "0931000010",
                "0931000011", "0931000012", "0931000013", "0931000014", "0931000015",
        };
        String[] hoTen = {
                "Trần Văn Bảo", "Lê Minh Bình", "Phạm Quang Châu", "Hoàng Văn Công",
                "Vũ Đức Cường", "Đỗ Thành Dũng", "Ngô Quốc Đức", "Bùi Minh Duy", "Đặng Hữu Đạt",
                "Nguyễn Văn Hưng", "Trần Văn Khánh", "Lê Minh Khoa", "Phạm Văn Long", "Hoàng Văn Mạnh",
                "Vũ Văn Nam", "Đỗ Quốc Phong", "Ngô Văn Quân", "Bùi Văn Sơn", "Đặng Văn Thắng",
                "Nguyễn Văn Tài", "Trần Văn Tùng", "Lê Văn Toàn", "Phạm Văn Trung", "Hoàng Văn Tuấn",
                "Vũ Văn Vinh", "Đỗ Văn Việt", "Ngô Văn Vũ", "Bùi Văn Hiếu", "Đặng Văn Huy",
                "Nguyễn Thị Ánh", "Trần Thị Bích", "Lê Thị Cẩm", "Phạm Thị Dung", "Hoàng Thị Hạnh",
                "Vũ Thị Hoa", "Đỗ Thị Huyền", "Ngô Thị Lan", "Bùi Thị Mai", "Đặng Thị Ngọc",
                "Nguyễn Thị Phương", "Trần Thị Thanh", "Lê Thị Thu", "Phạm Thị Thúy", "Hoàng Thị Trang",
                "Vũ Thị Xuân", "Đỗ Thị Yến", "Ngô Thị Anh", "Bùi Thị Bảo", "Đặng Thị Châu",
                "Nguyễn Thị Duyên", "Trần Thị Giang", "Lê Thị Hà", "Phạm Thị Hoài", "Hoàng Thị Hồng",
                "Vũ Thị Hương", "Đỗ Thị Kim", "Ngô Thị Linh", "Bùi Thị Loan", "Đặng Thị Mai",
                "Nguyễn Thị Nga", "Trần Thị Ngân", "Lê Thị Ngọc", "Phạm Thị Nhung", "Hoàng Thị Oanh",
                "Vũ Thị Phúc", "Đỗ Thị Quỳnh", "Ngô Thị Tâm", "Bùi Thị Thảo", "Đặng Thị Thúy",
                "Nguyễn Thị Thùy", "Trần Thị Thuý", "Lê Thị Tiên", "Phạm Thị Tuyết", "Hoàng Thị Vân",
                "Vũ Thị Vui", "Đỗ Thị Xuân", "Ngô Thị Yến", "Bùi Thị Ánh", "Đặng Thị Bích",
                "Nguyễn Thị Cẩm", "Trần Thị Dung", "Lê Thị Hạnh", "Phạm Thị Hoa", "Hoàng Thị Huyền",
                "Vũ Thị Lan", "Đỗ Thị Mai", "Ngô Thị Ngọc", "Bùi Thị Phương", "Đặng Thị Thanh",
                "Nguyễn Thị Thu", "Trần Thị Thúy", "Lê Thị Trang", "Phạm Thị Xuân", "Hoàng Thị Yến"
        };
        String[] emails = {
                "tranvanbao@gmail.com", "leminhbinh@gmail.com", "phamquangchau@gmail.com", "hoangvancong@gmail.com",
                "vuduccuong@gmail.com", "dothanh dung@gmail.com", "ngoquocduc@gmail.com", "buiminhhuy@gmail.com", "danghuudat@gmail.com",
                "nguyenvanhung@gmail.com", "tranvankhanh@gmail.com", "leminhkhoa@gmail.com", "phamvanlong@gmail.com", "hoangvanmanh@gmail.com",
                "vuvannam@gmail.com", "doquocphong@gmail.com", "ngovanquan@gmail.com", "buivanson@gmail.com", "dangvanthang@gmail.com",
                "nguyenvantai@gmail.com", "tranvantung@gmail.com", "levantoan@gmail.com", "phamvantrung@gmail.com", "hoangvantuan@gmail.com",
                "vuvanvinh@gmail.com", "dovanviet@gmail.com", "ngovanvu@gmail.com", "buivanhieu@gmail.com", "dangvanhuy@gmail.com",
                "nguyenthianh@gmail.com", "tranthibich@gmail.com", "lethicam@gmail.com", "phamthidung@gmail.com", "hoangthihanh@gmail.com",
                "vuthihoa@gmail.com", "dothihuyen@gmail.com", "ngothilan@gmail.com", "buithimai@gmail.com", "dangthingoc@gmail.com",
                "nguyenthiphuong@gmail.com", "tranthithanh@gmail.com", "lethithu@gmail.com", "phamthithuy@gmail.com", "hoangthitrang@gmail.com",
                "vuthixuan@gmail.com", "dothiyen@gmail.com", "ngothianh@gmail.com", "buithibao@gmail.com", "dangthichau@gmail.com",
                "nguyenthiduyen@gmail.com", "trangiang@gmail.com", "lethiha@gmail.com", "phamthihoai@gmail.com", "hoangthihong@gmail.com",
                "vuthihuong@gmail.com", "dothikim@gmail.com", "ngothilinh@gmail.com", "buithiloan@gmail.com", "dangthimai@gmail.com",
                "nguyenthinga@gmail.com", "tranthingan@gmail.com", "lethinhoc@gmail.com", "phamthinung@gmail.com", "hoangthioanh@gmail.com",
                "vuthiphuc@gmail.com", "dothiquynh@gmail.com", "ngothitam@gmail.com", "buithithao@gmail.com", "dangthithuy@gmail.com",
                "nguyenthithuy@gmail.com", "tranthithuy@gmail.com", "lethitrang@gmail.com", "phamthixuan@gmail.com", "hoangthiyen@gmail.com"
        };



//        List<PolicyHotel> policyHotels = policyRepository.findAll();
//
//        for (PolicyHotel policyHotel : policyHotels){
//            policyHotel.setAnimal("Không được mang theo thú cưng lẫn vật nuôi hỗ trợ người khuyết tật");
//            policyHotel.setNote("Chỗ nghỉ này không nhận tổ chức tiệc chia tay cuộc đời độc thân hay các bữa tiệc tương tự như vậy.\n" +
//                    "\n" +
//                    "Thời gian yên lặng bắt đầu từ 22:00:00 đến 08:00:00.");
//            policyHotel.setAgeLimit("Trẻ em được chào đón\n" +
//                    "1 trẻ em, từ 5 tuổi trở xuống, có thể lưu trú miễn phí nếu sử dụng giường có sẵn tại phòng của cha mẹ hoặc người giám hộ");
//            policyHotel.setCancelPolicy("Các chính sách hủy và thanh toán trước sẽ khác nhau tùy vào từng loại chỗ nghỉ. Vui lòng nhập ngày lưu trú và xem điều kiện áp dụng cho lựa chọn chỗ nghỉ của bạn.");
//            policyHotel.setService("Phí dịch vụ xe đưa đón sân bay: 450000 VND mỗi xe (số khách tối đa: 4)\n" +
//                    "Phí đưa đón sân bay / 1 trẻ: 299000 VND (tối đa 5 tuổi)\n" +
//                    "Phí bãi xe tự phục vụ: 10 VND mỗi ngày\n" +
//                    "Phí giường gấp: 699000.0 VND mỗi ngày");
//            policyRepository.save(policyHotel);
//        }

        List<Hotel> hotelList = hotelRepository.findHotelByCity_Id(18);
//
        List<Review> reviewList = reviewRepository .findAll();
        for (Hotel hotel : hotelList){
//            List<ImageHotel> imageHotelList = imageRepository.findAllByHotel_Id(hotel.getId());
//            if (imageHotelList.size()==0 ){
//                continue;
//            }
//            List<Booking> list = bookingRepository.findByHotel_IdAndStatusBooking(Review.getHotel().getId(),StatusBooking.COMPLETE);



//            if (list.isEmpty()){
//                reviewRepository.delete(Review);
//            }
//            else {
//                Review.setBooking(list.get(1));
//                reviewRepository.save(Review);
//            }
//            hotel.setPoster(imageHotelList.get(random.nextInt(0,imageHotelList.size())).getUrl());
//            Hotel hotel = hotelRepository.findById(review.getHotel().getId()).orElseThrow(()-> new RuntimeException("Khoon timf thaats"));
//            List<Room> roomList = roomRepository.findRoomByHotel_Id(hotel.getId());
//            User user = userRepository.findById(review.getUser().getId()).orElseThrow(()-> new RuntimeException("sdsd"));

//            Boolean iss = random.nextBoolean();
//            if (iss){
//                booking.setStatusBooking(StatusBooking.COMPLETE);
//            }
            List<User> userList = userRepository.findAllByUserRole(UserRole.ROLE_USER);
            List<Room> roomList = roomRepository.findRoomByHotel_Id(hotel.getId());
            for (Room room : roomList){
//                List<ImageRoom> imageRoomList = imageRoomRepository.findAllByRoom_Id(room.getId());
//                if (imageRoomList.size()==0){
//                    continue;
//                }
//                room.setThumbnailRoom(imageRoomList.get(random.nextInt(0,imageRoomList.size())).getUrl());
//                roomRepository.save(room);
                for (int i = 0; i < random.nextInt(1,3); i++) {
                    LocalDate checkIn = LocalDate.now().minusDays(random.nextInt(0,80));
                    Booking booking = Booking.builder()
                            .checkIn(checkIn)
                            .checkOut(checkIn.plusDays(random.nextInt(2,5)))
                            .createAt(checkIn.minusDays(random.nextInt(5,10)))
                            .emailCustomer(emails[random.nextInt(0, emails.length)])
                            .guests(random.nextInt(1,4))
                            .user(userList.get(random.nextInt(0, userList.size())))
                            .nameCustomer(hoTen[random.nextInt(0, hoTen.length)])
                            .phoneCustomer(soDienThoai[random.nextInt(0, soDienThoai.length)])
                            .paymentMethod(PaymentMethod.PAY_AT_ACCOMMODATION)
                            .hotel(hotel)
                            .room(room)
                            .numberRoom(random.nextInt(1,3))
                            .build();
                    booking.setStatusBooking(StatusBooking.COMPLETE);
                    booking.setPrice(random.nextInt(400000,10000000));
                    bookingRepository.save(booking);
                }

            }
        }
    }
    @Test
    void delere (){
        Random random = new Random();

        List<Booking> bookingList = bookingRepository.findByStatusBooking(StatusBooking.CONFIRMED);
        for (int i = 0; i < bookingList.size(); i++) {
//            LocalDate localDate = LocalDate.now().plusDays(random.nextInt(3,10));
//            bookingList.get(i).setCheckIn(localDate);
//            bookingList.get(i).setCheckOut(localDate.plusDays(random.nextInt(1,10)));
            bookingList.get(i).setIsReviewed(false);
            bookingRepository.save(bookingList.get(i));
        }

//        User user = userRepository.findById(221).orElseThrow(() -> new RuntimeException("Không thấy"));
//        Hotel hotel = hotelRepository.findHotelByUser_Id(user.getId());
//
//        // Xóa tất cả các review liên quan đến hotel
//        List<Review> reviewList = reviewRepository.findReviewByHotel_Id(hotel.getId());
//        reviewRepository.deleteAll(reviewList);
//
//        // Xóa tất cả các booking liên quan đến hotel
//        List<Booking> bookingList = bookingRepository.findAllByHotel_Id(hotel.getId());
//        bookingRepository.deleteAll(bookingList);
//
//        // Tìm và xóa tất cả các image liên quan đến các room
//        List<Room> roomList = roomRepository.findRoomByHotel_Id(hotel.getId());
//        for (Room room : roomList) {
//            List<ImageRoom> imageRoomList = imageRoomRepository.findAllByRoom_Id(room.getId());
//            imageRoomRepository.deleteAll(imageRoomList);
//        }
//
//        // Xóa tất cả các room liên quan đến hotel
//        roomRepository.deleteAll(roomList);
//
//        // Xóa hotel
//        hotelRepository.delete(hotel);

        // Xóa user
//        userRepository.delete(user);



    }
    @Test
    void test(){
        List<City> list = cityRepository.findAll();

        for (City city :list){
            city.setImageCity(null);
            cityRepository.save(city);
        }
    }


    }

//    void imageRoomvsHotel(){
//        try {
//            Random random = new Random();
//            Document doc = Jsoup.connect("https://www.booking.com/hotel/vn/the-song-vt-khang-aparment.vi.html?label=vi-vn-booking-desktop-QdMBcjDXmpsKa1W2aLERdwS652804042052%3Apl%3Ata%3Ap1%3Ap2%3Aac%3Aap%3Aneg%3Afi%3Atikwd-33467740%3Alp9047174%3Ali%3Adec%3Adm&gclid=Cj0KCQjwiMmwBhDmARIsABeQ7xS0f6bm-xD5dCuTjstOn8V3TmEZphHTMZgiJAc3XdPPPf5ll-ck6WwaAqfREALw_wcB&aid=2336990&ucfs=1&arphpl=1&activeTab=main").get();
//            Elements elementsRating = doc.getElementsByClass("bh-photo-modal-grid-image");
//            List<String> imageHotelList = new ArrayList<>();
//            for (Element element : elementsRating){
//                imageHotelList.add(element.attr("src"));
//            }
//            List<Hotel> hotelList = hotelRepository.findAll();
//            for (Hotel hotel : hotelList){
//                for (int i = 0; i < random.nextInt(0,7) ; i++) {
//                    String idImage = String.valueOf(System.currentTimeMillis());
//                  ImageHotel imageHotel = new ImageHotel();
//
//                  imageHotel.setHotel(hotel);
//                  imageHotel.setUrl(imageHotelList.get(random.nextInt(0,imageHotelList.size())));
//                  imageHotel.setUrl(imageHotelList.get(random.nextInt(0,imageHotelList.size())));
//                  imageHotel.setUrl(imageHotelList.get(random.nextInt(0,imageHotelList.size())));
//                  imageHotel.setUrl(imageHotelList.get(random.nextInt(0,imageHotelList.size())));
//                    imageHotel.setId(idImage);
//                    // set tên của file được upload
//                    imageHotel.setName(multipartFile.getOriginalFilename());
//                    imageHotel.setType(multipartFile.getContentType());
//                    imageHotel.setSize(multipartFile.getSize() / 1048576.0);
//                    imageHotel.setUrl("/" + uploadDir + "/image_hotel/" + idImage);
//                    imageHotel.setHotel(hotel);
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Lỗi");
//        }
//
//
//    }
//
//}

//
// đã taaoj
//    @Test
//    void createDataSupport() {
//        Faker faker = new Faker();
//
//
//        Slugify slugify = Slugify.builder().build();
//
//
//        Random random = new Random();
//        for (int i = 0; i < 30; i++) {
//            String title = faker.lorem().word();
//
//            Support support = Support.builder()
//                    .title(title) // Tiêu đề ngẫu nhiên
//                    .description(faker.lorem().paragraph()) // Mô tả ngẫu nhiên
//                    .content(faker.lorem().paragraph(30)) // Nội dung ngẫu nhiên, 3 đoạn
//                    .slug(slugify.slugify(title)) // Slug ngẫu nhiên
//                    .status(faker.bool().bool()) // Trạng thái ngẫu nhiên (true/false)
//                    .createdAt(LocalDate.now())
//                    .updatedAt(LocalDate.now())
//                    .publishedAt(LocalDate.now())
//                    .supportType(SupportType.values()[random.nextInt(SupportType.values().length)]) // Loại hỗ trợ ngẫu nhiên từ enum SupportType
//                    .build();
//
//
//            supportRepository.save(support);
//        }
//    }





