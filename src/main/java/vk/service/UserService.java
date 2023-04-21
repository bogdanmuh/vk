package vk.service;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
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

    public ProfileResponse getUser(String userId) throws IOException {
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
                friendsService.getFriends(userId));

    }

    public ActivateMessageResponse activateUser(String code) {
        User user = userRepository.findByActivateCode(code);

        if (user == null) {
            return new ActivateMessageResponse("Activation Code is not found");
        }
        user.setActivateCode(null);
        userRepository.save(user);
        return new ActivateMessageResponse("User successfully activated");

    }

    public MessageResponse registerUser(SignupRequest signupRequest) {
        roleService.createRole();
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return new MessageResponse("Error: Username is exist");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return new MessageResponse("Error: Email is exist");
        }
        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getDate(),
                UUID.randomUUID().toString(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                roleService.validationRole(signupRequest.getRoles()));

        userRepository.save(user);
        mailSender.sendValidationMessage(user);
        
        return new MessageResponse("User CREATED");
    }



}
