package com.example.rocket.product;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

public class OnewayProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        //实例化生产者
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        //设置NameServer地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        //启动
        producer.start();

        for (int i = 0 ; i < 100 ; i++){
            Message msg = new Message("TopicTest","TagA",("Hello RocketMQ"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送单向消息，没有任何返回
            producer.sendOneway(msg);
        }
        producer.shutdown();
    }

}
