package elastic;

import jdk.nashorn.api.scripting.JSObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Controller {
    public static void main(String[] args) throws IOException {
        Message message = new Message();

        for (Message msg : message.generateMessages(loadFromProperties())) {
            System.out.println(msg.toString());
        }
    }

    public static int loadFromProperties() throws IOException {
        Properties properties = new Properties();
        String file = "application.properties";
        InputStream in = Controller.class.getClassLoader().getResourceAsStream(file);
        if (in == null) {
            System.out.println("cant find " + file);
        }
        properties.load(in);

        return Integer.parseInt(properties.getProperty("number.of.messages"));
    }

    public static Message mapToObject(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(json, Message.class);
    }

    public static String mapToJson(Message message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(message);
    }

    public static IndexResponse toIndex(String indexName, String json) throws IOException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        IndexResponse response = client.prepareIndex(indexName, "event")
                .setSource(json, XContentType.JSON).get();
        client.close();

        return response;
    }

    public static void toDeleteList(String indexName, String searchByField, String searchByValue) throws UnknownHostException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery(searchByField, searchByValue))
                .source(indexName)
                .get();
        long deleted = response.getDeleted();
        System.out.println(response.toString() + " Deleted " + deleted + " documents");
        client.close();
    }

    public static long CountIndexes(String indexName) throws UnknownHostException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        SearchResponse sr = client.prepareSearch(indexName).get();
        SearchHits hits = sr.getHits();
        client.close();

        return hits.getTotalHits();
    }
}
