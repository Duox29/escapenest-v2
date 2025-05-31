package com.escapenest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.escapenest.model.enums.Gender;
import com.escapenest.model.enums.UserRole;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    @Column(name = "email", unique = true,nullable = false)
    String email;

    @Column( unique = true,nullable = false)
    String password;

    @Column(name = "avatar" )
    String avatar;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    UserRole userRole;

    LocalDate birthDay;

    String phoneNumber;

    String address;

    @Enumerated(EnumType.STRING)
    Gender gender;




    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "hotel_favourite",
            joinColumns =@JoinColumn (name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_id" ) )
    List<Hotel> hotelList;

    Boolean enable;
    Boolean suspended;


    LocalDate createdAt;

    LocalDate updateAt;



}
