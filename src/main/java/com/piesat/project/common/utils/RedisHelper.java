package com.piesat.project.common.utils;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

public class RedisHelper {

    private RedisTemplate<String, Object> redisTemplate;
    private String name;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    private RedisHelper(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public static RedisHelper create(RedisTemplate<String, Object> redisTemplate) {
        return new RedisHelper(redisTemplate);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Object getNativeCache() {
        return this.redisTemplate;
    }

    public Object get(Object key) {
        SuperLogger.e("读取缓存");
        final String keyf = key.toString();
        Object object = null;
        object = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] key = keyf.getBytes();
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                return toObject(value);
            }
        });
        return (object != null ? object : null);
    }

    public void put(Object key, Object value) {
        com.piesat.project.common.utils.SuperLogger.e("插入缓存");
        final String keyf = key.toString();
        final Object valuef = value;
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keyb = keyf.getBytes();
                byte[] valueb = toByteArray(valuef);
                connection.set(keyb, valueb);
                return 1L;
            }
        });
    }

    private byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    private Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    public void evict(Object key) {
        com.piesat.project.common.utils.SuperLogger.e("del key:"+key);
        final String keyf = key.toString();
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.del(keyf.getBytes());
            }
        });
    }

    public void clear() {
        com.piesat.project.common.utils.SuperLogger.e("clear key");
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    public static <K,V> void setValue(RedisTemplate<K,V> redisTemplate,K key,V value){
        setValue(redisTemplate,key, value, 10);
    }

    public static <K,V> void setValue(RedisTemplate<K,V> redisTemplate,K key,V value,long timeout){
        ValueOperations<K, V> operations = redisTemplate.opsForValue();
        operations.set(key, value, timeout, TimeUnit.SECONDS);
    }

    public static <K,V>V getValue(RedisTemplate<K,V> redisTemplate,K key){
        ValueOperations<K, V> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    public static <K,V> boolean deleteValue(RedisTemplate<K,V> redisTemplate, K key){
        return redisTemplate.delete(key);
    }
}