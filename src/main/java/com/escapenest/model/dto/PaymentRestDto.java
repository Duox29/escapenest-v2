package com.escapenest.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRestDto {
    private String status;
    private String message;
    private String url;

}
