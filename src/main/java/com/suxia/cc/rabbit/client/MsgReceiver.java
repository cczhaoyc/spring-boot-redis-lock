package com.suxia.cc.rabbit.client;

import com.suxia.cc.rabbit.configuration.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zychao950224@dingtalk.com
 * @version v_1.0.0
 * @description TODO
 * @date 2020/4/21 17:53
 */
@Component
@RabbitListener(queues = {RabbitConfig.QUEUE_A})
public class MsgReceiver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String content) {
        logger.info("接收处理队列A当中的消息： " + content);
    }

}
