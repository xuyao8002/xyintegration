package com.xuyao.integration.service;

public interface RedisService {


    <K, V> void push(K key, V value);

    <K, V> V pop(K key);
}
