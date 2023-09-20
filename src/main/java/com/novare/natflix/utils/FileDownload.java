package com.novare.natflix.utils;

import java.io.IOException;
import java.nio.file.*;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class FileDownload {
    private Path foundFile;

    public Resource getFileAsResource(String fileCode) throws IOException {
        Path dirPath = Paths.get("files-upload");

        Files.list(dirPath).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileCode)) {
                foundFile = file;
                return;
            }
        });

        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }

        return null;
    }
}
