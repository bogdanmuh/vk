package vk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vk.controller.pojo.ActivateMessageResponse;
import vk.controller.pojo.FindResponse;
import vk.controller.pojo.ProfileResponse;
import vk.domain.User;
import vk.pojo.MessageResponse;
import vk.pojo.SignupRequest;
import vk.repos.UserRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    public static final Map<String,String> onlineUsers = new HashMap<>();//  подумать
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private FriendsSercive friendsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MailSender mailSender;

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

    public ProfileResponse getProfileResponse(String userId) throws IOException {
        User user = userRepository.findByUsername(userId).get();

        return new ProfileResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getDate(),
                user.getUsername(),
                user.getEmail(),
                photoService.getPhoto(userId),
                user.getRoles().stream()
                        .map(data->data.getName().toString())
                        .collect(Collectors.toList()),
                friendsService.getFriends(userId),
                isOnline(user.getUsername()));
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
                UUID.randomUUID().toString(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                roleService.validationRole(signupRequest.getRoles()),
                signupRequest.getDate());

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
