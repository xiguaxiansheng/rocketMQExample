package com.example.rocket.product;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

//Sql过滤消息
public class FilterProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        //实例化生产者
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for(int i = 0 ; i < 100 ; i++){
            Message msg = new Message("TopicZjj","TagA",("Hello RocketMQ"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            msg.putUserProperty("a",String.valueOf(i));
            producer.send(msg);
        }
        producer.shutdown();
    }
}
