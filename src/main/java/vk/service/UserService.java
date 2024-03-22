package vk.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vk.controller.pojo.ActivateMessageResponse;
import vk.controller.pojo.FindResponse;
import vk.controller.pojo.ProfileResponse;
import vk.domain.Chats;
import vk.domain.User;
import vk.pojo.MessageResponse;
import vk.pojo.SignupRequest;
import vk.repos.UserRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private static final Map<String,String> onlineUsers = new ConcurrentHashMap<>();//  подумать
    private final UserRepository userRepository;
    private final PhotoService photoService;
    private final FriendsSercive friendsService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final MailSender mailSender;

    public Set<User> getUsers(String[] usernames) {
        Set<User> users = new HashSet<>();
        for (String username : usernames) {
            Optional<User> userData = getUser(username);
            if (userData.isPresent()){
                users.add(userData.get());
            } else {
                System.out.println("user с id -" + username + " не найден");
            }
        }
        return users;
    }

    public FindResponse findAllUsers(String text) {
        List<String> users = userRepository.findAll().stream()
                .map(User::getUsername)
                .filter(username->username.contains(text))
                .collect(Collectors.toList());
        return new FindResponse(users);
    }

    public Optional<User> getUser(String userId) {
        return userRepository.findByUsername(userId);
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public ProfileResponse getProfileResponse(String userId) throws IOException {
        Optional<User> userData = userRepository.findByUsername(userId);
        if (userData.isEmpty()){
            System.out.println("user с id -" + userId + " не найден");
        } else {
            return new ProfileResponse(
                    userData.get().getFirstName(),
                    userData.get().getLastName(),
                    userData.get().getDate(),
                    userData.get().getUsername(),
                    userData.get().getEmail(),
                    isOnline(userData.get().getUsername()),
                    userData.get().getRoles().stream()
                            .map(data->data.getName().toString())
                            .collect(Collectors.toList()),
                    friendsService.getFriends(userId),
                    photoService.getPhoto(userId)
            );
        } return null;// throw Exception
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

    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {
        roleService.createRole();
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is exist"));

        }
        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getDate(),
                null,//TODO временной решение, разобраться почему перестал работать spring mail
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getDate(),
                roleService.validationRole(signupRequest.getRoles()));

        userRepository.save(user);
        mailSender.sendValidationMessage(user);
        
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
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
