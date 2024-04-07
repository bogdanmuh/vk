package vk.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vk.domain.Friends;
import vk.domain.User;
import vk.repos.FriendsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendsService {

    private final FriendsRepository repository;

    public void add(User user, User friends) {
        Friends friends1 = new Friends(user,friends);
        repository.save(friends1);
    }

    public List<String> getFriends(String user){
        List<Friends> friends =  repository.getFriends(user);
        return friends.stream()
                .map(x->{
                        if(!user.equals(x.getFriend_one().getUsername())) return  x.getFriend_one().getUsername();
                        if(!user.equals(x.getFriend_two().getUsername())) return  x.getFriend_two().getUsername();
                        return "";})
                .collect(Collectors.toList());
    }

}
