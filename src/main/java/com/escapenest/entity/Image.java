package com.escapenest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PROTECTED)
@Table(name = "images")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Image {

    @Id
    String id;

    String name;
    String type;
    String url;

    Double size;

    LocalDate createdAt;

    @PrePersist
    public  void prePersist(){
        createdAt = LocalDate.now();
    }



}