package vk.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class ChatRequest {

    private String message ;
    private String from ;
    private String to ;
    private Date date ;

}
