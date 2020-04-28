package com.suxia.cc.rabbit.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description Rabbit基础配置
 * Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
 * Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。
 * Queue:消息的载体,每个消息都会被投到一个或多个队列。
 * Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
 * Routing Key:路由关键字,exchange根据这个关键字进行消息投递。
 * vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。
 * Producer:消息生产者,就是投递消息的程序.
 * Consumer:消息消费者,就是接受消息的程序.
 * Channel:消息通道,在客户端的每个连接里,可建立多个channel.
 * @date 2020/4/21 16:56
 */
@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;
    @Value("${spring.rabbitmq.publisherConfirms}")
    private Boolean enableConfirm;
    @Value("${spring.rabbitmq.publisherReturns}")
    private Boolean enableReturn;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirms(enableConfirm);
        connectionFactory.setPublisherReturns(enableReturn);
        return connectionFactory;
    }

    /**
     * 如果想要设置不同的回调类，就要设置为prototype的scope。
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(this.connectionFactory());
    }

    /**
     * 默认交换机
     * <p>
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingKey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingKey分发到指定队列
     * TopicExchange:多关键字匹配
     */
    @Bean
    public TopicExchange defaultTopicExchange() {
        return new TopicExchange(RabbitConstant.EXCHANGE_A);
    }


    /**
     * 获取队列A
     */
    @Bean
    public Queue queueA() {
        return new Queue(RabbitConstant.QUEUE_A);
    }

    /**
     * 获取队列B
     */
    @Bean
    public Queue queueB() {
        return new Queue(RabbitConstant.QUEUE_B);
    }


    @Bean
    public Binding bindingA() {
        return BindingBuilder.bind(this.queueA()).to(defaultTopicExchange()).with(RabbitConstant.ROUTING_KEY_A);
    }

    @Bean
    public Binding bindingB() {
        return BindingBuilder.bind(this.queueB()).to(defaultTopicExchange()).with(RabbitConstant.ROUTING_KEY_B);
    }

    /**
     * 将routingKey绑定到指定交换机
     *
     * @param queueName    队列名称
     * @param exchangeName 交换机名称
     * @param exchangeType 交换机类型
     * @param routingKey   路由key
     */
    public Binding createBinding(String queueName, String exchangeName, String exchangeType, String routingKey) {
        if (ExchangeTypes.DIRECT.equals(exchangeType)) {
            DirectExchange directExchange = (DirectExchange) createExchange(exchangeName, exchangeType);
            return BindingBuilder.bind(this.createQueue(queueName)).to(directExchange).with(routingKey);
        }
        if (ExchangeTypes.TOPIC.equals(exchangeType)) {
            TopicExchange topicExchange = (TopicExchange) createExchange(exchangeName, exchangeType);
            return BindingBuilder.bind(this.createQueue(queueName)).to(topicExchange).with(routingKey);
        }
        return null;
    }


    /**
     * 创建队列
     *
     * @param queueName 队列名称
     */
    public Queue createQueue(String queueName) {
        return new Queue(queueName);
    }

    /**
     * 创建交换机
     *
     * @param exchangeName 交换机名称
     * @param exchangeType 交换机类型
     * @return 交换机
     */
    public Exchange createExchange(String exchangeName, String exchangeType) {
        Exchange exchange;
        if (ExchangeTypes.DIRECT.equals(exchangeType)) {
            exchange = new DirectExchange(exchangeName, true, false);
        } else if (ExchangeTypes.TOPIC.equals(exchangeType)) {
            exchange = new TopicExchange(exchangeName, true, false);
        } else if (ExchangeTypes.FANOUT.equals(exchangeType)) {
            exchange = new FanoutExchange(exchangeName, true, false);
        } else if (ExchangeTypes.HEADERS.equals(exchangeType)) {
            exchange = new HeadersExchange(exchangeName, true, false);
        } else {
            exchange = new CustomExchange(exchangeName, exchangeType, true, false);
        }
        return exchange;
    }

}
