package com.ecom.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileUpload {
    // Create files on the server at the given path
    List<String> uploadFiles(String path, List<MultipartFile> files) throws IOException;

    // Get the resource
    InputStream getResource(String path) throws FileNotFoundException;

    // Delete file
    void deleteFile(String file);
}
