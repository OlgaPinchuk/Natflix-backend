package com.novare.natflix.services;

import com.novare.natflix.utils.FileUpload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
public class ImageService {
    private static final String LOCAL_SERVER_URL = "http://localhost:8080/api/files/download/";
    public String getImageUrl(String imageData) {

        try {
            byte[] decodedBytes = Base64.getDecoder().decode(imageData.split(",")[1]);
            return LOCAL_SERVER_URL + FileUpload.saveFile(decodedBytes);
        }
         catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
