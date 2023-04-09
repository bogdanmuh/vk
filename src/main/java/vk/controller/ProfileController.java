package vk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vk.service.PhotoService;

import java.io.IOException;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "profile")
public class ProfileController {
    @Autowired
    private PhotoService photoService;

    @PostMapping("/upload")
    public BodyBuilder uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        photoService.savePhoto(file);
        return ResponseEntity.status(HttpStatus.OK);
    }
}
