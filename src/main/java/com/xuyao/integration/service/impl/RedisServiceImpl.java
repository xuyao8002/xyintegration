package com.xuyao.integration.service.impl;

import com.xuyao.integration.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

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




}
