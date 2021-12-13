import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;

public class ConsumerTest {
  
    public static void main(String[] args) {  
        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("rocketGroup");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        try {  
              
            // 订阅PushTopic下Tag为push的消息,都订阅消息  
            consumer.subscribe("CicadaTopic", "rocketTag");
              
            // 程序第一次启动从消息队列头获取数据  
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            //可以修改每次消费消息的数量，默认设置是每次消费一条  
            // consumer.setConsumeMessageBatchMaxSize(10);  
  
            //注册消费的监听  
            //在此监听中消费信息，并返回消费的状态信息
            consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {

                 // msgs中只收集同一个topic，同一个tag，并且key相同的message
                 // 会把不同的消息分别放置到不同的队列中
                 for(Message msg:msgs){

                     System.out.println(new String(msg.getBody()));
                     System.out.println("topic===="+msg.getTopic());
                 }
                 return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
             });
  
            consumer.start();
            System.out.println("mq测试消费者成功!!!");
            Thread.sleep(5000);  
            //5秒后挂载消费端消费  
            consumer.suspend();  
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}