package spring.demo;

import beans.PropertyReader;
import beans.SpringFirstTest;
import elastic.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringDemo {


    public static void main(String[] args) {
        String confFile = "application-context.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(confFile);
        SpringFirstTest sft = (SpringFirstTest) context.getBean("springTest");
        PropertyReader propertiesReader = (PropertyReader) context.getBean("propertyReader");
        System.out.println(propertiesReader.getValue());

        sft.testMe();
    }
}
