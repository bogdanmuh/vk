package vk.controller.pojo;

import java.util.List;

public class ChatMessageResponse {
    private List<ChatResponse> list;
    private  boolean online;
    public ChatMessageResponse(List<ChatResponse> list, boolean online) {
        this.list = list;
        this.online = online;
    }

    public List<ChatResponse> getList() {return list;}

    public void setList(List<ChatResponse> list) {this.list = list;}

    public boolean isOnline() {return online;}

    public void setOnline(boolean online) {this.online = online;}
}
