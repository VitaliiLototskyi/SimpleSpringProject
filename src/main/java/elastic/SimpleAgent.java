package elastic;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class SimpleAgent {

    public SimpleAgent(Message messageBean) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("kafka.message:type=Message");
        mbs.registerMBean(messageBean, objectName);

        System.out.println("Waiting forever....");
        Thread.sleep(Long.MAX_VALUE);
    }

    public SimpleAgent(Record recordBean, int counterForInstance) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("kafka.message:type=Record" + counterForInstance);
        mbs.registerMBean(recordBean, objectName);

        System.out.println("Waiting forever....");
//        Thread.sleep(Long.MAX_VALUE);
    }
}
