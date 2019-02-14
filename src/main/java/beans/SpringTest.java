package beans;

import elastic.Controller;
import elastic.Message;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

@Component("springTest")
public class SpringTest {
    @Autowired
    private Controller controller;
    private static int counter = 0;
    private Connection connection;

    public SpringTest() throws SQLException, ClassNotFoundException {
        this.connection = new DBConnection().getConnection();
    }

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

    public void createIndex() throws IOException {
        Message message = new Message();
        for (Message m : message.generateMessages(1)) {
            controller.toIndex("kafkaesindex", controller.mapToJson(m));
        }
        System.out.println("Completed");

        System.out.println(controller.CountIndexes("kafkaesindex"));
    }

    public void generateMessageForDB() throws SQLException {
        Message message = new Message();
        for (Message m : message.generateMessages(3)) {
            saveToPostgresDB(m);
        }
        System.out.println("Success");
    }
    
    public void saveToPostgresDB(Message message) throws SQLException {
        String query = "insert into message(id,client_ip, sent_time, uuid, request_url, response_code, file_size," +
                " client_location, browser)values(?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, message.getId());
        ps.setString(2, message.getClient_ip());
        ps.setString(3, message.getSent_time());
        ps.setString(4, message.getUuid());
        ps.setString(5, message.getRequest_url());
        ps.setString(6, message.getResponse_code());
        ps.setInt(7, message.getFile_size());
        ps.setString(8, message.getClient_location());
        ps.setString(9, message.getBrowser());
        ps.executeUpdate();
    }

}
