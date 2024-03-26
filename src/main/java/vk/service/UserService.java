package vk.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vk.controller.exception.EmailIsExistException;
import vk.controller.exception.UserNofFoundException;
import vk.controller.exception.UsernameIsExistException;
import vk.controller.pojo.ActivateMessageResponse;
import vk.controller.pojo.FindResponse;
import vk.controller.pojo.ProfileResponse;
import vk.domain.User;
import vk.controller.pojo.MessageResponse;
import vk.controller.pojo.SignupRequest;
import vk.repos.UserRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private static final Map<String,String> onlineUsers = new ConcurrentHashMap<>();//TODO  подумать
    private final UserRepository userRepository;
    private final PhotoService photoService;
    private final FriendsService friendsService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final MailSender mailSender;

    public Set<User> getUsers(String[] usernames) throws UserNofFoundException  {
        Set<User> users = new HashSet<>();
        for (String username : usernames) {
            User user = getUser(username);
            users.add(user);
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

    public User getUser(String username) throws UserNofFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNofFoundException (
                    String.format(UserNofFoundException.UserWithUsernameNotFound, username)));
    }

    public User getUser(Long id) throws UserNofFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNofFoundException (
                        String.format(UserNofFoundException.UserWithIdNotFound, id)));
    }

    public ProfileResponse getProfileResponse(String userId) throws IOException, UserNofFoundException {
        User user = getUser(userId);
        return new ProfileResponse(
                getUser(userId),
                isOnline(user.getUsername()),
                friendsService.getFriends(userId),
                photoService.getPhoto(userId)
        );
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

    public MessageResponse registerUser(SignupRequest signupRequest) throws UsernameIsExistException, EmailIsExistException {
        roleService.createRole();
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
                roleService.validationRole(signupRequest.getRoles()));

        userRepository.save(user);
        mailSender.sendValidationMessage(user);
        return new MessageResponse("User CREATED");
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
