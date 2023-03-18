package vk.controller.pojo;

import java.util.ArrayList;
import java.util.List;

public class FindResponse {

    private List<String> users = new ArrayList<>();
    public FindResponse(List<String> users){
        this.users = users;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
