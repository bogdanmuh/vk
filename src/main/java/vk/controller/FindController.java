package vk.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vk.controller.pojo.FindRequest;
import vk.controller.pojo.FindResponse;
import vk.service.UserService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
@AllArgsConstructor
public class FindController {

    private final UserService userService;

    @PostMapping("/find")
    public FindResponse findInfo(@RequestBody FindRequest text) {
        return userService.findAllUsers(text.getText());
    }

}
