package vk.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vk.controller.exception.EmailIsExistException;
import vk.controller.exception.NotActivateAccountException;
import vk.controller.exception.UsernameIsExistException;
import vk.controller.pojo.ActivateMessageResponse;
import vk.controller.pojo.JwtResponse;
import vk.controller.pojo.LoginRequest;
import vk.controller.pojo.MessageResponse;
import vk.controller.pojo.SignupRequest;
import vk.service.AuthService;
import vk.service.UserService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins ="*")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signin")
    public JwtResponse authUser(@RequestBody LoginRequest loginRequest) throws NotActivateAccountException {
        return authService.authUser(loginRequest);
    }

    @PostMapping("/signup")
    public MessageResponse registerUser(@RequestBody SignupRequest signupRequest) throws EmailIsExistException, UsernameIsExistException {
        return userService.registerUser(signupRequest);
    }

    @GetMapping("/activate/{code}")
    public ActivateMessageResponse activate(@PathVariable String code) {
        return userService.activateUser(code);
    }

}
