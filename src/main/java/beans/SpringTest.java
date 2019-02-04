package beans;

import elastic.Controller;
import elastic.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component("springTest")
public class SpringTest {
    @Autowired
    private Controller controller;

    public void generateAndSend() throws IOException {
        Message message = new Message();
        List<String> jsonList = new ArrayList<>();
        System.out.println("Generating messages....");
        for (Message m : message.generateMessages(controller.read())) {
            jsonList.add(controller.mapToJson(m));
        }
        System.out.println("Sending messages");
        for (String s : jsonList) {
            controller.toIndex("million", s);
        }
        System.out.println("Completed");

    }
}
