package vk.controller.pojo;

public class FriendsRequest {
    private String username;
    private String friend;

    public FriendsRequest(String username, String friend) {
        this.username = username;
        this.friend = friend;
    }
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getFriend() {return friend;}
    public void setFriend(String friend) {this.friend = friend;}
}
