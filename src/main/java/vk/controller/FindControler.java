package vk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vk.controller.pojo.FindRequest;
import vk.controller.pojo.FindResponse;
import vk.controller.pojo.ProfileResponse;
import vk.domain.User;
import vk.repos.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class FindControler {
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/find")
    public ResponseEntity<?> findInfo(@RequestBody FindRequest findRequest) {
        List<String> users = userRepository.findAll().stream()
                .map(User::getUsername)
                .filter(username->username.contains(findRequest.getText()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new FindResponse(users));
    }
    @GetMapping("profile/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        User user = userRepository.findByUsername(userId).get();

        return ResponseEntity.ok(new ProfileResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getDate(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(data->data.getName().toString())
                        .collect(Collectors.toList())));
    }
}
