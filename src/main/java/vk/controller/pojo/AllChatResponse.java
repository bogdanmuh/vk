package vk.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class AllChatResponse {

    private String username;
    private String firstName;
    private String lastName;
    private Date date;
    private String message;
    private Long chat_id;
    private String usernameCompanion;
    private String firstNameCompanion;
    private String lastNameCompanion;
    private String chatName;

}
