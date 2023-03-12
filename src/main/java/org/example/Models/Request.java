package org.example.Models;

import org.example.Enums.RequestType;

import java.time.LocalDateTime;

public class Request {
    private String text;
    private LocalDateTime date;
    private Integer priority;
    private RequestType type;

    public Request(String text, Integer priority, RequestType requestType, LocalDateTime localDateTime) {
        this.text = text;
        this.date = localDateTime;
        this.priority = priority;
        this.type = requestType;
    }

    public String getText() {
        return text;
    }

    public Request setText(String text) {
        this.text = text;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Request setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public Request setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public RequestType getType() {
        return type;
    }

    public Request setType(RequestType type) {
        this.type = type;
        return this;
    }
}
