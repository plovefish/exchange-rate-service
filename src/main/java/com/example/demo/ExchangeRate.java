package com.example.demo;

public class ExchangeRate {

    private final long id;
    private final String content;

    public ExchangeRate(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}