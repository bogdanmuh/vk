package vk.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vk.controller.exception.UserNofFoundException;
import vk.controller.pojo.FriendsRequest;
import vk.service.FriendsService;
import vk.service.PhotoService;
import vk.service.UserService;

import java.io.IOException;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "profile")
@AllArgsConstructor
public class ProfileController {

    private final PhotoService photoService;
    private final FriendsService friendsService;
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) throws IOException, UserNofFoundException {
        return ResponseEntity.ok (userService.getProfileResponse(userId));
    }

    @PostMapping("/upload")
    public BodyBuilder uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        photoService.savePhoto(file);
        return ResponseEntity.status(HttpStatus.OK);
    }

    @PostMapping("/friend")
    public BodyBuilder addFriends(@RequestBody FriendsRequest request) throws UserNofFoundException {
        friendsService.add(request.getUsername(),request.getFriend());
        return ResponseEntity.status(HttpStatus.OK);
    }

}
