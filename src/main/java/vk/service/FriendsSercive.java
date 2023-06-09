package vk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vk.domain.Friends;
import vk.domain.User;
import vk.repos.FriendsRepository;
import vk.repos.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendsSercive {
    @Autowired
    FriendsRepository repository;
    @Autowired
    UserRepository userRepository;

    public void add(String user, String friends){
        Optional<User> user1 = userRepository.findByUsername(user);
        Optional<User> user2 = userRepository.findByUsername(friends);
        if(user1.isPresent() && user2.isPresent()) {
            Friends friends1 = new Friends(user2.get(),user1.get());
            repository.save(friends1);
        }
    }

    public List<String> getFriends(String user){
        List<Friends> friends =  repository.getFriends(user);
        return  friends.stream()
                .map(x->{
                        if(!user.equals(x.getFriend_one().getUsername())) return  x.getFriend_one().getUsername();
                        if(!user.equals(x.getFriend_two().getUsername())) return  x.getFriend_two().getUsername();
                        return "";})
                .collect(Collectors.toList());
    }

}
