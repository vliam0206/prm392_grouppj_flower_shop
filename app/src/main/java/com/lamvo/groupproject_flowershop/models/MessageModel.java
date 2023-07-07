package com.lamvo.groupproject_flowershop.models;

public class MessageModel {
    private String id;
    private String message;
    private String senderId;

    public MessageModel(String id, String senderId, String message) {
        this.id = id;
        this.message = message;
        this.senderId = senderId;
    }

    public MessageModel() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
