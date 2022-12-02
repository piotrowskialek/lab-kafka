package com.example.labkafka;

import java.time.LocalDateTime;

//Message with partition information
public class MessageWithPartition {
    private Integer partition;
    private String sender;
    private LocalDateTime at;
    private String text;

    public MessageWithPartition() {
    }

    public MessageWithPartition(String sender, LocalDateTime at, String text, Integer partition) {
        this.sender = sender;
        this.at = at;
        this.text = text;
        this.partition = partition;
    }

    public String getSender() {
        return sender;
    }

    public LocalDateTime getAt() {
        return at;
    }

    public String getText() {
        return text;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setAt(LocalDateTime at) {
        this.at = at;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPartition() {
        return partition;
    }

    @Override
    public String toString() {
        return "MessageWithPartition{" +
                "sender='" + sender + '\'' +
                ", at=" + at +
                ", text='" + text + '\'' +
                ", partition='" + partition + '\'' +
                '}';
    }
}
