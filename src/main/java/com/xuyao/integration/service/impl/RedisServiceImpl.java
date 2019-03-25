package com.xuyao.integration.service.impl;

import com.xuyao.integration.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public <K, V> void push(K key, V value){
        redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public <K, V> V pop(K key){
        return (V) redisTemplate.opsForList().rightPop(key);
    }

    @Override
    public <K, V> void set(K key, V value){
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setString(String key, String value){
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public <K, V> void set(K key, V value, long timeout, TimeUnit unit){
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public void setString(String key, String value, long timeout, TimeUnit unit){
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public <K, V> V get(K key){
        return (V) redisTemplate.opsForValue().get(key);
    }

    @Override
    public String getString(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public String getString(String key, long start, long end){
        return stringRedisTemplate.opsForValue().get(key, start, end);
    }

    @Override
    public <K, V> Long addSet(K key, V... value){
        return redisTemplate.opsForSet().add(key, value);
    }

    @Override
    public <K> Set getSet(K key){
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public <K, V> boolean isMember(K key, V value){
        return redisTemplate.opsForSet().isMember(key, value);
    }

}
