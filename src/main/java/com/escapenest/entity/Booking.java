package com.escapenest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.escapenest.model.enums.PaymentMethod;
import com.escapenest.model.enums.StatusBooking;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
@Builder

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    User user;


    @ManyToOne
    @JoinColumn(name = "id_hotel")
    Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "id_room")
    Room room;

    String nameCustomer;
    String emailCustomer;
    String phoneCustomer;

    Integer guests;

    LocalDate checkIn;

    LocalDate checkOut;

    Integer price;
    Integer numberRoom;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    StatusBooking statusBooking;

    Boolean isReviewed;

    LocalDate createAt;
    LocalDate updateAt;
}
