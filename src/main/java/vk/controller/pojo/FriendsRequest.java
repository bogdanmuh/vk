package vk.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendsRequest {

    private String username;
    private String friend;

}
