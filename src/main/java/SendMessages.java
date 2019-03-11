import beans.SpringTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class SendMessages {
    public static void main(String[] args) throws SQLException, InterruptedException, ExecutionException, IOException, MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, ClassNotFoundException {
        String confFile = "application-context.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(confFile);
        SpringTest sft = (SpringTest) context.getBean("springTest");
        sft.generateAndSendToKafka();
    }
}
