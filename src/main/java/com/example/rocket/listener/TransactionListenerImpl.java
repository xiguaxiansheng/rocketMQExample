package com.example.rocket.listener;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class TransactionListenerImpl implements TransactionListener {

    private AtomicInteger checkTimes = new AtomicInteger(0);
    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String msgKey = msg.getKeys();
        try{
            if(new Random().nextInt(3) == 2){
                int a = 1 / 0;
            }else {
                System.out.println(new Date()+"====>本地事务执行成功，发送确认消息");
            }
        }catch (Exception e){
            System.out.println(new Date() + "===> 本地事务执行失败,回滚消息！");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        //不响应，调用事务回查
        localTrans.put(msgKey, 1);
        return LocalTransactionState.UNKNOW;
    }


    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        System.out.println("服务器回查事务消息："+msg.toString());
        String msgKey = msg.getKeys();
        Integer state = localTrans.get(msgKey);

        switch (state){
            case 1:
                System.out.println("check result commit 回查次数" + checkTimes.incrementAndGet());
                return LocalTransactionState.COMMIT_MESSAGE;
        }
        return LocalTransactionState.UNKNOW;
    }
}
