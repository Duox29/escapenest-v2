package com.escapenest.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class UpsertPasswordRetrieval {
    String nameToken;
    String password;
}
