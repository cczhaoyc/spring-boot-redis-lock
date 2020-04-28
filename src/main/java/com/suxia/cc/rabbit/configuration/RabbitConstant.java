package com.suxia.cc.rabbit.configuration;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description Rabbit交换机、路由key、队列枚举
 * @date 2020/4/28 16:06
 */
public interface RabbitConstant {

    /**
     * 交换机
     */
    String EXCHANGE_A = "EXCHANGE_A";
    String EXCHANGE_B = "EXCHANGE_B";
    String EXCHANGE_C = "EXCHANGE_C";

    /**
     * 路由key
     */
    String ROUTING_KEY_ = "ROUTING_KEY_";
    String ROUTING_KEY_A = "ROUTING_KEY_A";
    String ROUTING_KEY_B = "ROUTING_KEY_B";
    String ROUTING_KEY_C = "ROUTING_KEY_C";

    /**
     * 对列
     */
    String QUEUE_A = "QUEUE_A";
    String QUEUE_B = "QUEUE_B";
    String QUEUE_C = "QUEUE_C";


}
