package com.novare.natflix.controllers;

import com.novare.natflix.exceptions.ResourceNotFoundException;
import com.novare.natflix.models.content.*;
import com.novare.natflix.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/media")
public class ContentController {
    @Autowired
    ContentService contentService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private DocumentaryService documentaryService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private ImageService imageService;

    @GetMapping(params = "id")
    public ResponseEntity<?> getContentById(@RequestParam("id") long id) {

        Content contentItem = contentService.get(id);

        if (contentItem == null) {
            throw new ResourceNotFoundException("Content", "id", String.valueOf(id));
        }
        Object responseDto = convertContentToResponseDto(contentItem);

        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        } else {
            throw new ResourceNotFoundException("Content type not supported");
        }
    }

    @DeleteMapping("/{contentType}/delete/{id}")
    public ResponseEntity<String> deleteContent(@PathVariable(name="contentType") String contentType, @PathVariable(name="id") long id) {
        contentService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

    private Object convertContentToResponseDto(Content content) {
        if (content instanceof Movie) {
            return movieService.convertToDto((Movie) content);
        } else if (content instanceof Series) {
            return seriesService.convertToDto((Series) content);
        } else if (content instanceof Documentary) {
            return documentaryService.convertToDto((Documentary) content);
        } else {
            return null;
        }
    }

}
