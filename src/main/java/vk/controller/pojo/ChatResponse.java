package vk.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    private String from;
    private Date date;
    private String message;
    private String event = "";

    public ChatResponse(String from, Date date, String message) {
        this.from = from;
        this.date = date;
        this.message = message;
    }
}
