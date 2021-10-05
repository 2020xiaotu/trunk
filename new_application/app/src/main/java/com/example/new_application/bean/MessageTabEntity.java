package com.example.new_application.bean;

import java.io.Serializable;

public class MessageTabEntity implements Serializable {
    String content;
    int type;
    String messageCount;

    public String getMessageCount() {
        return messageCount;
    }

    public MessageTabEntity(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public void setMessageCount(String messageCount) {
        this.messageCount = messageCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MessageTabEntity(String content, int type, String messageCount) {
        this.content = content;
        this.type = type;
        this.messageCount = messageCount;
    }
}
