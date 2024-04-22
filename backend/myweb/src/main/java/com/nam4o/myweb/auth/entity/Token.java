package com.nam4o.myweb.auth.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "token", timeToLive = 60*60*24*21)
public class Token {
    @Id
    private String id;

    private String refreshToken;

    private String accessToken;

}
