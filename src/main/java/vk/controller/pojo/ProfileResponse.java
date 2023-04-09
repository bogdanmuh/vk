package vk.controller.pojo;

import java.util.Date;
import java.util.List;

public class ProfileResponse {
    private String firstName;
    private String lastName;
    private Date date;
    private String username;
    private String email;
    List<String> roles;
    private ImageModel imageModel;


    public ProfileResponse(String firstName, String lastName, Date date,
                           String username, String email,ImageModel imageModel, List<String> roles ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.username = username;
        this.email = email;
        this.imageModel = imageModel;
        this.roles = roles;
    }
    public ImageModel getImageModel() {
        return imageModel;
    }

    public void setImageModel(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public  List<String> getRoles() {
        return roles;
    }

    public void setRoles( List<String> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
