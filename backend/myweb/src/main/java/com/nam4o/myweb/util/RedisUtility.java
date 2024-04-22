package com.nam4o.myweb.util;

import com.nam4o.myweb.auth.entity.Token;
import com.nam4o.myweb.auth.repository.TokenRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Getter
@Service
@RequiredArgsConstructor
public class RedisUtility {

    private final StringRedisTemplate stringRedisTemplate;
    private final TokenRepository tokenRepository;

    public void repo(String key, String value) {
        tokenRepository.save(new Token(key, value));
    }

    public boolean checkBlackList(String key) {
        return  tokenRepository.findById(key).get().getIsLogout();
    }

    public String getData(String key) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();

        return valueOperations.get(key);
    }

    public boolean existData(String key) {

        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteData(String key) {
        stringRedisTemplate.delete(key);
    }
}
