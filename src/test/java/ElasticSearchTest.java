import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.get.GetField;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.testng.Assert;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class ElasticSearchTest {
    public static final String INDEX_NAME = "test10";
    public static final String INDEX_TYPE = "test";
    public static final String INDEX_ID = "1";
    public static final String CLUSTER_NAME = "KafkaESCluster";

    @Test
    public void indexTest() throws IOException {
        Settings settings = Settings.builder()
                .put("cluster.name", CLUSTER_NAME).build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("name","indexName");
            builder.field("age", "123");
        }
        builder.endObject();

        IndexRequest indexRequest =  new IndexRequest(INDEX_NAME, INDEX_TYPE, INDEX_ID).source(builder);

        MultiGetResponse response = client.prepareMultiGet()
                .add(INDEX_NAME, INDEX_TYPE, INDEX_ID).get();
        boolean exist = false;
        for (MultiGetItemResponse r: response) {
            GetResponse getResponse = r.getResponse();
            if (getResponse.isExists() && getResponse.getSource().values().contains("indexName")) {
                exist = true;
            }
        }
        Assert.assertTrue(exist);
    }
}
