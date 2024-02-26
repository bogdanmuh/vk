package vk.pojo;

import lombok.Data;

import java.util.Date;
import java.util.Set;
@Data
public class SignupRequest {

    private String firstName;
    private String lastName;
    private Date date;
    private String username;
    private String email;
    private Set<String> roles;
    private String password;

}