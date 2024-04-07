package vk.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vk.controller.exception.EmailIsExistException;
import vk.controller.exception.UserNofFoundException;
import vk.controller.exception.UsernameIsExistException;
import vk.controller.pojo.ActivateMessageResponse;
import vk.controller.pojo.SignupRequest;
import vk.domain.Role;
import vk.domain.User;
import vk.domain.dto.UserDto;
import vk.repos.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class UserService {

    private static final Map<String,String> onlineUsers = new ConcurrentHashMap<>();//TODO  подумать
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public HashSet<User> getUsers(String[] usernames) throws UserNofFoundException  {
        HashSet<User> users = new HashSet<>();
        for (String username : usernames) {
            User user = getUser(username);
            users.add(user);
        }
        return users;
    }

    public List<UserDto> findAllUsers(String text) {
        return userRepository.findByText(text);
    }

    public User getUser(String username) throws UserNofFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNofFoundException (
                    String.format(UserNofFoundException.UserWithUsernameNotFound, username)));
    }
    public Optional<User> getUserOptional(String username)  {
        return userRepository.findByUsername(username);
    }

    public User getUser(Long id) throws UserNofFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNofFoundException (
                        String.format(UserNofFoundException.UserWithIdNotFound, id)));
    }


    public ActivateMessageResponse activateUser(String code) {
        Optional<User> user = userRepository.findByActivateCode(code);

        if (user.isEmpty()) {
            return new ActivateMessageResponse("Activation Code is not found");
        }
        user.get().setActivateCode(null);
        userRepository.save(user.get());
        return new ActivateMessageResponse("User successfully activated");
    }

    public void editLastOnline(String sessionId) {
        Optional<User> option= userRepository.findByUsername(removeOnlineUser(sessionId));
        if (option.isPresent()){
            User user = option.get();
            user.setLastOnline(new Date());
            userRepository.save(user);
        }
    }

    public User registerUser(SignupRequest signupRequest,Set<Role> roles) throws UsernameIsExistException, EmailIsExistException {
        if (userRepository.existsByUsername(signupRequest.getUsername()))
            throw new UsernameIsExistException(
                    String.format(UsernameIsExistException.usernameIsExist, signupRequest.getUsername()));
        if (userRepository.existsByEmail(signupRequest.getEmail()))
            throw new EmailIsExistException(
                    String.format(EmailIsExistException.emailIsExist,signupRequest.getEmail()));

        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getDate(),
                null,//TODO временной решение, разобраться почему перестал работать spring mail
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getDate(),
                roles);

        userRepository.save(user);
        return user;
    }

    public void addUser(String sessionId, String username){
        onlineUsers.put(sessionId,username);
    }

    public boolean isOnline(String username){
        return onlineUsers.containsValue(username);
    }

    public String removeOnlineUser(String sessionId){
        return onlineUsers.remove(sessionId);
    }

}
