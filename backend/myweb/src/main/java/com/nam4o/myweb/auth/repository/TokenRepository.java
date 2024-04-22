package com.nam4o.myweb.auth.repository;

import com.nam4o.myweb.auth.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String > {

    Optional<Token> findByAccessToken(String accessToken);
}
