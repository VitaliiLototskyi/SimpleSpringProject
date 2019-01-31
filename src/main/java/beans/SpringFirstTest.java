package beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("springTest")
public class SpringFirstTest {
    public void testMe() {
        System.out.println("Hello im Spring");
    }
}
