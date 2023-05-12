package vk.controller.pojo;

import java.util.Date;

public class ChatResponse {

    private String sender;

    private String recipient;
    private Date date;
    private String message;
    private String event;



    public ChatResponse(String sender, String recipient, Date date, String message, String event) {
        this.sender = sender;
        this.recipient = recipient;
        this.date = date;
        this.message = message;
        this.event = event;
    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getEvent() {return event;}
    public void setEvent(String event) {this.event = event;}
}
