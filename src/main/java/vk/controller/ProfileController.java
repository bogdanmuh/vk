package vk.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vk.controller.pojo.FriendsRequest;
import vk.service.FriendsSercive;
import vk.service.PhotoService;

import java.io.IOException;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "profile")
@AllArgsConstructor
public class ProfileController {

    private final PhotoService photoService;
    private final FriendsSercive friendsSercive;

    @PostMapping("/upload")
    public BodyBuilder uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        photoService.savePhoto(file);
        return ResponseEntity.status(HttpStatus.OK);
    }

    @PostMapping("/friend")
    public BodyBuilder addFreinds(@RequestBody FriendsRequest request) {
        friendsSercive.add(request.getUsername(),request.getFriend());
        return ResponseEntity.status(HttpStatus.OK);
    }

}
