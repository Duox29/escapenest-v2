package com.escapenest.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertPolicyRequest {
    String startCheckIn;
    String endCheckIn;
    String startCheckOut;
    String endCheckOut;
    String service;
    String cancel;
    String animal;
    String age;
    String other;
}
