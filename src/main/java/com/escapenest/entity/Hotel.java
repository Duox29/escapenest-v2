    package com.escapenest.entity;

    import jakarta.persistence.*;
    import lombok.*;
    import lombok.experimental.FieldDefaults;
    import com.escapenest.model.enums.RentalType;

    import java.time.LocalDate;
    import java.util.List;


    @ToString
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Entity
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "hotels")
    @Getter
    @Setter
    public class Hotel {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Integer id;

        @Column(name = "name" , unique = true)
        String name;

        @Column(name = "email" , unique = true)
        String email;

        @Column(columnDefinition = "TEXT")
        String description;

        String address;

        String poster;

        @ManyToOne
        @JoinColumn(name = "city_id")
        City city;


        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "policyHotel_id" )
        PolicyHotel policyHotel;

        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id" )
        User user;

        Integer star;

        String hotline;

        Float rating;

        @Enumerated(EnumType.STRING)
        RentalType rentalType;


        @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL ,fetch = FetchType.EAGER, orphanRemoval = true )
        List<RoomPrice> priceRoomList;

        @ManyToMany( fetch = FetchType.EAGER)
        @JoinTable(
                name = "amenity_hotel",
                joinColumns = @JoinColumn(name = "id_hotel"),
                inverseJoinColumns = @JoinColumn(name = "id_amenity")
        )

    //    @Fetch(FetchMode.SUBSELECT)
        List<AmenityHotel> amenityHotelList ;

        public String getRatingText() {
            if (rating  <= 1.0f) {
                return "Chưa có đánh giá";
            }
            // switch rating from 1 to 10
            if (rating <= 2.0f) {
                return "Quá Tệ";
            } else if (rating <= 3.0f) {
                return "Tệ";
            } else if (rating <= 4.0f) {
                return "Kém";
            } else if (rating <= 5.0f) {
                return "Chấp nhận được";
            } else if (rating <= 6.0f) {
                return "Tốt";
            } else if (rating <= 7.0f) {
                return "Rất tốt";
            } else if (rating <= 8.0f) {
                return "Tuyệt vời";
            } else if (rating <= 9.0f) {
                return "Trên cả tuyệt vời";
            } else if (rating <= 10.0f) {
                return  "Xuất sắc";
            } else {
               return  "Chưa có đánh giá";
            }
        }





        LocalDate createdAt;
        LocalDate updatedAt;
        Boolean status;



    }
