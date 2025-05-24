package com.escapenest.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertSupportRequest {
    String title ;
    String description ;
    String content ;
    Boolean status ;
    String supportType;
}
