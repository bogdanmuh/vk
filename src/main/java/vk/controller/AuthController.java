package vk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vk.pojo.LoginRequest;
import vk.pojo.SignupRequest;
import vk.service.AuthService;
import vk.service.UserService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins ="*")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {
        return authService.authUser(loginRequest);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        return userService.registerUser(signupRequest);
    }
    @GetMapping("/activate/{code}")
    public ResponseEntity<?> activate(@PathVariable String code) {
        return ResponseEntity.ok(userService.activateUser(code));
    }
}
