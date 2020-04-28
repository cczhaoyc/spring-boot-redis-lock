package com.suxia.cc.rabbit.controller;

import com.suxia.cc.mybatis.base.result.ActionResult;
import com.suxia.cc.mybatis.base.utils.UUIDUtils;
import com.suxia.cc.rabbit.client.Publisher;
import com.suxia.cc.rabbit.configuration.RabbitConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description TODO
 * @date 2020/4/21 19:20
 */
@RestController
@RequestMapping("/test")
public class RabbitTestController {

    @Autowired
    private Publisher publisher;

    @GetMapping("/send")
    public ActionResult test(String text) {
        publisher.sendMsg(text);
        return ActionResult.success();
    }

    @GetMapping("/sendb")
    public ActionResult testB(String text) {
        publisher.sendMsg(RabbitConstant.EXCHANGE_A, "FFFF_))OOO", text, UUIDUtils.getUUID());
        return ActionResult.success();
    }
}
