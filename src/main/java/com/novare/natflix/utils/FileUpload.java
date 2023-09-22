package com.novare.natflix.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class FileUpload {

    public static String saveFile(byte[] decodedData) throws IOException {
        Path uploadPath = Paths.get("src/main/files-upload");
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileCode = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
        String fileExtension = ".png";
        String fileName = fileCode + fileExtension;

        try  {
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, decodedData);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }

        return fileName;
    }
}
