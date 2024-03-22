package vk.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponse {

    private List<ChatResponse> list;
    private  boolean online;
    private  Long chatId;

}
