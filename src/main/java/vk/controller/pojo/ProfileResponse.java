package vk.controller.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import vk.domain.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
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

    public ProfileResponse(User user,
                           boolean isOnline,
                           List<String> friends,
                           ImageModel imageModel) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.date = user.getDate();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.isOnline = isOnline;
        this.roles = user.getRoles().stream()
                .map(data -> data.getName().toString())
                .collect(Collectors.toList());
        this.friends = friends;
        this.imageModel = imageModel;
    }
}
