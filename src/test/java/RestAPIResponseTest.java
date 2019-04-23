import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.testng.Assert;

import java.io.IOException;

public class RestAPIResponseTest {
    private String url = "http://localhost:8080";

    @Test
    public void ResponseTest() throws IOException {
        HttpUriRequest request = new HttpGet(url);
        HttpResponse response;
        response = HttpClientBuilder.create().build().execute(request);

//        HttpEntity entity = response.getEntity();
//        String resString = EntityUtils.toString(entity,"UTF-8");
//        Assert.assertEquals(resString, "Hello World");

        Assert.assertEquals(EntityUtils.toString(response.getEntity(), "UTF-8"), "Hello World");
    }
}
