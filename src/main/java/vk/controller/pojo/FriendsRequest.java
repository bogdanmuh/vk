package vk.controller.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FriendsRequest {

    private String username;
    private String friend;

}
