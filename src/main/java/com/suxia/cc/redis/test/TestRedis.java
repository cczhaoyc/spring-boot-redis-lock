package com.suxia.cc.redis.test;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.suxia.cc.redis.client.RedisClient;
import com.suxia.cc.redis.constant.RedisConstant;
import com.suxia.cc.redis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.applet.Main;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description TODO
 * @date 2020/4/22 10:13
 */
@RestController
@RequestMapping("/redis")
public class TestRedis {

    @Autowired
    private RedisClient redisClient;

    @PostMapping("/put")
    public Map<String, Object> put(String key, String value) {
        boolean set = redisClient.put(key, value, RedisConstant.DEFAULT_EXPIRE_TIME);
        long expire = redisClient.getExpire(key);
        Map<String, Object> map = new HashMap<>();
        map.put("set", set);
        map.put("expire", expire);
        return map;
    }

    @GetMapping("/get")
    public String get(String key) {
        return this.redisClient.get(key, String.class);
    }

    @GetMapping("/putO")
    public Map<String, Object> putO() {
        User user = new User();
        user.setName("苏夏");
        user.setAge(20);
        ZonedDateTime zdt = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss", Locale.CHINA);
        String birthday = dateTimeFormatter.format(zdt);
        user.setBirthday(birthday);
        Boolean set = redisClient.put("suxia:obb", user, RedisConstant.DEFAULT_EXPIRE_TIME);
        long expire = redisClient.getExpire("suxia:obb");
        Map<String, Object> map = new HashMap<>();
        map.put("set", set);
        map.put("expire", expire);
        return map;
    }

    @GetMapping("/lRightPush")
    public Map<String, Object> lRightPush() {
        Map<String, Object> map = new HashMap<>();
        redisClient.listRightPush("suxia:list", "诸葛亮", RedisConstant.DEFAULT_EXPIRE_TIME);
        redisClient.listRightPush("suxia:list", "吕布", RedisConstant.DEFAULT_EXPIRE_TIME);
        redisClient.listRightPush("suxia:list", "曹操", RedisConstant.DEFAULT_EXPIRE_TIME);
        redisClient.listRightPush("suxia:list", "孙权", RedisConstant.DEFAULT_EXPIRE_TIME);
        redisClient.listRightPush("suxia:list", "赵云", RedisConstant.DEFAULT_EXPIRE_TIME);
        redisClient.listRightPush("suxia:list", "赵云", RedisConstant.DEFAULT_EXPIRE_TIME);
        redisClient.listRightPush("suxia:list", "赵云", RedisConstant.DEFAULT_EXPIRE_TIME);

        map.put("length", redisClient.listGetSize("suxia:list"));
        return map;
    }

    @GetMapping("/lRemove")
    public Map<String, Object> lRemove() {
        Map<String, Object> map = new HashMap<>();
        redisClient.listRemove("suxia:list", 0L, "赵云");
        return map;
    }


    @GetMapping("/test2")
    public Map<String, Object> test2(String key, String value) {
        boolean result = redisClient.put(key, value, RedisConstant.DEFAULT_EXPIRE_TIME);
        long expire_tmie = redisClient.getExpire(key);
        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        map.put("expire_tmie", expire_tmie);
        return map;
    }

    @GetMapping("/hashPut")
    public void hashPut() {
        User user = new User();
        user.setName("苏夏");
        user.setAge(20);
        ZonedDateTime zdt = ZonedDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss", Locale.CHINA);
        String birthday = dateTimeFormatter.format(zdt);
        user.setBirthday(birthday);
        redisClient.hashPut("suxia", "cc", user);
    }

    @GetMapping("/hashPut2")
    public void hashPut2() {
        redisClient.hashPut("suxia:", "cc1:", "这种方式可以按照Locale默认习惯格式化。我们来看实际效果");
        redisClient.hashPut("suxia:", "cc2:", "这种方式可以按照Locale默认习惯格式化。我们来看实际效果");
        redisClient.hashPut("suxia:", "cc3:", "这种方式可以按照Locale默认习惯格式化。我们来看实际效果");

    }

    @GetMapping("/hashPut3")
    public void hashPut3() {
        Map<String, Object> map = new HashMap<>();
        map.put("result", "result");
        map.put("expire_tmie", "expire_tmie");
        redisClient.hashPutAll("suxia:", map);
    }

    @GetMapping("/hashPut4")
    public void hashPut4() {
        Map<String, Object> map = new HashMap<>();
        map.put("result", "result");
        map.put("expire_tmie", "expire_tmie");
        redisClient.hashPutAll("suxia:", map);
    }

    @GetMapping("/removeall")
    public void remove(String key, String key2) {
        redisClient.remove(key, key2);
    }

    @GetMapping("/remove")
    public void remove(String key) {
        redisClient.remove(key);
    }

    @GetMapping("/getExpire")
    public Long getExpire(String key) {
        Long expire = redisClient.getExpire(key);
        return expire;
    }

    @GetMapping("/setIfAbsent")
    public Boolean setIfAbsent(String key, String value) {
        return redisClient.setIfAbsent(key, value);
    }

    @GetMapping("/getAndSet")
    public Map<String, String> getAndSet(String key, String value) {
        String value1 = redisClient.get(key, String.class);
        String value2 = redisClient.getAndSet(key, value, RedisConstant.DEFAULT_EXPIRE_TIME);
        Map<String, String> map = new HashMap<>();
        map.put("value1", value1);
        map.put("value2", value2);
        return map;
    }


}
