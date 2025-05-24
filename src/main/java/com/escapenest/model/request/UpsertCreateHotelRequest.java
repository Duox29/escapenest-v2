package com.escapenest.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCreateHotelRequest {
    UpsertHotelRequest upsertHotelRequest;
    UpsertRoomRequest upsertRoomRequest;
    UpsertPolicyRequest upsertPolicyRequest;

}
