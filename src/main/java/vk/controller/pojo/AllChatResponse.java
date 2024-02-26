package vk.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class AllChatResponse {

    private String sender;
    private String firstName;
    private String lastName;
    private Date date;
    private String message;

}
