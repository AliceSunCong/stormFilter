package cn.scc.storm.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * 消费Kafka中的数据
 */
public class kafkaConsumer {

    public static Properties getProperties() {
        Properties props = new Properties();
        /** broker集群地址 ip:端口*/
        props.put("bootstrap.servers", "ip:端口,ip:端口,ip:端口");
        /** 消费组名称 testout/testvideo*/
        props.put("group.id", "testvideo");
        /** 设置是否自动提交 */
        props.put("enable.auto.commit", "true");
        /** 设置自动提交时间 */
        props.put("auto.commit.interval.ms", "1000");
        /** key的序列化类 */
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        /** key的序列化类 */
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        /**
         * 当前consumer所在的group获取上次读到的offset不存在将执行的动作，earliest:设置offset到0 latest:
         * 设置offset到最后, none:抛出异常
         */
        props.put("auto.offset.reset", "earliest");
        return props;
    }

    public static void main(String[] args) {
        //1.创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getProperties());
        //2.订阅topic/testout/testvideo
        consumer.subscribe(Arrays.asList("testnetwork"));
        //3.轮询
        while(true){
            //在1000ms内等待Kafka的broker返回数据
            ConsumerRecords<String, String> poll = consumer.poll(1000);
            boolean empty = poll.isEmpty();
            if(empty){
                return;
            }
            for(ConsumerRecord<String, String> record:poll){
                System.out.println(record.key()+":"+record.value());
            }
        }
    }
}
