package com.escapenest.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BedType {
    SINGLE("Giường đơn"),
    DOUBLE("Giường đôi"),
    BUNK("Giường tầng"),
    SOFA_BED("Giường sofa"),
    ROUND_BED("Giường tròn");







    private final String value;
}
