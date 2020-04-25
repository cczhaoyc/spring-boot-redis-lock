package com.suxia.cc.rabbit.controller;

import com.suxia.cc.rabbit.client.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zychao950224@dingtalk.com
 * @version v_1.0.0
 * @description TODO
 * @date 2020/4/21 19:20
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private MsgProducer msgProducer;

    @GetMapping("/send")
    public String test(String text) {
        msgProducer.sendMsg(text);
        return "Success";
    }

    @GetMapping("/sendb")
    public String testB(String text) {
        msgProducer.sendMsgB(text);
        return "Success";
    }
}
