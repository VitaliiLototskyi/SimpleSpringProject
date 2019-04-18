
import beans.ConsumerForKafka;
import beans.ProducerForKafka;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.concurrent.ExecutionException;


public class ProducerTest {
    public static final int TIMEOUT_VALUE = 3;

    @Test(timeOut = 1000*TIMEOUT_VALUE)
    public void testProducer() throws ExecutionException, InterruptedException {
        KafkaProducer<String, String> producer = ProducerForKafka.createProducer();

        producer.send(new ProducerRecord("messages", "0", "message0")).get();
        producer.send(new ProducerRecord("messages", "1", "message1")).get();
        producer.send(new ProducerRecord("messages", "2", "message2")).get();
        producer.send(new ProducerRecord("messages", "3", "message3")).get();

        Consumer<String, String> consumer = ConsumerForKafka.createConsumer();
        consumer.subscribe(Collections.singletonList("messages"));

        boolean notPassed = true;
        while (notPassed) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(100);
            for (ConsumerRecord record : consumerRecords) {
                if (record.value().equals("message3")) {
                    notPassed = false;
                }
            }
        }


    }
}


