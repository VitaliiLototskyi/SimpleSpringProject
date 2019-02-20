package beans;

import elastic.Record;
import elastic.SimpleAgent;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class ConsumerForKafka {
    private final static String TOPIC = "my_log_topic";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";
    private final static String CONSUMER_GROUP_ID = "KafkaConsumer";

    public static Consumer<Long, String> createConsumer() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        final Consumer<Long, String> consumer = new KafkaConsumer<Long, String>(properties);

        consumer.subscribe(Collections.singletonList(TOPIC));

        return consumer;
    }

    public static void runComsumer() {
        final Consumer<Long, String> consumer = createConsumer();
        int counterForInstance = 0;
        final int giveUp = 100;
        int noRecordsCount = 0;
        Record recordMBean = new Record();
        while (true) {
            final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
            if (consumerRecords.count() == 0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) {
                    break;
                } else continue;
            }

            for (ConsumerRecord<Long, String> record : consumerRecords) {
                System.out.printf("Consumer Record:(%s, %s, %d, %d)\n",
                        record.topic(), record.value(),
                        record.partition(), record.offset());
                recordMBean.setTopic(record.topic());
                recordMBean.setValue(record.value());
                recordMBean.setOffset(record.offset());
                recordMBean.setPartition(record.partition());
                try {
                    SimpleAgent agent = new SimpleAgent(recordMBean, counterForInstance);
                    counterForInstance++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("Done");
    }
}
