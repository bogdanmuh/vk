package vk.controller.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class ChatRequest {

    private String message ;
    private String sender ;
    private String recipient;
    private Long chatId ;
    private Date date ;

}
