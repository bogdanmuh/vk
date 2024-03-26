package vk.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vk.domain.User;
import vk.repos.UserRepository;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class GreetingController {

    private final UserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/all")
    public String allAccess() {
        return "public API";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public String userAccess() {
        return "user API";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "moderator API";
    }

    @GetMapping("/admin")
    public String adminAccess() {
        return "admin API";
    }

}
