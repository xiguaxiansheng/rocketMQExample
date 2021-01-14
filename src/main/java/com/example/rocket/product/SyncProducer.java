package com.example.rocket.product;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * 同步消息
 */
public class SyncProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        //实例化生产者
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        //设置NameServer地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        //启动
        producer.start();
        for(int i = 0 ; i < 100 ; i++){
            Message msg = new Message("TopicTest","TagA",("Hello RocketMQ"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            //返回消息是否成功送达
            System.out.printf("%s%n",sendResult);
        }
        //关闭实例
        producer.shutdown();
    }

}
