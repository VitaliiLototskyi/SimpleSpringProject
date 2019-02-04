package spring.demo;

import beans.SpringTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;


public class SpringDemo {

    public static void main(String[] args) throws IOException {
        String confFile = "application-context.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(confFile);
        SpringTest sft = (SpringTest) context.getBean("springTest");
        sft.generateAndSend();
    }
}
