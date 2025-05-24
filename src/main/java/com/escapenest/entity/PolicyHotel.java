package com.escapenest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "policy")
@NoArgsConstructor
@AllArgsConstructor
public class PolicyHotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "TEXT")
    String startCheckIn;
    @Column(columnDefinition = "TEXT")
    String endCheckIn;
    @Column(columnDefinition = "TEXT")
    String startCheckOut;
    @Column(columnDefinition = "TEXT")
    String endCheckOut;
    @Column(columnDefinition = "TEXT")
    String service;
    @Column(columnDefinition = "TEXT")
    String cancelPolicy;
    @Column(columnDefinition = "TEXT")
    String animal;
    @Column(columnDefinition = "TEXT")
    String ageLimit;
    @Column(columnDefinition = "TEXT")
    String note;

    LocalDate createdAt;
    LocalDate updatedAt;

}
