package spring.demo;

import beans.SpringFirstTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringDemo {
    public static void main(String[] args) {
        String confFile = "application-context.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(confFile);
        SpringFirstTest sft = (SpringFirstTest) context.getBean("springTest");
        sft.testMe();
    }
}
