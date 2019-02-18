package elastic;

public interface MessageMBean {

    int getId();

    void setId(int id);

    String getClient_ip();

    void setClient_ip(String client_ip);

    String getSent_time();

    void setSent_time(String sent_time);

    String getUuid();

    void setUuid(String uuid);

    String getRequest_url();

    void setRequest_url(String request_url);

    String getResponse_code();

    void setResponse_code(String response_code);

    Integer getFile_size();

    void setFile_size(Integer file_size);

    String getClient_location();

    void setClient_location(String client_location);

    String getBrowser();

    void setBrowser(String browser);
}
