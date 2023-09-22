package com.novare.natflix.controllers;

import com.novare.natflix.models.content.*;
import com.novare.natflix.payloads.*;
import com.novare.natflix.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/media/documentaries")
public class DocumentariesController {
    @Autowired
    ContentService contentService;

    @Autowired
    private DocumentaryService documentaryService;


    @GetMapping()
    public ResponseEntity<List<DocumentaryDto>> getDocumentaries() {
        List<DocumentaryDto> documentaries = documentaryService.getDocumentaries();
        return ResponseEntity.ok(documentaries);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDocumentary(@RequestBody DocumentaryDto docDto) {
        Genre genre = contentService.findGenreById(docDto.getGenre_id());

        if (docDto.getTitle() == null || genre == null) {
            return ResponseEntity.badRequest().body("Required fields are missing");
        }

        Documentary newDoc = new Documentary();
        newDoc.setCommonProperties(docDto, genre);
        newDoc.setContentType(contentService.findContentTypeByName("Documentaries"));
        newDoc.setNarrator(docDto.getNarrator());
        newDoc.setVideoCode(docDto.getVideoCode());

        contentService.create(newDoc);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/{id}/update")
    public ResponseEntity<String> updateDocumentary(@RequestBody DocumentaryDto payload, @PathVariable(name="id") long id) {
        documentaryService.update(id, payload);
        return ResponseEntity.ok("Success");
    }
}