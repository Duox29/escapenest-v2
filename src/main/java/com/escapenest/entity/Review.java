package com.escapenest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    User user;

    @ManyToOne
    @JoinColumn(name = "id_hotel" , nullable = false)
    Hotel hotel;

    String comment;

    Integer rating;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_booking" )
    Booking booking;

    @Column(name = "create_at")
    LocalDate createAt;

    @Column(name = "update_at")
    LocalDate updateAt;

    public String getRatingText() {
        if (rating == null) {
            return "Chưa có đánh giá";
        }
        // switch rating from 1 to 10
        return switch (rating) {
            case 1 -> "Quá Tệ";
            case 2 -> "Tệ";
            case 3 -> "Quá Kém";
            case 4 -> "Kém";
            case 5 -> "Chấp nhận được";
            case 6 -> "Tốt";
            case 7 -> "Rất tốt";
            case 8 -> "Tuyệt vời";
            case 9 -> "Trên cả tuyệt vời";
            case 10 -> "Xuất sắc";
            default -> "Chưa có đánh giá";
        };
    }

}
