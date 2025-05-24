package com.escapenest.repository;

import com.escapenest.entity.TokenConfirm;
import com.escapenest.model.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenConfirmRepository extends JpaRepository<TokenConfirm,String> {

    TokenConfirm findByNameToken ( String nameToken);

    Optional<TokenConfirm> findByNameTokenAndTokenType(String token, TokenType tokenType);

    TokenConfirm findAllByUser_IdAndTokenTypeOrderByCreatedAtDesc(Integer id,TokenType tokenType);
}
