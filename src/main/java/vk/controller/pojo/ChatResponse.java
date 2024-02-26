package vk.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class ChatResponse {

    private String from;
    private String to;
    private Date date;
    private String message;
    private String event;

}
