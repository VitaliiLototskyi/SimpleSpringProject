import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RestAPIResponseTest {
    private String url = "http://localhost:8080";

    @Test
    public void RestResponseTest() throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        Response response = httpClient.newCall(request).execute();
        String actual = "";
        if (response != null) {
            actual = response.body().string();
        }
        Assert.assertEquals(actual, "Hello World");
    }

    @Test
    public void ResponseTest() throws IOException {
        HttpUriRequest request = new HttpGet(url);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(EntityUtils.toString(response.getEntity(), "UTF-8"), "Hello World");

//        HttpEntity entity = response.getEntity();
//        String resString = EntityUtils.toString(entity, "UTF-8");
//        Assert.assertEquals(resString, "Hello World");
    }
}
