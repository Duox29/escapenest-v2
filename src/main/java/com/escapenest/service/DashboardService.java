package com.escapenest.service;

import lombok.RequiredArgsConstructor;
import com.escapenest.model.dto.TotalBookingMonthDto;
import com.escapenest.model.dto.RegisterDto;
import com.escapenest.repository.BookingRepository;
import com.escapenest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public List<RegisterDto> getUserByMonth (int limit){
        List<RegisterDto> registerDtos = userRepository.findUserRegistrationsByMonthAndYear();
        List<RegisterDto> result = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            boolean check = false;
            LocalDate localDate = LocalDate.now().minusMonths(limit-i-1);
            for (RegisterDto registerDto : registerDtos){
                if (registerDto.getMonth() == localDate.getMonthValue() &&
                        registerDto.getYear() == localDate.getYear()){
                    result.add(registerDto);
                    check=true;
                    break;
                }
            }
            if (!check){
                RegisterDto registerDtoNew = new RegisterDto();
                registerDtoNew.setMonth(localDate.getMonthValue());
                registerDtoNew.setTotalUser(0);
                registerDtoNew.setYear(localDate.getYear());
                result.add(registerDtoNew);
            }

        }
        return result;
    }
    public List<TotalBookingMonthDto> getBookingByMonth (int limit){
        List<TotalBookingMonthDto> totalBookingMonthDto = bookingRepository.finTotalBookingMonth();
        List<TotalBookingMonthDto> result = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            boolean check = false;
            LocalDate localDate = LocalDate.now().minusMonths(limit-i-1);
            for (TotalBookingMonthDto totalBookingMonthDto1 : totalBookingMonthDto){
                if (totalBookingMonthDto1.getMonth() == localDate.getMonthValue() &&
                        totalBookingMonthDto1.getYear() == localDate.getYear()){

                    result.add(totalBookingMonthDto1);
                    check=true;
                    break;
                }
            }
            if (!check){
                TotalBookingMonthDto totalBookingMonthDtoNew = new TotalBookingMonthDto();
                totalBookingMonthDtoNew.setMonth(localDate.getMonthValue());
                totalBookingMonthDtoNew.setTotalBooking(0);
                totalBookingMonthDtoNew.setYear(localDate.getYear());
                result.add(totalBookingMonthDtoNew);
            }

        }
        return result;
    }



}
