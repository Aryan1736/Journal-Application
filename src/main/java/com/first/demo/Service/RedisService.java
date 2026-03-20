package com.first.demo.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass) throws JsonProcessingException {
        Object o = redisTemplate.opsForValue().get(key);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(o.toString(),entityClass);
    }

    public void set(String key, Object o, Long ttl) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonValue = mapper.writeValueAsString(o);
        redisTemplate.opsForValue().set(key,jsonValue,ttl, TimeUnit.SECONDS);
    }

}
