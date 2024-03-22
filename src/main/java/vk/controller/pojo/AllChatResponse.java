package vk.controller.pojo;

import lombok.Data;

import java.util.Date;
@Data
public class AllChatResponse {

    private String username;
    private String firstName;
    private String lastName;
    private Date date;
    private String message;
    private Long chat_id;
    private String usernameCompains;
    private String firstNameCompains;
    private String lastNameCompains;

    public AllChatResponse(String username, String firstName, String lastName, Date date, String message, Long chat_id) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.message = message;
        this.chat_id = chat_id;
    }
}
