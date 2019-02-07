package beans;

import elastic.Controller;
import elastic.Message;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component("springTest")
public class SpringTest {
    @Autowired
    private Controller controller;
    private static int counter = 0;

    @SuppressWarnings("unchecked")
    public void generateAndSendToKafka() throws IOException, ExecutionException, InterruptedException {
        Message message = new Message();
        KafkaProducer<String, String> producer = ProducerForKafka.createProducer();
        System.out.println("Generating " + controller.read() + " messages");
        for (Message m : message.generateMessages(controller.read())) {
            producer.send(new ProducerRecord("my_log_topic", controller.mapToJson(m))).get();
            counter++;
            if (counter % 10000 == 0) {
                System.out.println("Sended " + counter + " messages");
            }
        }
        System.out.println("Completed");
        producer.close();
    }

}
