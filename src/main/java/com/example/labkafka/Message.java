package com.example.labkafka;

import java.time.LocalDateTime;

public class Message {
    private String sender;
    private LocalDateTime at;
    private String text;

    public Message() {
    }

    public Message(String sender, LocalDateTime at, String text) {
        this.sender = sender;
        this.at = at;
        this.text = text;
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

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", at=" + at +
                ", text='" + text + '\'' +
                '}';
    }
}
