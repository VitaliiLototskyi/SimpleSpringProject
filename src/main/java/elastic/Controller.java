package elastic;

import beans.PropertyReader;
import jdk.nashorn.internal.objects.annotations.Property;
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
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import spring.demo.SpringDemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Controller {
    @Autowired
    private PropertyReader propertyReader;

    public int read() {
        String s = propertyReader.getValue();
        return Integer.parseInt(s);
    }

    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
        System.out.println(controller.read());

//        List<String> jsonList = new ArrayList<>();
//        List<Message> msgList = new ArrayList<>();
//        for (Message msg : message.generateMessages()) {
//            jsonList.add(Controller.mapToJson(msg));
//        }
//        System.out.println(jsonList.toString());
//
//        for (String s : jsonList) {
//            msgList.add(Controller.mapToObject(s));
//        }
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
