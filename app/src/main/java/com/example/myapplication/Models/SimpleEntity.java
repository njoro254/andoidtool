package com.example.myapplication.Models;

public class SimpleEntity {
    protected String status;
    protected String url;

    public SimpleEntity(String status,String url) {
        this.status = status;
    }

    // no-arg constructor, getters, and setters

    public String getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }
}