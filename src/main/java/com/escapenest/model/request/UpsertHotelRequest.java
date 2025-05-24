package com.escapenest.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertHotelRequest {
    String nameHotel ;
    String descriptionHotel;
    String rentalType ;
    String phoneHotel ;
    String emailHotel ;
    String addressHotel;
    Integer idCity ;
    Integer star ;
    List<Integer> amenityHotelList;
    Boolean status;
}
