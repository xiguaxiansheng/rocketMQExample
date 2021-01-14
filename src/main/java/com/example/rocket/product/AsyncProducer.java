package com.example.rocket.product;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * 异步消息
 */
public class AsyncProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {

        //实例化生产者
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        //设置NameServer地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        //启动
        producer.start();
        //设置重试次数
        producer.setRetryTimesWhenSendAsyncFailed(0);
        //根据消息数，实例化倒计时计数器
        int messageCount = 100;
        final CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);
        for(int i = 0 ; i < messageCount ; i++){
            final int index = i;
            //创建消息，并指定Topic， Tag 和消息体
            Message msg = new Message("TopicTest","TagA","OrderID188","Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            //SendCallback接收异步返回结果的回调
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n",index,sendResult.getMsgId());
                    countDownLatch.countDown();
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n",index,e);
                    e.printStackTrace();
                    countDownLatch.countDown();
                }
            });
        }
        //等待5S
        countDownLatch.await();
        producer.shutdown();
    }

}
