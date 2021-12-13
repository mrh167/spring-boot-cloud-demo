import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.Collection;

public class ConsumerPullTest {
  
    public static void main(String[] args) {
        DefaultLitePullConsumer consumer=new DefaultLitePullConsumer ();
        consumer.setNamesrvAddr("127.0.0.1:9876");
       consumer.setConsumerGroup("rocketGroup");
        try {  
            consumer.start();  
//        Set<MessageQueue> messageQueues=  consumer.fetchSubscribeMessageQueues("PushTopic");
            Collection<MessageQueue> rocketTopic = consumer.fetchMessageQueues("rocketTopic");
            for (MessageQueue messageQueue : rocketTopic) {
                System.out.println("topic========="+messageQueue.getTopic()+"body=========");
            }
//            for(MessageQueue messageQueue:messageQueues){
//
//            System.out.println(messageQueue.getTopic());

        } catch (MQClientException mqClientException) {
            mqClientException.printStackTrace();
        }

//        //消息队列的监听
//        consumer.registerMessageQueueListener("", new MessageQueueListener() {
//
//            @Override
//            //消息队列有改变，就会触发
//            public void messageQueueChanged(String topic, Set<MessageQueue> mqAll,
//                    Set<MessageQueue> mqDivided) {
//                // TODO Auto-generated method stub
//
//            }
//        });

    }  
} 