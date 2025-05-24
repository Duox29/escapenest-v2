package com.escapenest.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeInformationUserRequest {
    String name;
    String phone;
    String gender;
    String address;
    String birthDay;

}
