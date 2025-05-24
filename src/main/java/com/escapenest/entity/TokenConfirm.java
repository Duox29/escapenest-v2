package com.escapenest.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.escapenest.model.enums.TokenType;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "token_confirm")
@Builder
public class TokenConfirm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String nameToken; // chuỗi token

    @Enumerated(EnumType.STRING)
    TokenType tokenType; // loại token

    LocalDateTime createdAt; // thời gian tạo
    LocalDateTime expiredAt; // thời gian hết hạn
    LocalDateTime confirmedAt; // thời gian xác thực

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user; // token này của user nào
}
