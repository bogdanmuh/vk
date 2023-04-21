package vk.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vk.controller.pojo.ImageModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Service
public class PhotoService {
    @Value("${upload.path}")
    private String uploadPath;
    public void savePhoto(MultipartFile file) throws IOException {
        file.transferTo(toOurDirectories(file));  ;
    }
    public ImageModel getPhoto (String userId) throws IOException {
        ImageModel img = null;

        Optional<Path> photoPath  = Files.walk(getPathForUsername(userId))
                .filter(Files::isRegularFile)
                .findFirst();
        if (photoPath.isPresent()) {
            File file = photoPath.get().toFile();
            FileInputStream fileStream = new FileInputStream(file);
            img = new ImageModel(file.getName(),
                    file.getName().substring(file.getName().indexOf('.')),
                    fileStream.readAllBytes());
        }
        return img;
    }
    private Path getPathForUsername(String username) {
        String path = "";
        Path currentPath = Path.of(uploadPath);

        for (int i = 0; i < username.length(); i++) {
            path = path + username.charAt(i);
            if (path.length() == 2)
                path = "";
        }
        if (!path.isEmpty()) {
            currentPath = d(currentPath,path);
        }
        return currentPath;
    }

    private Path toOurDirectories (MultipartFile file) {
        String path  = "";
        Path currentPath = Path.of(uploadPath);

        for (int i = 0; i < file.getOriginalFilename().length(); i++) {
            path = path + file.getOriginalFilename().charAt(i);
            if (path.length() == 2) {
                currentPath = d(currentPath,path);
                path = "";
            }
        }
        if (!path.isEmpty()) {
            currentPath = d(currentPath,path);
        }
        return Path.of(currentPath + File.separator + file.getOriginalFilename() + ".jpeg");
    }

    private Path d (Path currentPath , String path)  {
        currentPath  = Path.of(currentPath + File.separator + path);
        if (!Files.exists(currentPath)) {
            try {
                Files.createDirectories(currentPath);
            } catch (IOException e) {
                ////////////////
            }
        }
        return currentPath;
    }

}
