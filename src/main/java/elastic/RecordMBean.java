package elastic;

public interface RecordMBean {
    String getTopic();

    void setTopic(String topic);

    String getGroupId();

    void setGroupId(String groupId);

    long getKey();

    void setKey(long key);

    String getValue();

    void setValue(String value);

    void setOffset(long offset);

    long getOffset();

    int getPartition();

    void setPartition(int partition);
}
