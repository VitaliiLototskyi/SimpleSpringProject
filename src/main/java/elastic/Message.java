package elastic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Message {
    private int id;
    private String client_ip;
    private String sent_time;
    private String uuid;
    private String request_url;
    private String response_code;
    private Integer file_size;
    private String client_location;
    private String browser;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getSent_time() {
        return sent_time;
    }

    public void setSent_time(String sent_time) {
        this.sent_time = sent_time;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRequest_url() {
        return request_url;
    }

    public void setRequest_url(String request_url) {
        this.request_url = request_url;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public Integer getFile_size() {
        return file_size;
    }

    public void setFile_size(Integer file_size) {
        this.file_size = file_size;
    }

    public String getClient_location() {
        return client_location;
    }

    public void setClient_location(String client_location) {
        this.client_location = client_location;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public List<Message> generateMessages(int numberOfMessage) {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < numberOfMessage; i++) {
            Message message = new Message();
            message.setBrowser(this.clientBrowser());
            message.setId((int) (Math.random() * 1000000));
            message.setClient_ip(randomIP());
            message.setSent_time(new Date().toString());
            message.setUuid(String.valueOf(UUID.randomUUID()));
            message.setRequest_url("elasticSearch" + message.getId());
            message.setResponse_code("OK");
            message.setFile_size((int) (Math.random() * 10));
            message.setClient_location(clientLocation());
            messages.add(message);
        }

        return messages;
    }


    public String clientLocation() {
        List<String> countries = new ArrayList<>();
        countries.add("New York");
        countries.add("Los Angeles");
        countries.add("Chicago");
        countries.add("Houston");
        countries.add("Phoenix");
        countries.add("Philadelphia");
        countries.add("San Antonio");
        countries.add("San Diego");
        countries.add("Dallas");
        countries.add("San Jose");
        countries.add("Austin");
        countries.add("San Francisco");
        countries.add("Seattle");
        countries.add("Denver");
        countries.add("Detroit");
        double tmp = Math.random() * 14;

        return countries.get((int) tmp);
    }

    public String clientBrowser() {
        List<String> browsers = new ArrayList<>();
        browsers.add("Safari");
        browsers.add("Opera");
        browsers.add("Chrome");
        browsers.add("IE");
        browsers.add("FireFox");
        double tmp = Math.random() * 4;

        return browsers.get((int) tmp);
    }

    public String randomIP() {
        double tmp;
        String firstNumber = "";
        String secondNumber = "";
        String thirdNumber = "";
        String fourthNumber = "";
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                tmp = Math.random() * 255;
                firstNumber = Integer.toString((int) tmp);
            } else if (i == 1) {
                tmp = Math.random() * 255;
                secondNumber = Integer.toString((int) tmp);
            } else if (i == 2) {
                tmp = Math.random() * 255;
                thirdNumber = Integer.toString((int) tmp);
            } else {
                tmp = Math.random() * 255;
                fourthNumber = Integer.toString((int) tmp);
            }
        }
        String IP = firstNumber + "." + secondNumber + "." + thirdNumber + "." + fourthNumber;

        return IP;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", client_ip='" + client_ip + '\'' +
                ", sent_time=" + sent_time +
                ", uuid='" + uuid + '\'' +
                ", request_url='" + request_url + '\'' +
                ", response_code='" + response_code + '\'' +
                ", file_size=" + file_size +
                ", client_location='" + client_location + '\'' +
                ", browser='" + browser + '\'' +
                '}';
    }
}
