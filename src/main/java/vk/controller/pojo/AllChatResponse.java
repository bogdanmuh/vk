package vk.controller.pojo;

import java.util.Date;

public class AllChatResponse {
    private String sender;
    private String firstName;
    private String lastName;
    private Date date;
    private String message;

    public AllChatResponse(String sender, String firstName, String lastName, Date date, String message) {
        this.sender = sender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
}
