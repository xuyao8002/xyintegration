package com.xuyao.integration.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {


    <K, V> void push(K key, V value);

    <K, V> V pop(K key);

    <K, V> void set(K key, V value);

    void setString(String key, String value);

    <K, V> void set(K key, V value, long timeout, TimeUnit unit);

    void setString(String key, String value, long timeout, TimeUnit unit);

    <K, V> V get(K key);

    String getString(String key);

    String get(String key, long start, long end);

    String getString(String key, long start, long end);
}
