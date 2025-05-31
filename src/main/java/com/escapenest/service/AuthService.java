package com.escapenest.service;

import com.escapenest.model.request.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.escapenest.entity.TokenConfirm;
import com.escapenest.entity.User;
import com.escapenest.exception.BadRequestException;
import com.escapenest.model.response.VerifyAccountResponse;
import com.escapenest.model.enums.Gender;
import com.escapenest.model.enums.TokenType;
import com.escapenest.model.enums.UserRole;
import com.escapenest.repository.TokenConfirmRepository;
import com.escapenest.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {



    private final HttpSession httpSession;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenConfirmRepository tokenConfirmRepository;
    private final MailService mailService;

    public void login(LoginRequest loginRequest) {
        // Taạo đối tượng xác thực
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword());
        try {
            // Tiến hành xác tực
            Authentication authentication = authenticationManager.authenticate(token);
//            // lưu vào securityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // lưu vào session
            httpSession.setAttribute("MY_SESSION",authentication.getName());
        }
        catch(DisabledException disabledException){
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(()-> new UsernameNotFoundException("Không tìm thấy User có email : " + loginRequest.getEmail()));
            checkExpiredToken(user);
            throw new DisabledException("Tài khoản chưa được xác thực. Vui lòng kiểm tra email để xác thực tài khoản.");
        }
        catch (AuthenticationException  e){
           throw new RuntimeException ("Tài khoản hoặc mật khẩu không chính xác");
        }
    }

    // kiểm tra token của người dùng  đó đã hết hạn hay chưa
    private void checkExpiredToken(User user) {
        // tìm token với user tương ứng
        TokenConfirm tokenConfirm =
                tokenConfirmRepository.findAllByUser_IdAndTokenTypeOrderByCreatedAtDesc(user.getId(),TokenType.REGISTRATION);
        // kiểm tra xem token đó đã hết hạn hay chưa
        if (tokenConfirm.getExpiredAt().isBefore(LocalDateTime.now())){
            // tạo token mới cho người dùng đó nếu đã hết hạn
            createTokenConfirmAccount(user);
            // xóa token cũ đi
            tokenConfirmRepository.delete(tokenConfirm);
        }
    }


    // logic xủ lý khi người dùng đăng ký tài khoản mới
    @Transactional
    public String register(RegisterRequest registerRequest) {
        // kiểm tra email có tồn tại trong hệ thống hay không
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new BadRequestException("Email đã tồn tại");
        }
        // confirm password người dùng đã nhập
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())){
            throw new BadRequestException("Mật khẩu không trùng khớp");
        }
        // tạo user
        User user = User.builder()
                .name(registerRequest.getName())
                .userRole(UserRole.ROLE_USER)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .createdAt(LocalDate.now())
                .avatar("/web/assets/image/avata-default.jpg")
                .enable(false)
                .suspended(false)
                .build();
        userRepository.save(user);
        createTokenConfirmAccount(user);
        return "Đăng ký thành công. Vui lòng kiểm tra email để xác thực tài khoản";
    }

    private void createTokenConfirmAccount(User user) {
        TokenConfirm tokenConfirmOld= tokenConfirmRepository.findAllByUser_IdAndTokenTypeOrderByCreatedAtDesc(user.getId(),TokenType.REGISTRATION);
        if ( tokenConfirmOld != null && tokenConfirmOld.getConfirmedAt() == null
                && tokenConfirmOld.getExpiredAt().isBefore(LocalDateTime.now())){
            tokenConfirmRepository.delete(tokenConfirmOld);
        }
        // tạo token để xác thực
        TokenConfirm tokenConfirm = TokenConfirm.builder()
                .nameToken(UUID.randomUUID().toString())
                .tokenType(TokenType.REGISTRATION)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusHours(1))
                .user(user)
                .build();
        tokenConfirmRepository.save(tokenConfirm);
        // gửi  link xác thực gửi đến email
        String link = "http://localhost:9000/account/xac-minh-tai-khoan?token=" + tokenConfirm.getNameToken();
        mailService.sendMail(user.getEmail(),
                "Xác thực tài khoản",
                "Chào " +user.getName()+"! \n" +
                        "\n" +
                        "Chúng tôi xác nhận rằng bạn đã đăng ký tài khoản thành công tại EscapeNest.\n" +
                        "\n" +
                        "Xin vui lòng nhấp vào liên kết sau để xác nhận đăng ký của bạn và kích hoạt tài khoản:\n" +
                        "\n" +
                        link+"\n" +
                        "\n" +
                        "Trân trọng.\n" );
    }


    // xác minh token
    @Transactional
    public VerifyAccountResponse verifyAccount(String token) {
        // lấy ra token ,  thuộc type registation hay không
        Optional<TokenConfirm> optionalTokenConfirm = tokenConfirmRepository
                .findByNameTokenAndTokenType(token, TokenType.REGISTRATION);

        // kiểm tra token có tồn tại hay không
        if (optionalTokenConfirm.isEmpty()){
            return VerifyAccountResponse.builder()
                    .success(false)
                    .message("Link xác thực không tồn tại")
                    .build();
        }
        // kiểm tra xem token đã hết hạn hay chưa
        TokenConfirm tokenConfirm = optionalTokenConfirm.get();
        if (tokenConfirm.getExpiredAt().isBefore(LocalDateTime.now())){
            return VerifyAccountResponse.builder()
                    .success(false)
                    .message("Link xác thực đã hết hạn ")
                    .build();
        }
        // kiểm tra xem token đã được xác thực trước đó hayy chưa
        if (tokenConfirm.getConfirmedAt()!=null){
            return VerifyAccountResponse.builder()
                    .success(false)
                    .message("Link xác thực đã được sử dụng trước đó")
                    .build();
        }

        // set enable cho user là true để tài khoản đó có thể đăng nhập
        tokenConfirm.getUser().setEnable(true);
        userRepository.save(tokenConfirm.getUser());

        // set thời gian đã xác thực cho token
        tokenConfirm.setConfirmedAt(LocalDateTime.now());
        tokenConfirmRepository.save(tokenConfirm);
        // trả về một instance VerifyAccountResponse
            return VerifyAccountResponse.builder()
                    .success(true)
                    .message("Xác thực tài khoản thành công")
                    .build();
    }


    @Transactional
    public void updateUser(Integer id, ChangeInformationUserRequest changeInformationUserRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Không thể tìm thấy user trên ") );
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
        LocalDate birthday = LocalDate.parse(changeInformationUserRequest.getBirthDay(),dateTimeFormatter);
        user.setAddress(changeInformationUserRequest.getAddress());
        user.setName(changeInformationUserRequest.getName());
        String regex = "^(0\\d{9}|(\\+84|84)[1-9]\\d{7})$";

        if (changeInformationUserRequest.getPhone().matches(regex)){
            user.setPhoneNumber(changeInformationUserRequest.getPhone());
        }else {
            throw new BadRequestException("Số điện thoại của bạn không hợp lệ ");
        }
        user.setUpdateAt(LocalDate.now());
        user.setBirthDay(birthday);
        Gender gender  = Gender.valueOf(changeInformationUserRequest.getGender());
        user.setGender(gender);
        userRepository.save(user);
    }


    // thay đổi mật khẩu
    public void changePassword(Integer id, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy user") );

        // kiểm tra xem mật khẩu có đúng với mật khẩu được lưu trong db không
        if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())){
            // kiểm tra xem 2 mật khẩu có giống nhau hay không
            if (changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())){
                user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                userRepository.save(user);
            }
            else {
                throw new BadRequestException("Mật khẩu không trùng khớp");
            }

        }else {
            throw new BadRequestException("Mật khẩu không chính xác");
        }
    }
    // thay ddooir mật khẩu khi người dùng chọn chức nang quên mật khẩu
    public void changePasswordForForgetPassword(UpsertPasswordRetrieval upsertPasswordRetrieval) {
        // tìm token quên mật khẩu
        TokenConfirm tokenConfirm = tokenConfirmRepository
                .findByNameTokenAndTokenType(upsertPasswordRetrieval.getNameToken(), TokenType.FORGOT_PASSWORD).orElseThrow(()-> new BadRequestException("Không tìm thấy token trên "));
        tokenConfirm.getUser().setPassword(passwordEncoder.encode(upsertPasswordRetrieval.getPassword()));
        tokenConfirm.setConfirmedAt(LocalDateTime.now());
        userRepository.save(tokenConfirm.getUser());
    }


    // logic xử lý chức năng quên mật khẩu
    public void forgotPassword(String email) {
        // lấy user với email người dùng nhập
       User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Tài khoản không tồn tại ."));
       // tìm xem có token lấy lật khẩu cũ không
       TokenConfirm tokenConfirmOld= tokenConfirmRepository.findAllByUser_IdAndTokenTypeOrderByCreatedAtDesc(user.getId(),TokenType.FORGOT_PASSWORD);
        if (tokenConfirmOld != null && tokenConfirmOld.getConfirmedAt()==null && tokenConfirmOld.getExpiredAt().isAfter(LocalDateTime.now())) {
            throw new  RuntimeException("Vui lòng kiểm tra email để xác thực tài khoản");
        }else if ( tokenConfirmOld != null && tokenConfirmOld.getConfirmedAt() != null ){
            tokenConfirmRepository.delete(tokenConfirmOld);
        }

       // tạo một token với type ForGot Password
        TokenConfirm tokenConfirm = TokenConfirm.builder()
                .nameToken(UUID.randomUUID().toString())
                .tokenType(TokenType.FORGOT_PASSWORD)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusHours(1))
                .user(user)
                .build();
        tokenConfirmRepository.save(tokenConfirm);
        // gửi link xác thực tới email
        String link = "http://localhost:9000/account/quen-mat-khau?token=" + tokenConfirm.getNameToken();
        mailService.sendMail(user.getEmail(),
                "Quên mật khẩu ",
                "Chào " +user.getName()+"! \n" +
                        "\n" +
                        "Chúng tôi xác nhận rằng bạn đã yêu cầu cấp lại mật khẩu cho tài khoản sử dụng email này.\n" +
                        "\n" +
                        "Xin vui lòng nhấp vào liên kết sau để chuyến tới trang lấy lại mật khẩu cho bạn:\n" +
                        "\n" +
                        link+"\n" +
                        "\n" +
                        "Trân trọng.\n" );
    }


    // logic xử lý link với chức năng quên mật khẩu
    public VerifyAccountResponse verifyForgotPassword(String token) {
        Optional<TokenConfirm> optionalTokenConfirm = tokenConfirmRepository
                .findByNameTokenAndTokenType(token, TokenType.FORGOT_PASSWORD);
        if (optionalTokenConfirm.isEmpty()){
            return VerifyAccountResponse.builder()
                    .success(false)
                    .message("Link lấy lại mật khẩu không tồn tại")
                    .build();
        }
        TokenConfirm tokenConfirm = optionalTokenConfirm.get();
        if (tokenConfirm.getExpiredAt().isBefore(LocalDateTime.now())){
            return VerifyAccountResponse.builder()
                    .success(false)
                    .message("Link lấy lại mật khẩu đã được sử dụng trước đó ")
                    .build();
        }
        if (tokenConfirm.getConfirmedAt()!=null){
            return VerifyAccountResponse.builder()
                    .success(false)
                    .message("Link lấy lại mật khẩu đã hết hạn")
                    .build();
        }
        tokenConfirmRepository.save(tokenConfirm);
        return VerifyAccountResponse.builder()
                .success(true)
                .message("Link xác thực không tồn tại")
                .build();
    }


    // lấy user đang đăng nhập
    public User getUserCurrent (){
        return userRepository.findByEmail(httpSession.getAttribute("MY_SESSION").toString()).orElseThrow(()->new UsernameNotFoundException("Không tìm thấy user hiện tại "));
    }


    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    // lấy thông tin user với id được cung cấp
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("Không tìm thấy user nào có id :"+id));
    }
    public boolean isSuspended(String emailUser) {
        User user = userRepository.findByEmail(emailUser).orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
        if(user.getSuspended().equals(true))
        {
            return true;
        }
        return false;
    }

}
