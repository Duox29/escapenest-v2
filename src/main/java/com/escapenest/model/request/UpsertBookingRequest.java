package com.escapenest.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UpsertBookingRequest {
    Integer idHotel;
    Integer idRoom;
    String nameCustomer;
    String emailCustomer;
    String phoneCustomer;
    Integer guest;
    String checkIn;
    String checkOut;
    Integer price;
    Integer numberRoom;
    String paymentMethod;
}
