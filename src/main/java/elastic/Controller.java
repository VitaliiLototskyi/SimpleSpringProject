package elastic;

import beans.PropertyReader;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Controller {
    @Autowired
    private PropertyReader propertyReader;

    public int read() {
        String s = propertyReader.getValue();

        return Integer.parseInt(s);
    }

    public Message mapToObject(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(json, Message.class);
    }

    public String mapToJson(Message message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(message);
    }

    public IndexResponse toIndex(String indexName, String json) throws IOException {
        Settings settings = Settings.builder()
                .put("cluster.name", "KafkaESCluster")
                .put("xpack.security.user", "transport_client_user:changeme")
                .build();

        TransportClient client = new PreBuiltXPackTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        IndexResponse response = client.prepareIndex(indexName, "event")
                .setSource(json, XContentType.JSON).get();
        client.close();

        return response;
    }

    public void toDeleteList(String indexName, String searchByField, String searchByValue) throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "KafkaESCluster")
                .put("xpack.security.user", "transport_client_user:changeme")
                .build();

        TransportClient client = new PreBuiltXPackTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery(searchByField, searchByValue))
                .source(indexName)
                .get();
        long deleted = response.getDeleted();
        System.out.println(response.toString() + " Deleted " + deleted + " documents");
        client.close();
    }

    public long CountIndexes(String indexName) throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "KafkaESCluster")
                .put("xpack.security.user", "transport_client_user:changeme")
                .build();

        TransportClient client = new PreBuiltXPackTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        SearchResponse sr = client.prepareSearch(indexName).get();
        SearchHits hits = sr.getHits();
        client.close();

        return hits.getTotalHits();
    }
}

