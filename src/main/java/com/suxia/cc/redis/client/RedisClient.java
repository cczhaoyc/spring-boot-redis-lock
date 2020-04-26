package com.suxia.cc.redis.client;


import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description redis服务类
 * @date 2020/4/22 10:35
 */
public interface RedisClient {

    /**
     * 根据key获取缓存
     *
     * @param key 键
     * @return 值
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 缓存字符串，默认缓存失效时间2小时
     *
     * @param key   键
     * @param value 值
     * @ return   true成功, false失败
     */
    <T> Boolean put(String key, T value);

    /**
     * 普通缓存放入并设置时间
     *
     * @param key        键
     * @param value      值
     * @param expireTime 时间(秒)
     * @ return   true成功 false 失败
     */
    <T> Boolean put(String key, T value, Long expireTime);

    /**
     * 指定缓存失效时间
     *
     * @param key        键
     * @param expireTime 缓存失效时间(秒)
     */
    Boolean expire(String key, Long expireTime);

    /**
     * 根据key获取过期时间
     *
     * @param key 键 不能为null
     * @return 缓存失效时间(秒) 返回0代表为永久有效
     */
    Long getExpire(String key);

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @ return   Boolean.TRUE存在，false不存在
     */
    Boolean hasKey(String key);

    /**
     * 删除缓存
     *
     * @param key 可以传一个值或多个
     */
    void remove(String... key);

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     */
    Long incr(String key, Long delta);

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     */
    Long decr(String key, Long delta);

    /**
     * 获取item对应的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    Object hashGetOne(String key, String item);

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    Map<Object, Object> hashGetAll(String key);

    /**
     * 缓存所有map对应的多个键值
     *
     * @param key 键
     * @param map 对应多个键值
     * @ return   true成功, false失败
     */
    Boolean hashPutAll(String key, Map<String, Object> map);

    /**
     * 缓存所有map对应的多个键值,并设置时间
     *
     * @param key        键
     * @param map        对应多个键值
     * @param expireTime 时间(秒)
     * @ return   true成功, false失败
     */
    Boolean hashPutAll(String key, Map<String, Object> map, Long expireTime);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @ return   Boolean.TRUE 成功 false失败
     */
    Boolean hashPut(String key, String item, Object value);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key        键
     * @param item       项
     * @param value      值
     * @param expireTime 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @ return   true成功, false失败
     */
    Boolean hashPut(String key, String item, Object value, Long expireTime);

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    void hashDelete(String key, Object... item);

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @ return   Boolean.TRUE存在，false不存在
     */
    Boolean hasHasKey(String key, String item);

    /**
     * hash递增，如果不存在，就会创建一个，并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     */
    Double hashIncrement(String key, String item, Double by);

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     */
    Double hashDecrease(String key, String item, Double by);

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     */
    Set<Object> setGetAll(String key);

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @ return   Boolean.TRUE 存在 false不存在
     */
    Boolean setHasKey(String key, Object value);

    /**
     * 将set数据放入缓存
     *
     * @param key        键
     * @param expireTime 时间(秒)
     * @param values     值 可以是多个
     * @return 成功个数
     */
    Long setPut(String key, Long expireTime, Object... values);

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     */
    Long setGetSize(String key);

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值，可以是多个
     * @return 移除的个数
     */
    Long setRemove(String key, Object... values);

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     */
    List<Object> listGet(String key, Long start, Long end);

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     */
    Long listGetSize(String key);

    /**
     * 通过索引，获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    Object listGetIndex(String key, Long index);


    /**
     * 将list放入缓存
     *
     * @param key        键
     * @param value      值
     * @param expireTime 时间(秒)
     */
    Boolean listRightPush(String key, Object value, Long expireTime);


    /**
     * 将list放入缓存
     *
     * @param key        键
     * @param value      值
     * @param expireTime 时间(秒)
     */
    Boolean listRightPushAll(String key, List<Object> value, Long expireTime);

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     */
    Boolean listUpdateIndex(String key, Long index, Object value);

    /**
     * 移除多个值
     *
     * @param key   键
     * @param count 从存储在键中的列表中删除等于值的元素的第一个计数事件。count> 0：删除等于从左到右移动的值的第一个元素；count< 0：删除等于从右到左移动的值的第一个元素；count = 0：删除等于value的所有元素。
     * @param value 值
     * @return 移除的个数
     */
    Long listRemove(String key, Long count, Object value);

    /**
     * 如果键不存在则新增,存在则不改变已经有的值
     */
    Boolean setIfAbsent(String key, String value);

    /**
     * 如果键不存在则新增,存在则不改变已经有的值
     */
    Boolean setIfAbsent(String key, String value, Long expireTime);

    /**
     * 获取旧值并设置新值，并设置过期时间
     */
    String getAndSet(String key, String value, Long newExpireTime);

}
