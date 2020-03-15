package com.sweteamdragon.raisedhandsserver.auth.models;

public class RegisterResponseModel {

    private final long id;
    private final String content;

    public RegisterResponseModel(long id, String content) {
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
