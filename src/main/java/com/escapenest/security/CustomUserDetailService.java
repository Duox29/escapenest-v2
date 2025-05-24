package com.escapenest.security;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import com.escapenest.entity.User;
import com.escapenest.repository.HotelRepository;
import com.escapenest.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Không tìm thấy user có Email là : " + email));
        return new  CustomUserDetail(user);
    }


}
