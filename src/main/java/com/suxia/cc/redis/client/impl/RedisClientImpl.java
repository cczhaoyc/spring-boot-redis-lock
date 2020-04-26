package com.suxia.cc.redis.client.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.suxia.cc.redis.client.RedisClient;
import com.suxia.cc.redis.constant.RedisConstant;
import com.suxia.cc.redis.exception.RedisClientException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description redis服务类
 * @date 2020/4/22 10:35
 */
@Component
public class RedisClientImpl implements RedisClient {

    private static final Logger LOG = LoggerFactory.getLogger(RedisClientImpl.class);

    @Value("${spring.redis.key-prefix}")
    private String redisKeyPrefix;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据key获取缓存
     *
     * @param key 键
     * @return 值
     */
    @Override
    public <T> T get(String key, Class<T> clazz) {
        this.verifyKeyAadExpireTime(key);
        Object value = redisTemplate.opsForValue().get(this.getRedisKeyAll(key));
        if (value != null) {
            String temp;
            try {
                temp = String.valueOf(value);
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                return null;
            }

            if (clazz.isPrimitive()) {
                if (String.class.isAssignableFrom(clazz)) {
                    return (T) temp;
                } else if (int.class.isAssignableFrom(clazz)) {
                    return (T) new Integer(temp);
                } else if (Long.class.isAssignableFrom(clazz)) {
                    return (T) new Long(temp);
                } else if (Boolean.class.isAssignableFrom(clazz)) {
                    return (T) new Boolean(temp);
                } else if (Double.class.isAssignableFrom(clazz)) {
                    return (T) new Double(temp);
                } else if (float.class.isAssignableFrom(clazz)) {
                    return (T) new Float(temp);
                } else if (byte.class.isAssignableFrom(clazz)) {
                    return (T) new Byte(temp);
                } else if (short.class.isAssignableFrom(clazz)) {
                    return (T) new Short(temp);
                } else {
                    throw new RedisClientException("not support type:type=[" + clazz.getName() + "],value=[" + temp + "]");
                }
            } else {
                return JSON.parseObject(temp, clazz);
            }
        }
        return null;
    }

    /**
     * 缓存字符串，缓存默认失效时间2小时
     *
     * @param key   键
     * @param value 值
     * @ return   true成功, false失败
     */
    @Override
    public <T> Boolean put(String key, T value) {
        this.verifyKeyAadExpireTime(key);
        try {
            redisTemplate.opsForValue().set(this.getRedisKeyAll(key), JSON.toJSONString(value, SerializerFeature.WriteClassName), RedisConstant.DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key        键
     * @param value      值
     * @param expireTime 缓存失效时间（秒）
     * @ return   true成功 false 失败
     */
    @Override
    public <T> Boolean put(String key, T value, Long expireTime) {
        this.verifyKeyAadExpireTime(key, expireTime);
        try {
            redisTemplate.opsForValue().set(this.getRedisKeyAll(key), JSON.toJSONString(value, SerializerFeature.WriteClassName), expireTime, TimeUnit.SECONDS);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
    }


    /**
     * 指定缓存失效时间
     *
     * @param key        键
     * @param expireTime 缓存失效时间(秒)
     */
    @Override
    public Boolean expire(String key, Long expireTime) {
        this.verifyKeyAadExpireTime(key, expireTime);
        try {
            redisTemplate.expire(this.getRedisKeyAll(key), expireTime, TimeUnit.SECONDS);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    /**
     * 根据key获取过期时间
     *
     * @param key 键 不能为null
     * @return 缓存失效时间(秒) 返回0代表为永久有效
     */
    @Override
    public Long getExpire(String key) {
        this.verifyKeyAadExpireTime(key);
        try {
            return redisTemplate.getExpire(this.getRedisKeyAll(key), TimeUnit.SECONDS);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return 0L;
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @ return   Boolean.TRUE存在，false不存在
     */
    @Override
    public Boolean hasKey(String key) {
        this.verifyKeyAadExpireTime(key);
        try {
            return redisTemplate.hasKey(this.getRedisKeyAll(key));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值或多个
     */
    @Override
    public void remove(String... key) {
        try {
            if (key != null && key.length > 0) {
                if (key.length == 1) {
                    redisTemplate.delete(this.getRedisKeyAll(key[0]));
                } else {
                    List<String> list = Arrays.asList(key);
                    List<String> redisKeyAllList = new ArrayList<>();
                    list.forEach(k -> {
                        String redisKeyAll = this.getRedisKeyAll(k);
                        redisKeyAllList.add(redisKeyAll);
                    });
                    redisTemplate.delete(redisKeyAllList);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     */
    @Override
    public Long incr(String key, Long delta) {
        this.verifyKeyAadExpireTime(key);
        if (delta < 0) {
            throw new RedisClientException("递增因子必须大于0");
        }
        try {
            return redisTemplate.opsForValue().increment(this.getRedisKeyAll(key), delta);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     */
    @Override
    public Long decr(String key, Long delta) {
        this.verifyKeyAadExpireTime(key);
        if (delta < 0) {
            throw new RedisClientException("递减因子必须大于0");
        }
        try {
            return redisTemplate.opsForValue().increment(this.getRedisKeyAll(key), -delta);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取item对应的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    @Override
    public Object hashGetOne(String key, String item) {
        this.verifyKeyAadExpireTime(key);
        try {
            return redisTemplate.opsForHash().get(this.getRedisKeyAll(key), item);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    @Override
    public Map<Object, Object> hashGetAll(String key) {
        this.verifyKeyAadExpireTime(key);
        try {
            return redisTemplate.opsForHash().entries(this.getRedisKeyAll(key));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 缓存所有map对应的多个键值
     *
     * @param key 键
     * @param map 对应多个键值
     * @ return   true成功, false失败
     */
    @Override
    public Boolean hashPutAll(String key, Map<String, Object> map) {
        this.verifyKeyAadExpireTime(key);
        try {
            redisTemplate.opsForHash().putAll(this.getRedisKeyAll(key), map);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    /**
     * 缓存所有map对应的多个键值,并设置时间
     *
     * @param key        键
     * @param map        对应多个键值
     * @param expireTime 时间(秒)
     * @ return   true成功, false失败
     */
    @Override
    public Boolean hashPutAll(String key, Map<String, Object> map, Long expireTime) {
        this.verifyKeyAadExpireTime(key, expireTime);
        try {
            redisTemplate.opsForHash().putAll(this.getRedisKeyAll(key), map);
            this.expire(key, expireTime);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @ return   Boolean.TRUE 成功 false失败
     */
    @Override
    public Boolean hashPut(String key, String item, Object value) {
        this.verifyKeyAadExpireTime(key);
        try {
            redisTemplate.opsForHash().put(this.getRedisKeyAll(key), item, value);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Boolean.FALSE;

        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key        键
     * @param item       项
     * @param value      值
     * @param expireTime 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @ return   true成功, false失败
     */
    @Override
    public Boolean hashPut(String key, String item, Object value, Long expireTime) {
        this.verifyKeyAadExpireTime(key, expireTime);
        try {
            redisTemplate.opsForHash().put(this.getRedisKeyAll(key), item, value);
            this.expire(key, expireTime);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    @Override
    public void hashDelete(String key, Object... item) {
        this.verifyKeyAadExpireTime(key);
        try {
            redisTemplate.opsForHash().delete(this.getRedisKeyAll(key), item);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @ return   Boolean.TRUE存在，false不存在
     */
    @Override
    public Boolean hasHasKey(String key, String item) {
        this.verifyKeyAadExpireTime(key);
        try {
            return redisTemplate.opsForHash().hasKey(this.getRedisKeyAll(key), item);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    /**
     * hash递增，如果不存在，就会创建一个，并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     */
    @Override
    public Double hashIncrement(String key, String item, Double by) {
        this.verifyKeyAadExpireTime(key);
        try {
            return redisTemplate.opsForHash().increment(this.getRedisKeyAll(key), item, by);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     */
    @Override
    public Double hashDecrease(String key, String item, Double by) {
        this.verifyKeyAadExpireTime(key);
        try {
            return redisTemplate.opsForHash().increment(this.getRedisKeyAll(key), item, -by);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     */
    @Override
    public Set<Object> setGetAll(String key) {
        this.verifyKeyAadExpireTime(key);
        try {
            return redisTemplate.opsForSet().members(this.getRedisKeyAll(key));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @ return   Boolean.TRUE 存在 false不存在
     */
    @Override
    public Boolean setHasKey(String key, Object value) {
        this.verifyKeyAadExpireTime(key);
        try {
            return redisTemplate.opsForSet().isMember(this.getRedisKeyAll(key), value);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key        键
     * @param expireTime 时间(秒)
     * @param values     值 可以是多个
     * @return 成功个数
     */
    @Override
    public Long setPut(String key, Long expireTime, Object... values) {
        this.verifyKeyAadExpireTime(key, expireTime);
        try {
            Long count = redisTemplate.opsForSet().add(this.getRedisKeyAll(key), values);
            this.expire(key, expireTime);
            return count;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return 0L;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     */
    @Override
    public Long setGetSize(String key) {
        this.verifyKeyAadExpireTime(key);
        try {
            return redisTemplate.opsForSet().size(this.getRedisKeyAll(key));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return 0L;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值，可以是多个
     * @return 移除的个数
     */
    @Override
    public Long setRemove(String key, Object... values) {
        this.verifyKeyAadExpireTime(key);
        try {
            return redisTemplate.opsForSet().remove(this.getRedisKeyAll(key), values);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return 0L;
        }
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     */
    @Override
    public List<Object> listGet(String key, Long start, Long end) {
        this.verifyKeyAadExpireTime(key);
        try {
            return redisTemplate.opsForList().range(this.getRedisKeyAll(key), start, end);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     */
    @Override
    public Long listGetSize(String key) {
        this.verifyKeyAadExpireTime(key);
        try {
            return redisTemplate.opsForList().size(this.getRedisKeyAll(key));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return 0L;
        }
    }

    /**
     * 通过索引，获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    @Override
    public Object listGetIndex(String key, Long index) {
        this.verifyKeyAadExpireTime(key);
        try {
            return redisTemplate.opsForList().index(this.getRedisKeyAll(key), index);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }


    /**
     * 将list放入缓存，并设置过期时间
     *
     * @param key        键
     * @param value      值
     * @param expireTime 时间(秒)
     */
    @Override
    public Boolean listRightPush(String key, Object value, Long expireTime) {
        this.verifyKeyAadExpireTime(key, expireTime);
        try {
            redisTemplate.opsForList().rightPush(this.getRedisKeyAll(key), value);
            this.expire(key, expireTime);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
    }


    /**
     * 将list放入缓存
     *
     * @param key        键
     * @param value      值
     * @param expireTime 时间(秒)
     */
    @Override
    public Boolean listRightPushAll(String key, List<Object> value, Long expireTime) {
        this.verifyKeyAadExpireTime(key, expireTime);
        try {
            redisTemplate.opsForList().rightPushAll(this.getRedisKeyAll(key), value);
            this.expire(key, expireTime);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     */
    @Override
    public Boolean listUpdateIndex(String key, Long index, Object value) {
        this.verifyKeyAadExpireTime(key);
        try {
            redisTemplate.opsForList().set(this.getRedisKeyAll(key), index, value);
            return Boolean.TRUE;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    /**
     * 移除多个值
     *
     * @param key   键
     * @param count 从存储在键中的列表中删除等于值的元素的第一个计数事件。count> 0：删除等于从左到右移动的值的第一个元素；count< 0：删除等于从右到左移动的值的第一个元素；count = 0：删除等于value的所有元素。
     * @param value 值
     * @return 移除的个数
     */
    @Override
    public Long listRemove(String key, Long count, Object value) {
        this.verifyKeyAadExpireTime(key);
        try {
            return redisTemplate.opsForList().remove(this.getRedisKeyAll(key), count, value);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return 0L;
        }
    }

    @Override
    public Boolean setIfAbsent(String key, String value) {
        this.verifyKeyAadExpireTime(key);
        return redisTemplate.opsForValue().setIfAbsent(this.getRedisKeyAll(key), JSON.toJSONString(value, SerializerFeature.WriteClassName));
    }

    @Override
    public Boolean setIfAbsent(String key, String value, Long expireTime) {
        this.verifyKeyAadExpireTime(key);
        return redisTemplate.opsForValue().setIfAbsent(this.getRedisKeyAll(key), JSON.toJSONString(value, SerializerFeature.WriteClassName), expireTime, TimeUnit.SECONDS);
    }

    @Override
    public String getAndSet(String key, String value, Long newExpireTime) {
        this.verifyKeyAadExpireTime(key);
        String oldValue = (String) redisTemplate.opsForValue().getAndSet(this.getRedisKeyAll(key), JSON.toJSONString(value, SerializerFeature.WriteClassName));
        this.expire(key, newExpireTime);
        return JSON.parseObject(oldValue, String.class);
    }


    /**
     * 验证缓存失效时间
     *
     * @param expireTime 缓存失效时间
     */
    private void verifyKeyAadExpireTime(Long expireTime) {
        if (expireTime <= 0) {
            throw new RedisClientException("缓存失效时间不能小于0");
        }
    }

    /**
     * 验证缓存key
     */
    private void verifyKeyAadExpireTime(String key) {
        if (StringUtils.isBlank(key)) {
            throw new RedisClientException("缓存key不能为空");
        }
    }

    /**
     * 验证缓存key和失效时间
     *
     * @param expireTime 缓存失效时间
     */
    private void verifyKeyAadExpireTime(String key, Long expireTime) {
        if (StringUtils.isBlank(key)) {
            throw new RedisClientException("缓存key不能为空");
        }
        if (expireTime <= 0) {
            throw new RedisClientException("缓存失效时间不能小于0");
        }
    }

    /**
     * 缓存key
     */
    private String getRedisKeyAll(String key) {
        StringJoiner redisKeyAll = new StringJoiner(RedisConstant.DELIMITER);
        return redisKeyAll.add(redisKeyPrefix).add(key).toString();
    }

}