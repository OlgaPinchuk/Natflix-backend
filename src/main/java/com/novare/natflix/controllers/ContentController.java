package com.novare.natflix.controllers;

import com.novare.natflix.exceptions.ResourceNotFoundException;
import com.novare.natflix.models.content.*;
import com.novare.natflix.payloads.*;
import com.novare.natflix.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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

    @GetMapping("/movies")
    public ResponseEntity<List<MovieDto>> getMovies() {
        List<MovieDto> movies = movieService.getMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/tv-series")
    public ResponseEntity<List<ContentDto>> getSeries() {
        List<ContentDto> seriesDto = seriesService.getSeries();
        return ResponseEntity.ok(seriesDto);
    }

    @GetMapping("/documentaries")
    public ResponseEntity<List<DocumentaryDto>> getDocumentaries() {
       List<DocumentaryDto> documentaries = documentaryService.getDocumentaries();
       return ResponseEntity.ok(documentaries);
    }

    @GetMapping("/tv-series/{seriesId}")
    public ResponseEntity<List<EpisodeDto>> getEpisodes(@PathVariable("seriesId") long seriesId) {
        List<EpisodeDto> episodes = episodeService.getEpisodes(seriesId);
        return ResponseEntity.ok(episodes);
    }

    @PostMapping("/tv-series/create")
    public ResponseEntity<?> createSeries(ContentDto contentDto,
                                          @RequestParam("banner_url") MultipartFile fileBanner,
                                          @RequestParam("thumbnail_url") MultipartFile fileThumbnail) throws IOException {

        Genre genre = contentService.findGenreById(contentDto.getGenre_id());

        if (contentDto.getTitle() == null || genre == null) {
            return ResponseEntity.badRequest().body("Required fields are missing");
        }

        Series newSeries = new Series();
        newSeries.setCommonProperties(contentDto, genre);
        newSeries.setContentType(contentService.findContentTypeByName("Series"));

        contentService.create(newSeries);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/movies/create")
    public ResponseEntity<?> createMovies(@RequestBody MovieDto movieDto) {
        Genre genre = contentService.findGenreById(movieDto.getGenre_id());

        if (movieDto.getTitle() == null || genre == null) {
            return ResponseEntity.badRequest().body("Required fields are missing");
        }

        Movie newMovie = new Movie();
        newMovie.setCommonProperties(movieDto, genre);
        newMovie.setContentType(contentService.findContentTypeByName("Movies"));
        newMovie.setDirector(movieDto.getDirector());
        newMovie.setVideoCode(movieDto.getVideoCode());
        newMovie.setRating(movieDto.getRating());
        contentService.create(newMovie);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/documentaries/create")
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

    @PostMapping("/tv-series/{seriesId}/create")
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

        contentService.create(newEpisode);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/movies/{id}/update")
        public ResponseEntity<String> updateMovie(@RequestBody MovieDto payload, @PathVariable(name="id") long id) {
            movieService.update(id, payload);
            return ResponseEntity.ok("Success");
        }

    @PutMapping("/documentaries/{id}/update")
    public ResponseEntity<String> updateDocumentary(@RequestBody DocumentaryDto payload, @PathVariable(name="id") long id) {
        documentaryService.update(id, payload);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/tv-series/{id}/update")
    public ResponseEntity<String> updateSeries(ContentDto contentDto,
                                               @RequestParam(value = "banner_url", required=false) MultipartFile bannerFile,
                                               @RequestParam(value = "thumbnail_url", required=false) MultipartFile thumbFile,
    @PathVariable(name="id") long id) throws IOException {
        String banner = imageService.saveFile(bannerFile.getOriginalFilename(), bannerFile);
        String thumb = imageService.saveFile(thumbFile.getOriginalFilename(), thumbFile);

        seriesService.update(id, contentDto, banner, thumb);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/tv-series/{seriesId}/{id}/update")
    public ResponseEntity<String> Episode(@RequestBody EpisodeDto payload, @PathVariable(name="seriesId") long seriesId, @PathVariable(name="id") long id) {
        episodeService.update(id, payload);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{contentType}/delete/{id}")
    public ResponseEntity<String> deleteContent(@PathVariable(name="contentType") String contentType, @PathVariable(name="id") long id) {
        contentService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

    @DeleteMapping("/{contentType}/{seriesId}/delete/{episodeId}")
    public ResponseEntity<String> deleteEpisode(@PathVariable(name="contentType") String contentType, @PathVariable(name="episodeId") long id) {
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
