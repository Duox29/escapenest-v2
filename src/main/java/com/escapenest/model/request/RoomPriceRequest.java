package com.escapenest.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomPriceRequest {
    String startDate;
    String endDate;
    List<String> dayApply;
    Integer idRoom;
    Integer price;
}
