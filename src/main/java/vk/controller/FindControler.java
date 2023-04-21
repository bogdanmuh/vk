package vk.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
public class FindControler {
    @Autowired
    private UserService userService;

    @PostMapping("/find")
    public ResponseEntity<?> findInfo(@RequestBody FindRequest findRequest) {
        return ResponseEntity.ok(userService.findAllUsers(findRequest.getText()));
    }
    @GetMapping("profile/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) throws IOException {
        return ResponseEntity.ok (userService.getUser(userId));
    }
}
