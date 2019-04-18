import beans.SpringTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class SendMessages {
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException, SQLException, ClassNotFoundException {
        String confFile = "application-context.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(confFile);
        SpringTest sft = (SpringTest) context.getBean("springTest");
//        sft.generateMessageForDB();
//        sft.generateAndSendToKafka();
        sft.createIndex();
    }
}
