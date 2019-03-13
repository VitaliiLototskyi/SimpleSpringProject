package beans;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class ProducerForKafka {
    public static KafkaProducer<String, String> createProducer() {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", "kafka:9092");// kafka:9092 to run in docker, otherwise localhost:9092
        prop.put("batch.size", "16384");
        prop.put("buffer.memory", 10000000);
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer<>(prop);
    }
}