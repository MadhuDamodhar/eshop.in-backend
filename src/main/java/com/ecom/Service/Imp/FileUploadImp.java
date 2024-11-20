package com.ecom.Service.Imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.Service.FileUpload;

@Service
public class FileUploadImp implements FileUpload {

    @Override
    public List<String> uploadFiles(String path, List<MultipartFile> files) throws IOException {
        List<String> imageNames = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                continue; // Skip if original filename is null
            }
            String randomImageName = UUID.randomUUID().toString();
            String randomImageNameWithExtension = randomImageName.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));

            String fullPath = path + File.separator + randomImageNameWithExtension;

            File folderFile = new File(path);
            if (!folderFile.exists()) {
                folderFile.mkdirs(); // Create directories if not exist
            }

            // Save the file
            Files.copy(file.getInputStream(), Paths.get(fullPath));
            imageNames.add(randomImageNameWithExtension); // Add to the list of image names
        }

        return imageNames; // Return the list of uploaded image names
    }

    @Override
    public InputStream getResource(String path) throws FileNotFoundException {
        return new FileInputStream(path);
    }

    @Override
    public void deleteFile(String filePath) {
        // Delete file if exists
    }
}
