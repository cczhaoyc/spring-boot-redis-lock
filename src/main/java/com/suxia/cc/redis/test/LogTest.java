package com.suxia.cc.redis.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description TODO
 * @date 2020/4/24 14:03
 */
@RestController
@RequestMapping("/log")
@Slf4j
public class LogTest {

    @PostMapping("/test")
    public void test() {
        log.trace("======trace");
        log.debug("======debug");
        log.info("======info");
        log.warn("======warn");
        log.error("======error");
    }
}
