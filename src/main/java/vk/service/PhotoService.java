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
import java.util.stream.Stream;


@Service
public class PhotoService {
    // bogdan  bo->gd->an-> фотографии
    // в каждой папкеможет храниться около 6500
    // 6500 - 2900  количество фоток у одного пользователя
    // если добавить папку user по этому пути bo->gd->an то увеличим в два раза количкство фоток.
    // bogdann  bo->gd->an->n-> фотографии

    @Value("${upload.path}")
    private String uploadPath;

    public void savePhoto(MultipartFile file) throws IOException {
        if(file == null) return;

        file.transferTo(toOurDirectories(file));
    }

    public ImageModel getPhoto (String userId) throws IOException {
        try (Stream<Path> stream = Files.walk(getPathForUsername(userId))) {
            Optional<Path> photoPath  = stream
                    .filter(Files::isRegularFile)
                    .findFirst();
            if (photoPath.isPresent()) {
                File file = photoPath.get().toFile();
                try (FileInputStream fileStream = new FileInputStream(file)) {
                    return new ImageModel(file.getName(),
                            file.getName().substring(file.getName().indexOf('.')),
                            fileStream.readAllBytes());
                }
            }
        }
        return null;
    }

    private Path getPathForUsername(String username) {
        StringBuilder path = new StringBuilder();
        Path currentPath = Path.of(uploadPath);

        for (int i = 0; i < username.length(); i++) {
            path.append(username.charAt(i));
            if (path.length() == 2)
                path = new StringBuilder();
        }
        if (path.length() > 0) {
            currentPath = d(currentPath, path.toString());
        }
        return currentPath;
    }

    private Path toOurDirectories (MultipartFile file) {
        StringBuilder path  = new StringBuilder();
        Path currentPath = Path.of(uploadPath);

        for (int i = 0; i < file.getOriginalFilename().length(); i++) {
            path.append(file.getOriginalFilename().charAt(i));
            if (path.length() == 2) {
                currentPath = d(currentPath, path.toString());
                path = new StringBuilder();
            }
        }
        if (path.length() > 0) {
            currentPath = d(currentPath, path.toString());
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
