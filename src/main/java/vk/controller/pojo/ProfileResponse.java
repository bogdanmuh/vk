package vk.controller.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private String firstName;
    private String lastName;
    private Date date;
    private String username;
    private String email;
    private boolean isOnline;
    List<String> roles;
    List<String> friends;
    private ImageModel imageModel;

}
