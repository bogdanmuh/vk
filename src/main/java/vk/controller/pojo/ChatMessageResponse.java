package vk.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class ChatMessageResponse {

    private List<ChatResponse> list;
    private  boolean online;

}
