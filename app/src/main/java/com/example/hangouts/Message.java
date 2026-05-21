package com.example.hangouts;

public class Message {

    private int id;
    private int contactId;
    private String msg;
    private int isSent;
    private String timestamp;

    public Message(int id, int contactId, String msg, int isSent, String timestamp) {
        this.id = id;
        this.contactId = contactId;
        this.msg = msg;
        this.isSent = isSent;
        this.timestamp = timestamp;
    }

    // getters, setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getContactId() { return contactId; }
    public void setContactId(int contactId) { this.contactId = contactId; }

    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }

    public int getIsSent() { return isSent; }
    public void setIsSent(int isSent) { this.isSent = isSent; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
