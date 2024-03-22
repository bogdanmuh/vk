package vk.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vk.controller.pojo.FindRequest;
import vk.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
@AllArgsConstructor
public class FindControler {

    private final UserService userService;

    @PostMapping("/find")
    public ResponseEntity<?> findInfo(@RequestBody FindRequest text) {
        return ResponseEntity.ok(userService.findAllUsers(text.getText()));
    }

    @GetMapping("profile/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) throws IOException {
        return ResponseEntity.ok (userService.getProfileResponse(userId));
    }

}
