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
@RequestMapping("/api/media/tv-series")
public class SeriesController {
    @Autowired
    ContentService contentService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private ImageService imageService;


    @GetMapping
    public ResponseEntity<List<ContentDto>> getSeries() {
        List<ContentDto> seriesDto = seriesService.getSeries();
        return ResponseEntity.ok(seriesDto);
    }


    @GetMapping("/{seriesId}")
    public ResponseEntity<List<EpisodeDto>> getEpisodes(@PathVariable("seriesId") long seriesId) {
        List<EpisodeDto> episodes = episodeService.getEpisodes(seriesId);
        return ResponseEntity.ok(episodes);
    }

    @PostMapping("/create")
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

    @PostMapping("/{seriesId}/create")
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

    @PutMapping("/{seriesId}/update")
    public ResponseEntity<String> updateSeries(ContentDto contentDto,
                                               @RequestParam(value = "banner_url", required=false) MultipartFile bannerFile,
                                               @RequestParam(value = "thumbnail_url", required=false) MultipartFile thumbFile,
                                               @PathVariable(name="seriesId") long id) throws IOException {
        String banner = imageService.saveFile(bannerFile.getOriginalFilename(), bannerFile);
        String thumb = imageService.saveFile(thumbFile.getOriginalFilename(), thumbFile);

        seriesService.update(id, contentDto, banner, thumb);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/{seriesId}/{id}/update")
    public ResponseEntity<String> Episode(@RequestBody EpisodeDto payload, @PathVariable(name="seriesId") long seriesId, @PathVariable(name="id") long id) {
        episodeService.update(id, payload);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{seriesId}/delete/{episodeId}")
    public ResponseEntity<String> deleteEpisode(@PathVariable(name="contentType") String contentType, @PathVariable(name="episodeId") long id) {
        contentService.delete(id);
        return ResponseEntity.ok("Deleted");
    }


}
