package com.escapenest.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import com.escapenest.entity.Hotel;
import com.escapenest.entity.User;
import com.escapenest.exception.BadRequestException;
import com.escapenest.exception.ResourceNotFoundException;
import com.escapenest.model.request.AdminUpdateUserRequest;
import com.escapenest.model.request.CreateUserRequest;
import com.escapenest.model.enums.UserRole;
import com.escapenest.repository.HotelRepository;
import com.escapenest.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession session;
    private final HotelRepository hotelRepository;


    public User adminResetPasswordUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user"));

        user.setPassword(passwordEncoder.encode("123"));

        return userRepository.save(user);
    }
    public User adminUpdateUser(Integer id, AdminUpdateUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user có id = " + id));

        existingUser.setName(request.getName());
        existingUser.setPhoneNumber(request.getPhone());
        existingUser.setUserRole(UserRole.valueOf(request.getRole()));
        return userRepository.save(existingUser);
    }

    public User createUser(CreateUserRequest request) {

        // find user by email -> throw exception if exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email đã tồn tại");

        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhone())
                .password(passwordEncoder.encode("123"))
                .avatar("/web/assets/image/avata-default.jpg")
                .userRole(UserRole.valueOf(request.getRole()))
                .enable(false)
                .createdAt(LocalDate.now())
                .build();
        return userRepository.save(user);
    }

    public List<User> getUserNew() {
        LocalDate star = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(end  + "ngày cuối tháng ");
        System.out.println(star  + "ngày dầu  tháng ");
        return  userRepository.findUserByCreatedAtBetweenAndUserRoleOrderByCreatedAtDesc(star,end,UserRole.ROLE_USER);
    }

    public int totalUser() {
        return   userRepository.findAll().size();
    }


    // lấy user đang đăng nhập hiện tại
    public User getUserCurrent (){
        return userRepository.findByEmail(session.getAttribute("MY_SESSION").toString()).orElseThrow(()->new UsernameNotFoundException("Không tìm thấy user hiện tại "));
    }

    // lưu khách sạn vào danh sách yêu thích
    public void saveHotelFavourite(Integer id) {
        // tìm user đang đăng nhập
        User user = userRepository.findByEmail((String) session.getAttribute("MY_SESSION")).orElseThrow(()-> new UsernameNotFoundException("User Not Found "));
        // tìm khách sạn được chọn
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy homestay trên"));
        user.getHotelList().add(hotel);
        userRepository.save(user);
    }
    // xóa khách sạn ra khỏi danh sách yêu thích trang danh sách khách snaj yêu thích
    public Page<Hotel> deleteHotelFavourite(Integer id, Integer pageNumber) {
        if (pageNumber < 1){
            throw  new RuntimeException("Số trang không hợp lệ ");
        }
        // tạo đối twuongj pageable
        Pageable pageable = PageRequest.of(pageNumber-1,8);
        User user = deleteHotelFavourite(id);
        userRepository.save(user);
        List<Hotel> favouriteHotels = user.getHotelList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), favouriteHotels.size());

        // trả về một page
        return PageableExecutionUtils.getPage(favouriteHotels.subList(start,end)
                ,pageable,
                favouriteHotels::size);
    }


    // xóa khách sạn yêu thích taại trang danh sách khách sạn
    public User deleteHotelFavourite(Integer idHotel) {
        // tìm kiếm user đăng nhập
        User user = userRepository.findByEmail((String) session.getAttribute("MY_SESSION")).orElseThrow(()-> new UsernameNotFoundException("User Not Found "));
        // tìm kiếm khách sạn bị xóa khỏi danh sách yêu thích
        Hotel hotel = hotelRepository.findById(idHotel)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy homestay trên"));

        // kiêm tra xem khách sạn đó có trong danh sách yeeu thích của user hay không
        if (!user.getHotelList().contains(hotel)) {
            throw new IllegalStateException("Homestay trên không phải homestay được yêu thích của người dùng");
        }
        // remove ra khỏi danh sách yêu thích
        user.getHotelList().remove(hotel);
        return userRepository.save(user);
    }
}