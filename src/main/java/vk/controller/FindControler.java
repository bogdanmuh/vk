package vk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vk.controller.pojo.FindRequest;
import vk.controller.pojo.FindResponse;
import vk.controller.pojo.ImageModel;
import vk.controller.pojo.ProfileResponse;
import vk.domain.User;
import vk.repos.UserRepository;
import vk.service.PhotoService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class FindControler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhotoService photoService;
    @PostMapping("/find")
    public ResponseEntity<?> findInfo(@RequestBody FindRequest findRequest) {
        List<String> users = userRepository.findAll().stream()
                .map(User::getUsername)
                .filter(username->username.contains(findRequest.getText()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new FindResponse(users));
    }
    @GetMapping("profile/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) throws IOException {
        User user = userRepository.findByUsername(userId).get();

        File file = Files.walk(photoService.getPathForUsername(userId))
                .filter(Files::isRegularFile)
                .findFirst().get().toFile();
        FileInputStream fileStream = new FileInputStream(file);
        ImageModel img = new ImageModel(file.getName(),
                file.getName().substring(file.getName().indexOf('.')),
                fileStream.readAllBytes());

        return ResponseEntity.ok(new ProfileResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getDate(),
                user.getUsername(),
                user.getEmail(),
                img,
                user.getRoles().stream()
                        .map(data->data.getName().toString())
                        .collect(Collectors.toList())));
    }
}
