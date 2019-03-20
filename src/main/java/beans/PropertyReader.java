package beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("propertyReader")
public class PropertyReader {
    @Value("${number.of.messages}")
    private String value;

    public String getValue() {
        return value;
    }
}
