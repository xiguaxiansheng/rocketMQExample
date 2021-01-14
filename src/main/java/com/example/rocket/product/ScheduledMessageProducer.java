package com.example.rocket.product;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * 延时消息
 */
public class ScheduledMessageProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        //实例化一个生产者
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        //设置NameServer地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        //启动
        producer.start();
        int totalMessageToSend = 100;
        for(int i = 0 ; i < totalMessageToSend; i++){
            Message message = new Message("TestTopic",("Hello scheduled message"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //设置延时等级3,10之后发送（现在只支持几个时间，看delayTimeLevel: 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h）
            message.setDelayTimeLevel(3);
            producer.send(message);
        }
        //关闭生产者
        producer.shutdown();
    }
}
