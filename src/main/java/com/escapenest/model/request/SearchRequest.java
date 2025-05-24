package com.escapenest.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    String nameCity;
    String checkIn;
    String checkOut;
    Integer numberGuest;
    Integer numberRoom;

}
