package com.nam4o.myweb.auth.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@AllArgsConstructor
@RedisHash(value = "token", timeToLive = 60*60*24*21)
public class Token {
    @Id
    @Indexed
    private String id;

    @Indexed
    private String refreshToken;

    @Indexed
    private String accessToken;

}
