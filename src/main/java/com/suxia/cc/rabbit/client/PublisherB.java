package com.suxia.cc.rabbit.client;


import com.suxia.cc.mybatis.base.utils.UUIDUtils;
import com.suxia.cc.rabbit.configuration.RabbitConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description 消息发布者
 * @date 2020/4/21 17:13
 */
@Component
public class PublisherB implements RabbitTemplate.ConfirmCallback {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 由于rabbitTemplate的scope属性设置为prototype，所以不能自动注入
    private final RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public PublisherB(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    }

    public void sendMsg(String content) {
        CorrelationData correlationId = new CorrelationData(UUIDUtils.getUUID());
        rabbitTemplate.convertAndSend(RabbitConstant.EXCHANGE_A, RabbitConstant.ROUTING_KEY_A, content, correlationId);
    }

    public void sendMsg(String exchange, String routingKey, Object content, CorrelationData correlationData) {
        rabbitTemplate.convertAndSend(exchange, routingKey, content, correlationData);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info(" 回调id:" + correlationData);
        if (ack) {
            logger.info("消息成功消费");
        } else {
            logger.info("消息消费失败:" + cause);
        }
    }

}
