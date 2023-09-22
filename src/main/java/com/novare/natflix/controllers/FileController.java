package com.novare.natflix.controllers;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import com.novare.natflix.utils.FileDownload;
import com.novare.natflix.utils.FileUploadResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController("/api/files")
public class FileController {

    @GetMapping("/download/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
        FileDownload fileDownload = new FileDownload();

        Resource resource = null;
        try {
            resource = fileDownload.getFileAsResource(fileCode);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") String base64File)
            throws IOException {

        byte[] decodedBytes = Base64.getDecoder().decode(base64File.split(",")[1]);
        String fileName = UUID.randomUUID().toString();
        String fileCode = RandomStringUtils.randomAlphanumeric(8);

     //   FileUpload.saveFile(fileName, base64File);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(decodedBytes.length);
        response.setDownloadUri("/downloadFile/" + fileCode);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
