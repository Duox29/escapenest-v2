package com.escapenest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class ImageRoom extends Image {
    @ManyToOne
    @JoinColumn(name = "room_id")
     Room room;
}
