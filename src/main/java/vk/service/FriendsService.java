package vk.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vk.controller.exception.UserNofFoundException;
import vk.domain.Friends;
import vk.domain.User;
import vk.repos.FriendsRepository;
import vk.repos.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendsService {

    private final FriendsRepository repository;

    private final UserRepository userRepository;

    public void add(String user, String friends) throws UserNofFoundException {
        Optional<User> user1 = userRepository.findByUsername(user);
        Optional<User> user2 = userRepository.findByUsername(friends);
        if(user1.isPresent() && user2.isPresent()) {
            Friends friends1 = new Friends(user2.get(),user1.get());
            repository.save(friends1);
        }
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
