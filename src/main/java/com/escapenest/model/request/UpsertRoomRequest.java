package com.escapenest.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertRoomRequest {
    String nameRoom;
    String descriptionRoom;
    List<Integer> amenityRoom;
    String roomType;
    Integer area;
    Integer capacity;
    Integer quantity;
    String bedType;
    String bedSize;
    Integer priceDefault;
    Boolean statusRoom;
}
