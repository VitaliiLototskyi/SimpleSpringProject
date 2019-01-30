package elastic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Message {
    private int id;
    private String clientIP;
    private Date sentTime;
    private String uuid;
    private String requestURL;
    private String responseCode;
    private Integer fileSize;
    private String client_location;
    private String browser;

    public Message() {
    }

    public List<Message> generateMessages(int numberOfMessage) {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < numberOfMessage; i++) {
            Message message = new Message();
            message.setBrowser(this.clientBrowser());
            message.setId((int) (Math.random() * 1000000));
            message.setClientIP(randomIP());
            message.setSentTime(new Date());
            message.setUuid(String.valueOf(UUID.randomUUID()));
            message.setRequestURL("elasticSearch" + message.getId());
            message.setResponseCode("OK");
            message.setFileSize((int) (Math.random() * 10));
            message.setClient_location(clientLocation());
            messages.add(message);
        }

        return messages;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", clientIP='" + clientIP + '\'' +
                ", sentTime=" + sentTime +
                ", uuid='" + uuid + '\'' +
                ", requestURL='" + requestURL + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", fileSize=" + fileSize +
                ", client_location='" + client_location + '\'' +
                ", browser='" + browser + '\'' +
                '}';
    }

    public Message(int id, String clientIP, Date sentTime, String uuid, String requestURL, String responseCode, Integer fileSize, String client_location, String browser) {
        this.id = id;
        this.clientIP = clientIP;
        this.sentTime = sentTime;
        this.uuid = uuid;
        this.requestURL = requestURL;
        this.responseCode = responseCode;
        this.fileSize = fileSize;
        this.client_location = client_location;
        this.browser = browser;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requsetURL) {
        this.requestURL = requsetURL;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String clientLocation() {
        List<String> countries = new ArrayList<>();
        countries.add("New York");
        countries.add("Los Angeles");
        countries.add("Chicago");
        countries.add("Houston");
        countries.add("Phoenix");
        countries.add("Philadelphia");
        countries.add("SaN Antonio");
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
}
