package com.novare.natflix.controllers;

import com.novare.natflix.exceptions.ResourceNotFoundException;
import com.novare.natflix.models.content.*;
import com.novare.natflix.payloads.*;
import com.novare.natflix.services.ContentService;
import com.novare.natflix.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/media")
public class ContentController {
    @Autowired
    ContentService contentService;

    @GetMapping(params = "id")
    public Content getContentById(@RequestParam("id") long id) {
        Content contentItem = contentService.get(id);

        if(contentItem == null) {
            throw new ResourceNotFoundException("Content", "id", String.valueOf(id));
        }

        return contentItem;
    }

    @GetMapping("/{contentType}")
    public List<Content> getContentByType(@PathVariable String contentType) {

        List<Content> contentList = contentService.getContentsByType(StringUtil.capitalizeFirstLetter(contentType));

        if (contentList == null || contentList.isEmpty()) {
            throw new ResourceNotFoundException("Content", "contentType", contentType);
        }

        return contentList;
    }

    @GetMapping("/series/{seriesId}/episodes")
    public Set<Episode> getEpisodes(@PathVariable("seriesId") long seriesId) {
        return contentService.findEpisodesBySeriesId(seriesId);
    }

    @PostMapping("/series/create")
    public ResponseEntity<?> createSeries(@RequestBody ContentDto contentDto) {
        Genre genre = contentService.findGenreByName(contentDto.getGenre());

        if (contentDto.getTitle() == null || genre == null) {
            return ResponseEntity.badRequest().body("Required fields are missing");
        }

        Series newSeries = new Series();
        newSeries.setCommonProperties(contentDto, genre);
        newSeries.setContentType(contentService.findContentTypeByName("Series"));

        Content createdContent = contentService.create(newSeries);

        return new ResponseEntity<>(createdContent, HttpStatus.CREATED);
    }


    @PostMapping("/movies/create")
    public ResponseEntity<?> createMovies(@RequestBody MovieDto movieDto) {
        Genre genre = contentService.findGenreByName(movieDto.getGenre());

        if (movieDto.getTitle() == null || genre == null) {
            return ResponseEntity.badRequest().body("Required fields are missing");
        }

        Movie newMovie = new Movie();
        newMovie.setCommonProperties(movieDto, genre);
        newMovie.setContentType(contentService.findContentTypeByName("Movies"));
        newMovie.setDirector(movieDto.getDirector());
        newMovie.setVideoCode(movieDto.getVideoCode());

        Content createdContent = contentService.create(newMovie);

        return new ResponseEntity<>(createdContent, HttpStatus.CREATED);
    }

    @PostMapping("/documentaries/create")
    public ResponseEntity<?> createDocumentary(@RequestBody DocumentaryDTO docDto) {
        Genre genre = contentService.findGenreByName(docDto.getGenre());

        if (docDto.getTitle() == null || genre == null) {
            return ResponseEntity.badRequest().body("Required fields are missing");
        }

        Documentary newDoc = new Documentary();
        newDoc.setCommonProperties(docDto, genre);
        newDoc.setContentType(contentService.findContentTypeByName("Documentaries"));
        newDoc.setNarrator(docDto.getNarrator());
        newDoc.setVideoCode(docDto.getVideoCode());

        Content createdContent = contentService.create(newDoc);

        return new ResponseEntity<>(createdContent, HttpStatus.CREATED);
    }

    @PostMapping("/series/{seriesId}/episodes")
    public ResponseEntity<?> createEpisode(
            @PathVariable("seriesId") long seriesId,
            @RequestBody EpisodeDto episodeDto) {

        Series series = contentService.findSeriesById(seriesId);

        if (series == null) {
            throw new ResourceNotFoundException("Series", "id", String.valueOf(seriesId));
        }


        Episode newEpisode = new Episode();
        newEpisode.setCommonProperties(episodeDto, series.getGenre());
        newEpisode.setSeasonNumber(episodeDto.getSeasonNumber());
        newEpisode.setEpisodeNumber(episodeDto.getEpisodeNumber());
        newEpisode.setVideoCode(episodeDto.getVideoCode());

        series.addEpisode(newEpisode);

        Content createdEpisode = contentService.create(newEpisode);
        Map<String, Long> response = new HashMap<>();
        response.put("id", createdEpisode.getId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
        public ResponseEntity<String> updateContent(@RequestBody ContentDto payload, @PathVariable(name="id") long id) {
            contentService.update(id, payload);
            return ResponseEntity.ok("Success");
        }
    @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteContent(@PathVariable(name="id") long id) {
            contentService.delete(id);
            return ResponseEntity.ok("Deleted");
        }

}
